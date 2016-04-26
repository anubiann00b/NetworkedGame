package me.shreyasr.networked.util

import me.shreyasr.networked.NetworkedGame
import me.shreyasr.networked.util.network.PacketToClient
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData

import scala.collection.mutable

class PacketQueue {

  var list = mutable.ListBuffer[PacketToClient]()
  list += new PacketToClient(Array[EntityUpdateData](), System.currentTimeMillis())

  var currentPacketIndex = 0

  def addPacket(packet: PacketToClient): Unit = {
    if (!list.exists(_.time == packet.time)) {
      packet +=: list
      list = list.sortBy(_.time * -1)
      if (list.size > 10000) {
        list.remove(list.size - 1)
      }
    }
  }

  var lastPacket = list.head
  var lastIndex = -1

  def getNextPacket(time: Long): Option[PacketToClient] = {
    val index = list.indexOf(lastPacket)
    lastIndex = index
    if (index < 0) None
    else if (index == 0) None
    else {
      val nextPacket = list(index-1)
      if (time - nextPacket.time > NetworkedGame.GLOBAL_DELAY) {
        lastPacket = nextPacket
        Some(nextPacket)
      } else None
    }
  }

  def getPacketsTo(time: Long): Seq[PacketToClient] = {
//    val packets = list.filter(_.time < time-NetworkedGame.GLOBAL_DELAY)
//    packets.foreach(_.consumed = true)
//    prune()
//    packets
    val index = list.indexOf(lastPacket)-1
    println(index)
    if (index < 3) Seq()
    else if (index > 8) {
      lastPacket = list(index - 2)
      list.slice(index-2, index+1).reverse
    } else {
      lastPacket = list(index)
      Seq(list(index))
    }
  }

  def prune() = {
    list = list.filterNot(_.consumed)
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
