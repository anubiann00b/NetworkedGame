package me.shreyasr.networked.system.render

import com.badlogic.gdx.graphics.Color
import me.shreyasr.networked.NetworkedGame
import me.shreyasr.networked.system.render.util.BasicRenderSystem

class PreBatchRenderSystem(priority: Int, res: NetworkedGame.Res) extends BasicRenderSystem(priority) {

  override def update(deltaTime: Float) {
    if (res.shape.isDrawing) res.shape.end()
    if (res.batch.isDrawing) res.batch.end()
    res.batch.begin()
    res.batch.setColor(Color.WHITE)
  }
}
