package me.shreyasr.networked.system.render

import me.shreyasr.networked.NetworkedGame
import me.shreyasr.networked.system.render.util.BasicRenderSystem

class PostRenderSystem(priority: Int, res: NetworkedGame.Res) extends BasicRenderSystem(priority) {

  override def update(deltaTime: Float) {
    if (res.shape.isDrawing) res.shape.end()
    if (res.batch.isDrawing) res.batch.end()
  }
}
