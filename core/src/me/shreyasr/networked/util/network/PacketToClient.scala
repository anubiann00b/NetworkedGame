package me.shreyasr.networked.util.network

import me.shreyasr.networked.component.{InputDataComponent, StateDataComponent}

class PacketToClient(val entityId: Int, val stateDataComponent: StateDataComponent,
                     val inputDataComponentOpt: Option[InputDataComponent]) {

  def this() = this(0, null, None)

  override def toString: String = s"PacketToClient[$entityId $stateDataComponent]"
}
