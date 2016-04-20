package me.shreyasr.networked.system

import com.badlogic.ashley.core.EntitySystem
import me.shreyasr.networked.NetworkedGame

class InputUpdateSystem(priority: Int, res: NetworkedGame.Res) extends EntitySystem(priority) {

  override def update(deltaTime: Float): Unit = {
    res.player.add(res.inputQueue.getInput(System.currentTimeMillis()))
  }
}
