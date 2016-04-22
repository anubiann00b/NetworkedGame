package me.shreyasr.networked.component

import com.badlogic.ashley.core.Component
import me.shreyasr.networked._
import me.shreyasr.networked.util.Vec2

class StateDataComponent(val pos: Vec2 = new Vec2(0, 0), var dir: Float = 0f, var vel: Float = 0f)
  extends Component {

  override def toString: String = s"State[$pos ${vel.display} ${dir.display}]"
}
