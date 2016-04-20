package me.shreyasr.networked.util

import me.shreyasr.networked.component.InputDataComponent

import scala.collection.mutable

class InputDataQueue {

  var list = mutable.ListBuffer[(Long, InputDataComponent)]()
  list += -Long.MaxValue -> new InputDataComponent

  def addInputData(time: Long, inputDataComponent: InputDataComponent): Unit = {
    (time -> inputDataComponent) +=: list
    list = list.sortBy(-_._1)
    if (list.size > 20) list.remove(list.size - 1)
  }

  def getInput(time: Long): InputDataComponent = {
    list.find(_._1 < time-100).get._2
  }
}
