package me.shreyasr.networked.system

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import me.shreyasr.networked.{NetworkedGame, _}
import me.shreyasr.networked.component.{InputDataComponent, StateDataComponent, TypeComponent}
import me.shreyasr.networked.util.EntityFactory

class UpdateSystem(priority: Int, res: NetworkedGame.BaseRes)
  extends IteratingSystem(Family.all(classOf[StateDataComponent]).get(), priority) {

  override def processEntity(entity: Entity, deltaTime: Float) = {
    val inputOpt = entity.getOpt[InputDataComponent]
    val state = entity.get[StateDataComponent]

    if (inputOpt.isDefined) {
      val input = inputOpt.get
        if (!input.freshFromServer) {
        if (input.w) state.vel += 0.1f
        if (input.s) state.vel -= 0.1f

        if (input.a) state.dir += 0.05f
        if (input.d) state.dir -= 0.05f
      } else {
        input.freshFromServer = false
      }

      if (input.space) {
        getEngine.addEntity(EntityFactory.createLaser(state))
      }
    }

    state.pos.x += state.vel * Math.cos(state.dir).toFloat
    state.pos.y += state.vel * Math.sin(state.dir).toFloat

    if (state.pos.x < -100 || state.pos.x > 1000 || state.pos.y < -100 || state.pos.y > 1000) {
      if (entity.is[TypeComponent.Laser]) getEngine.removeEntity(entity)
    }
  }
}
