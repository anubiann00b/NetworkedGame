package me.shreyasr.networked.util

import me.shreyasr.networked.util.network.PacketToClient
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData

import scala.collection.mutable

class PacketQueue {

  var queue = new mutable.PriorityQueue[PacketToClient]()
  queue += new PacketToClient(Array[EntityUpdateData](), System.currentTimeMillis())

  def addPacket(packet: PacketToClient): Unit = {
    if (!queue.exists(_.time == packet.time)) {
      queue += packet
    }
  }

  var lastPacket = queue.head

  def getNextPacket(time: Long): Option[PacketToClient] = {
    if (queue.size <= 3) None
//    else if (index < 3 && lastPacket.time > time-NetworkedGame.GLOBAL_DELAY*3) None
    else {
//      if (time - queue.head.time > NetworkedGame.GLOBAL_DELAY) {
        Some(queue.dequeue())
//      } else None
    }
  }
}
