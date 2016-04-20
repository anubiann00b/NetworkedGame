package me.shreyasr.networked.system.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import me.shreyasr.networked.system.render.util.BasicRenderSystem

class PreRenderSystem(priority: Int) extends BasicRenderSystem(priority) {

  override def update(deltaTime: Float) {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }
}
