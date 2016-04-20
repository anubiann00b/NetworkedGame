package me.shreyasr.networked.system

import com.badlogic.ashley.core.EntitySystem
import me.shreyasr.networked.NetworkedGame
import me.shreyasr.networked.component.InputDataComponent

class InputHandleSystem(priority: Int, res: NetworkedGame.Res) extends EntitySystem(priority) {

  override def update(deltaTime: Float) {
    res.inputQueue.addInputData(System.currentTimeMillis, InputDataComponent.create)
  }
}
