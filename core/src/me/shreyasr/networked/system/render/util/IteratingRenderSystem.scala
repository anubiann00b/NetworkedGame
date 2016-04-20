package me.shreyasr.networked.system.render.util

import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem

abstract class IteratingRenderSystem(family: Family, priority: Int) extends IteratingSystem(family, priority)
  with RenderSystem {

}
