package me.shreyasr.networked.util

import com.badlogic.ashley.core.Entity
import me.shreyasr.networked.component._

object EntityFactory {

  def createRenderablePlayer(id: Int = IdComponent.randomId()) =
    createPlayer(id)
      .add(new RenderDataComponent(Asset.FIGHTER, 58, 72, 1f))

  def createPlayer(id: Int) = new Entity()
    .add(new TypeComponent(TypeComponent.Ship))
    .add(new IdComponent(id))
    .add(new StateDataComponent)
    .add(new InputDataComponent)

  def createLaser(ownerState: StateDataComponent): Entity = new Entity()
    .add(IdComponent.create())
    .add(new TypeComponent(TypeComponent.Laser))
    .add(new StateDataComponent(ownerState.pos.copy, ownerState.dir, 5))
    .add(new RenderDataComponent(Asset.LASER, 460, 15, 0.25f))
}
