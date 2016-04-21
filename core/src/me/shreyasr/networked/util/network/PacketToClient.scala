package me.shreyasr.networked.util.network

import me.shreyasr.networked._
import me.shreyasr.networked.component.{InputDataComponent, StateDataComponent}
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData

class PacketToClient(val entityData: Array[EntityUpdateData],
                     val time: Long) {

  def this() = this(null, 0)

  override def toString: String =
    s"PacketToClient[${time.display} ${entityData mkString ","}]"
}

object PacketToClient {
  class EntityUpdateData(val entityId: Int,
                         val stateDataComponent: StateDataComponent,
                         val inputDataComponent: InputDataComponent) {

    def this() = this(0, null, null)

    override def toString: String =
      s"EntityUpdateData[${entityId.display} $stateDataComponent $inputDataComponent]"
  }
}