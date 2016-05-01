package me.shreyasr.networked.component

import com.badlogic.ashley.core.Component
import com.esotericsoftware.kryo.io.{Input, Output}
import com.esotericsoftware.kryo.{Kryo, Serializer}
import me.shreyasr.networked._
import me.shreyasr.networked.util.Vec2

class StateDataComponent(val pos: Vec2 = new Vec2(0, 0), var dir: Int = 0, var vel: Float = 0f)
  extends Component {

  var lastKnownPos = pos.copy
  var lastKnownDir = dir
  var lastKnownVel = vel

  override def toString: String = s"State[$pos ${vel.display} ${dir.display}]"
}

object StateDataComponent {
  class StateDataComponentSerializer extends Serializer[StateDataComponent] {
    override def write(kryo: Kryo, output: Output, component: StateDataComponent): Unit = {
      kryo.writeObject(output, component.pos)
      output.writeInt(component.dir)
      output.writeFloat(component.vel)
    }

    override def read(kryo: Kryo, input: Input, cls: Class[StateDataComponent]): StateDataComponent = {
      val pos = kryo.readObject(input, classOf[Vec2])
      val component = new StateDataComponent(pos, input.readInt(), input.readFloat())
      component
    }
  }
}