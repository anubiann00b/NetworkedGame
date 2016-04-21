package me.shreyasr.networked.util

import me.shreyasr.networked.util.network.PacketToClient
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData

import scala.collection.mutable

class InputDataQueue {

  var list = mutable.ListBuffer[PacketToClient]()
  list += new PacketToClient(Array[EntityUpdateData](), -Long.MaxValue)

  def addPacket(packet: PacketToClient): Unit = {
    packet +=: list
    list = list.sortBy(_.time * -1)
    if (list.size > 10) list.remove(list.size - 1)
  }

  var lastGottenPacket = list.head
  def getPacket(time: Long): PacketToClient = {
    var result = lastGottenPacket
    if (lastGottenPacket.time < time-100) {
      list.reverse.sliding(2).find(slidingWindow => {
        val packet = slidingWindow.head
        val nextPacket = slidingWindow.last

        if (packet == lastGottenPacket) {
          result = nextPacket
          lastGottenPacket = result
          true
        } else false
      })
    }
    result
//    list.find(_.time < time-100).get
  }
}
