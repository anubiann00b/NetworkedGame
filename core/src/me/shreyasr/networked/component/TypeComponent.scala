package me.shreyasr.networked.component

import com.badlogic.ashley.core.Component
import me.shreyasr.networked.component.TypeComponent.Type

import scala.reflect.ClassTag

class TypeComponent(t: Type) extends Component {

  def get = t

  def is[T <: Type: ClassTag]: Boolean = {
    implicitly[ClassTag[T]].runtimeClass.isInstance(t)
  }
}

object TypeComponent {

  sealed trait Type
    trait GameEntity extends Type
      trait Ship extends GameEntity
        object Ship extends Ship
      trait Laser extends GameEntity
        object Laser extends Laser
}
