package me.shreyasr.networked.component

import java.util.Random

import com.badlogic.ashley.core.Component

class IdComponent extends Component {

  val id: Double = IdComponent.random.nextDouble

  override def equals(o: Any): Boolean = o.isInstanceOf[IdComponent] && o.asInstanceOf[IdComponent].id == id

  override def hashCode: Int = {
    val temp = java.lang.Double.doubleToLongBits(id)
    (temp ^ (temp >>> 32)).toInt
  }

  override def toString: String = (id * 10000).toInt.toString
}

object IdComponent {
  private val random: Random = new Random
}
