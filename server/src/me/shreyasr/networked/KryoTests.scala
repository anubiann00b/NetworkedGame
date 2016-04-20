package me.shreyasr.networked

import java.nio.ByteBuffer

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{ByteBufferInput, ByteBufferOutput}
import me.shreyasr.networked.component.StateDataComponent
import me.shreyasr.networked.util.KryoRegistrar
import me.shreyasr.networked.util.network.PacketToClient

object KryoTests {

  def main(args: Array[String]) {
    val kryo = new Kryo()
    KryoRegistrar.register(kryo)
    val buffer = ByteBuffer.allocate(64)
    val output = new ByteBufferOutput(buffer)
    val state = new StateDataComponent()
    state.pos.x = 15
    state.pos.y = 17
    state.dir = 2
    state.vel = 1
    kryo.writeClassAndObject(output, new PacketToClient(5, state))
    output.flush()
    println(buffer.position())
    buffer.rewind()
    println(kryo.readClassAndObject(new ByteBufferInput(buffer)))
  }
}
