package me.shreyasr.networked.util

import me.shreyasr.networked.NetworkedGame
import me.shreyasr.networked.util.network.PacketToClient
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData

import scala.collection.mutable

class PacketQueue {

  var list = mutable.ListBuffer[PacketToClient]()
  list += new PacketToClient(Array[EntityUpdateData](), System.currentTimeMillis())

  def addPacket(packet: PacketToClient): Unit = {
    if (!list.exists(_.time == packet.time)) {
      packet +=: list
      list = list.sortBy(_.time * -1)
      if (list.size > 1000) {
//        if (list.last == lastPacket) {
//          list.remove(list.size - 2)
//        } else {
          list.remove(list.size - 1)
//        }
      }
    }
  }

  var lastPacket = list.head
  var lastIndex = -1

  def getNextPacket(time: Long): Option[PacketToClient] = {
    val index = list.indexOf(lastPacket)
    lastIndex = index
    if (index <= 0) None
    else if (index < 3 && lastPacket.time > time-NetworkedGame.GLOBAL_DELAY*3) None
//    else if (index == 0) None
    else {
      val nextPacket = list(index-1)
      if (time - nextPacket.time > NetworkedGame.GLOBAL_DELAY) {
        lastPacket = nextPacket
        Some(nextPacket)
      } else None
    }
  }
}
