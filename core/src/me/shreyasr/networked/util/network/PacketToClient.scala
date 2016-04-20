package me.shreyasr.networked.util.network

import me.shreyasr.networked.component.StateDataComponent

class PacketToClient(val entityId: Int, val stateDataComponent: StateDataComponent) {

  def this() = this(0, null)

  override def toString: String = s"PacketToClient[$entityId $stateDataComponent]"
}
