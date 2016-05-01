package me.shreyasr.networked.util.network

import com.badlogic.ashley.core.Entity
import me.shreyasr.networked._
import me.shreyasr.networked.component.{InputDataComponent, StateDataComponent}
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData

class PacketToClient(val entityData: Array[EntityUpdateData],
                     val time: Long) extends Ordered[PacketToClient] {

  @transient var consumed = false

  def this() = this(null, 0)
  def this(entityData: Array[EntityUpdateData]) = this(entityData, System.currentTimeMillis())

  override def toString: String =
    s"PacketToClient[${time.display} ${entityData mkString ","}]"

  override def compare(that: PacketToClient): Int = {
    Ordering.Long.compare(that.time, time)
  }
}

object PacketToClient {
  class EntityUpdateData(val entityId: Int,
                         val stateDataComponent: StateDataComponent,
                         val inputDataComponent: InputDataComponent) {

    def this() = this(0, null, null)
    def this(e: Entity) = {
      this(e.id, e.get[StateDataComponent], e.get[InputDataComponent].forClient())
    }

    override def toString: String =
      s"EntityUpdateData[${entityId.display} $stateDataComponent $inputDataComponent]"
  }
}