package me.shreyasr.networked.component

import java.util.Random

import com.badlogic.ashley.core.Component

class IdComponent(val id: Int) extends Component {

  override def equals(o: Any): Boolean = o.isInstanceOf[IdComponent] && o.asInstanceOf[IdComponent].id == id
  override def hashCode: Int = id
  override def toString: String = "%05d" format (id % 100000)
}

object IdComponent {
  private val random: Random = new Random

  def randomId(): Int = random.nextInt
}
