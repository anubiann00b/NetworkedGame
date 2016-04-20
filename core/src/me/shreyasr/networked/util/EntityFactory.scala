package me.shreyasr.networked.util

import com.badlogic.ashley.core.Entity
import me.shreyasr.networked.component.{IdComponent, RenderDataComponent, StateDataComponent}

object EntityFactory {

  def createPlayer() = new Entity()
    .add(new IdComponent)
    .add(new StateDataComponent)
    .add(new RenderDataComponent(Asset.FIGHTER, 141, 95, 0.75f))
}
