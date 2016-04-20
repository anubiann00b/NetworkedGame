package me.shreyasr

import com.badlogic.ashley.core.{Component, Entity}

import scala.reflect._

package object networked {

  implicit class EntityImprovements(val entity: Entity) {
    def has[T <: Component : ClassTag]: Boolean = getOpt[T].isDefined
    def getOpt[T <: Component : ClassTag]: Option[T] = Option(get[T])
    def get[T <: Component : ClassTag]: T =
      entity.getComponent(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
}
