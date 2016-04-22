package me.shreyasr.networked.util

import com.esotericsoftware.kryo.Kryo
import me.shreyasr.networked.component.{InputDataComponent, StateDataComponent, TypeComponent}
import me.shreyasr.networked.util.network.{PacketToClient, PacketToServer}

import scala.reflect._

object KryoRegistrar {

  def register(kryo: Kryo) = {
    kryo.registerType[PacketToClient]
    kryo.registerType[PacketToServer]

    kryo.registerType[StateDataComponent]
    kryo.registerType[InputDataComponent]
    kryo.registerType[TypeComponent]

    kryo.registerType[Vec2]

    kryo.register(TypeComponent.Ship.getClass)
  }

  implicit class KryoImprovements(val kryo: Kryo) {
    def registerType[T: ClassTag] = {
      kryo.register(classTag[T].runtimeClass)
    }
  }
}
