package me.shreyasr.networked.system

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import me.shreyasr.networked.component.{RenderDataComponent, StateDataComponent}
import me.shreyasr.networked.{NetworkedGame, _}

class RenderDataUpdateSystem(priority: Int, res: NetworkedGame.ClientRes)
  extends IteratingSystem(Family.all(classOf[StateDataComponent], classOf[RenderDataComponent]).get(), priority) {

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val state = entity.get[StateDataComponent]
    val render = entity.get[RenderDataComponent]

    render.pos.x = state.pos.x
    render.pos.y = state.pos.y
    render.rotation = state.dir
  }
}
