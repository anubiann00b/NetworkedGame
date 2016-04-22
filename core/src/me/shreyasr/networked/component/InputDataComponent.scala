package me.shreyasr.networked.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.{Gdx, Input}

class InputDataComponent(var w: Boolean = false,
                         var a: Boolean = false,
                         var s: Boolean = false,
                         var d: Boolean = false,
                         var space: Boolean = false)
  extends Component {

  var freshFromServer = false
  def forClient(): InputDataComponent = {
    val newInput = new InputDataComponent(w, a, s, d, space)
    newInput.freshFromServer = true
    newInput
  }

  def this() = this(false)

  override def toString: String = "Input[" +
    (if (w) "w" else "") + (if (a) "a" else "") + (if (s) "s" else "") + (if (d) "d" else "") + "]"
}

object InputDataComponent {

  def create: InputDataComponent = {
    new InputDataComponent(
      Gdx.input.isKeyPressed(Input.Keys.W),
      Gdx.input.isKeyPressed(Input.Keys.A),
      Gdx.input.isKeyPressed(Input.Keys.S),
      Gdx.input.isKeyPressed(Input.Keys.D),
      Gdx.input.isKeyPressed(Input.Keys.SPACE))
  }
}