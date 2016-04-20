package me.shreyasr.networked.component

import com.badlogic.ashley.core.Component
import me.shreyasr.networked.util.Vec2

class StateDataComponent extends Component {

  val pos = new Vec2(0, 0)
  var vel = 0f
  var dir = 0f
}
