package me.shreyasr.networked

import java.nio.ByteBuffer

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{ByteBufferInput, ByteBufferOutput}
import me.shreyasr.networked.component.{InputDataComponent, StateDataComponent}
import me.shreyasr.networked.util.KryoRegistrar
import me.shreyasr.networked.util.network.PacketToClient
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData

object KryoTests {

  def main(args: Array[String]) {
    val kryo = new Kryo()
    KryoRegistrar.register(kryo)
    val buffer = ByteBuffer.allocate(256)
    val output = new ByteBufferOutput(buffer)
    val state = new StateDataComponent()
    state.pos.x = 15
    state.pos.y = 17
    state.dir = 2
    state.vel = 1
    kryo.writeClassAndObject(output,
      new PacketToClient(Array(new EntityUpdateData(5, state, new InputDataComponent(true))), 1234))
    output.flush()
    println(buffer.position())
    buffer.rewind()
    println(kryo.readClassAndObject(new ByteBufferInput(buffer)))
  }
}
