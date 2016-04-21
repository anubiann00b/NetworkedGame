package me.shreyasr

import com.badlogic.ashley.core.{Component, Engine, Entity}
import me.shreyasr.networked.component.IdComponent

import scala.reflect._
import scala.collection.JavaConverters._

package object networked {

  implicit class FloatImprovements(float: Float) {
    def roundTo(digits: Int) = {
      val s = math.pow(10, digits)
      math.round(float * s) / s
    }
    def display = roundTo(4)
  }

  implicit class IntImprovements(int: Int) {
    def display: String = int.toLong.display
  }

  implicit class LongImprovements(long: Long) {
    def display: String = "%05d" format (long % 100000)
  }

  implicit class EntityImprovements(val entity: Entity) {
    def has[T <: Component : ClassTag]: Boolean = getOpt[T].isDefined
    def getOpt[T <: Component : ClassTag]: Option[T] = Option(get[T])
    def get[T <: Component : ClassTag]: T =
      entity.getComponent(classTag[T].runtimeClass.asInstanceOf[Class[T]])
    def id = get[IdComponent].id
  }

  implicit class EngineImprovements(val engine: Engine) {
    def getById(id: Int): Option[Entity] = {
      engine.getEntities.asScala.find(_.get[IdComponent].id == id)
    }
  }
}
