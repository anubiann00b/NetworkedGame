package me.shreyasr.networked.system.render

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import me.shreyasr.networked.{NetworkedGame, _}
import me.shreyasr.networked.component.{RenderDataComponent, StateDataComponent}
import me.shreyasr.networked.system.render.util.IteratingRenderSystem

class MainRenderSystem(priority: Int, res: NetworkedGame.RenderingRes)
  extends IteratingRenderSystem(Family.all(classOf[RenderDataComponent]).get(), priority) {

  var i = 0

  override def processEntity(entity: Entity, deltaTime: Float) {
    val ttc = entity.get[RenderDataComponent]
    val dataOpt = entity.getOpt[StateDataComponent]
    if (!ttc.hide) {
      ttc.asset.get.setFilter(TextureFilter.Linear, TextureFilter.Linear)

      if (dataOpt.isDefined && false) {
        val data = dataOpt.get
        res.batch.setColor(Color.RED)//.sub(0, 0, 0, 0.6f))
        res.batch.draw(ttc.asset.get,
          data.lastKnownPos.x%640 - ttc.originX, data.lastKnownPos.y%480 - ttc.originY,
          ttc.originX, ttc.originY,
          ttc.screenWidth, ttc.screenHeight,
          1, 1, data.lastKnownDir,
          ttc.srcX, ttc.srcY, ttc.srcWidth, ttc.srcHeight,
          ttc.flipX, ttc.flipY)
      }

      res.batch.setColor(ttc.color)
      res.batch.draw(ttc.asset.get,
        ttc.pos.x.toInt%640 - ttc.originX, ttc.pos.y.toInt%480 - ttc.originY,
        ttc.originX, ttc.originY,
        ttc.screenWidth, ttc.screenHeight,
        1, 1, ttc.rotation,
        ttc.srcX, ttc.srcY, ttc.srcWidth, ttc.srcHeight,
        ttc.flipX, ttc.flipY)

      i += 1
      println(s"$i ${ttc.pos.x}")
    }
  }
}
