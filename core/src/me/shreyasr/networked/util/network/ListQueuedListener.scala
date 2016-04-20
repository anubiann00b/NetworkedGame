package me.shreyasr.networked.util.network

import com.esotericsoftware.kryonet.Listener
import com.esotericsoftware.kryonet.Listener.QueuedListener

import scala.collection.mutable

class ListQueuedListener(listener: Listener = null) extends QueuedListener(listener) {

  val list = new mutable.MutableList[Runnable]

  override protected def queue(runnable: Runnable): Unit = list += runnable

  def run() = {
    list.foreach(_.run)
    list.clear
  }
}
