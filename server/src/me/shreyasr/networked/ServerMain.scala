package me.shreyasr.networked

import java.net.BindException

import com.esotericsoftware.kryonet.{Connection, KryoSerialization, Listener, Server}
import com.twitter.chill.ScalaKryoInstantiator
import me.shreyasr.networked.component.{InputDataComponent, TypeComponent}
import me.shreyasr.networked.system.UpdateSystem
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData
import me.shreyasr.networked.util.network.{ListQueuedListener, PacketToClient, PacketToServer}
import me.shreyasr.networked.util.{EntityFactory, KryoRegistrar}

import scala.collection.mutable

object ServerMain extends Listener {

  def main(args: Array[String]) {
    ServerMain.run()
  }

  class ServerRes extends NetworkedGame.BaseRes {
    val server = new Server(8192, 2048, new KryoSerialization(new ScalaKryoInstantiator().newKryo()))
    val listener = new ListQueuedListener(ServerMain)
    val idMap = new mutable.HashMap[Int, Int]
    val inputs = new mutable.HashMap[Int, InputDataComponent]
  }

  val res = new ServerRes
  import res._

  KryoRegistrar.register(server.getKryo)
  server.start()
  try {
    server.bind(54555, 54777)
  } catch {
    case e: BindException => System.exit(1)
  }
  server.addListener(listener)

  val p = { var i = 0; () => { i += 1; i} }
  engine.addSystem(new UpdateSystem(p(), res))

  def run() = {
    var startTime = 0l
    while (true) {
      startTime = System.currentTimeMillis()
      listener.run()

      engine.update(16)

//      inputs.foreach{case (entityId: Int, input: InputDataComponent) =>
//          val entityOpt = engine.getById(entityId)
//          if (entityOpt.isDefined) entityOpt.get.add(input)
//      }

      server.sendToAllUDP(new PacketToClient(engine.entities
        .filter(_.is[TypeComponent.Ship])
        .map(new EntityUpdateData(_)).toArray))

      val timeDiff = System.currentTimeMillis() - startTime
//      Thread.sleep(16-timeDiff)
      Thread.sleep(12)
    }
  }

  override def received(conn: Connection, obj: scala.Any) {
    obj match {
      case packet: PacketToServer =>
        idMap += conn.getID -> packet.entityId
        if (engine.getById(packet.entityId).isEmpty) {
          engine.addEntity(EntityFactory.createPlayer(packet.entityId))
        }
        engine.getById(packet.entityId).get.add(packet.inputData)
      case _ =>
    }
  }

  override def disconnected(conn: Connection) {
    val entityOption = idMap.get(conn.getID)
    if (entityOption.isDefined) {
      engine.removeEntity(engine.getById(entityOption.get).get)
      idMap -= conn.getID
    }
  }
}
