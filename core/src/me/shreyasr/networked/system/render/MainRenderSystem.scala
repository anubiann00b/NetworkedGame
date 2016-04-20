package me.shreyasr.networked.system.render

import com.badlogic.ashley.core.{Entity, Family}
import me.shreyasr.networked._
import me.shreyasr.networked.NetworkedGame
import me.shreyasr.networked.component.RenderDataComponent
import me.shreyasr.networked.system.render.util.IteratingRenderSystem

class MainRenderSystem(priority: Int, res: NetworkedGame.Res)
  extends IteratingRenderSystem(Family.all(classOf[RenderDataComponent]).get(), priority) {

  override def processEntity(entity: Entity, deltaTime: Float) {
    val ttc = entity.get[RenderDataComponent]
    if (!ttc.hide) {
      res.batch.setColor(ttc.color)
      res.batch.draw(ttc.asset.get,
        ttc.pos.x - ttc.originX, ttc.pos.y - ttc.originY,
        ttc.originX, ttc.originY,
        ttc.screenWidth, ttc.screenHeight,
        1, 1, ttc.rotation.toDegrees,
        ttc.srcX, ttc.srcY, ttc.srcWidth, ttc.srcHeight,
        ttc.flipX, ttc.flipY)
    }
  }
}
