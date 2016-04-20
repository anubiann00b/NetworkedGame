package me.shreyasr.networked.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color
import me.shreyasr.networked.util.{Asset, Vec2}

class RenderDataComponent(val asset: Asset, width: Int, height: Int, scale: Float) extends Component {

  val pos = new Vec2(0, 0)

  var screenWidth: Int = (width * scale).round
  var screenHeight: Int = (height * scale).round
  var normalScreenWidth = screenWidth
  var originX: Int = screenWidth/2
  var originY: Int = screenHeight/2
  var srcX: Int = 0
  var srcY: Int = 0
  var srcWidth: Int = width
  var srcHeight: Int = height
  var rotation: Float = 0
  var hide: Boolean = false
  var color: Color = Color.WHITE
  var flipX: Boolean = false
  var flipY: Boolean = false
}
