package me.shreyasr.networked.util

import com.badlogic.ashley.core.Entity
import me.shreyasr.networked.component.{IdComponent, RenderDataComponent, StateDataComponent}

object EntityFactory {

  def createRenderablePlayer(id: Int = IdComponent.randomId(),
                             stateDataComponent: StateDataComponent = new StateDataComponent) =
    createPlayer(id, stateDataComponent)
      .add(new RenderDataComponent(Asset.FIGHTER, 141, 95, 0.75f))

  def createPlayer(id: Int, stateDataComponent: StateDataComponent = new StateDataComponent) = new Entity()
    .add(new IdComponent(id))
    .add(stateDataComponent)
}
