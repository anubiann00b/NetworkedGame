package me.shreyasr.networked.util.network

import me.shreyasr.networked.component.InputDataComponent

class PacketToServer(val entityId: Int, val inputData: InputDataComponent) {

  def this() = this(0, null)

  override def toString: String = s"PacketToServer[$entityId $inputData]"
}
