package me.shreyasr.networked.system

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import me.shreyasr.networked.NetworkedGame
import me.shreyasr.networked.component.{InputDataComponent, StateDataComponent}
import me.shreyasr.networked._

class UpdateSystem(priority: Int, res: NetworkedGame.Res)
  extends IteratingSystem(Family.all(classOf[InputDataComponent], classOf[StateDataComponent]).get(), priority) {

  override def processEntity(entity: Entity, deltaTime: Float) = {
    val input = entity.get[InputDataComponent]
    val state = entity.get[StateDataComponent]

    if (input.w) state.vel += 0.1f
    if (input.s) state.vel -= 0.1f

    if (input.a) state.dir += 0.05f
    if (input.d) state.dir -= 0.05f

    state.pos.x += state.vel * Math.cos(state.dir).toFloat
    state.pos.y += state.vel * Math.sin(state.dir).toFloat
  }
}
