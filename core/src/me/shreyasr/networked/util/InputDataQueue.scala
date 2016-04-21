package me.shreyasr.networked.util

import me.shreyasr.networked.NetworkedGame
import me.shreyasr.networked.util.network.PacketToClient
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData

import scala.collection.mutable

class InputDataQueue {

  val PACKET_COUNT = NetworkedGame.GLOBAL_DELAY/16*2

  var list = mutable.ListBuffer[PacketToClient]()
  list += new PacketToClient(Array[EntityUpdateData](), -Long.MaxValue)

  def addPacket(packet: PacketToClient): Unit = {
    packet +=: list
    list = list.sortBy(_.time * -1)
    if (list.size > PACKET_COUNT) {
      list.remove(list.size - 1)
    }
  }

  var lastGottenPacket = list.head
  def getPacket(time: Long): PacketToClient = {
    var result = lastGottenPacket
    var continue = true
    while (lastGottenPacket.time < time-NetworkedGame.GLOBAL_DELAY && continue) {
      val b = list.reverse.sliding(2).find(slidingWindow => {
        val packet = slidingWindow.head
        val nextPacket = slidingWindow.last

        if (packet == lastGottenPacket) {
          result = nextPacket
          lastGottenPacket = result
          true
        } else false
      })
      continue = b.isDefined
    }
    result
  }
}
