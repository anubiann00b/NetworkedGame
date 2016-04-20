package me.shreyasr.networked.system.render.util

import com.badlogic.ashley.core.EntitySystem

trait RenderSystem extends EntitySystem {

  override def checkProcessing = false
}
