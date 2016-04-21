package me.shreyasr.networked

import com.esotericsoftware.kryonet.{Connection, KryoSerialization, Listener, Server}
import com.twitter.chill.ScalaKryoInstantiator
import me.shreyasr.networked.component.{InputDataComponent, StateDataComponent}
import me.shreyasr.networked.system.UpdateSystem
import me.shreyasr.networked.util.network.PacketToClient.EntityUpdateData
import me.shreyasr.networked.util.network.{ListQueuedListener, PacketToClient, PacketToServer}
import me.shreyasr.networked.util.{EntityFactory, KryoRegistrar}

import scala.collection.JavaConverters._
import scala.collection.mutable

object ServerMain extends Listener {

  def main(args: Array[String]) {
    ServerMain.run()
  }

  class ServerRes extends NetworkedGame.BaseRes {
    val server = new Server(8192, 2048, new KryoSerialization(new ScalaKryoInstantiator().newKryo()))
    val listener = new ListQueuedListener(ServerMain)
    val idMap = new mutable.HashMap[Int, Int]
  }

  val res = new ServerRes
  import res._

  KryoRegistrar.register(server.getKryo)
  server.start()
  server.bind(54555, 54777)
  server.addListener(listener)

  val p = { var i = 0; () => { i += 1; i} }
  engine.addSystem(new UpdateSystem(p(), res))

  def run() = {
    while (true) {
      listener.run()

      engine.update(16)

      val entityUpdateData: Array[EntityUpdateData] = engine.getEntities.asScala.toArray
        .map(e => new EntityUpdateData(e.id, e.get[StateDataComponent], e.get[InputDataComponent]))
      val packet = new PacketToClient(entityUpdateData, System.currentTimeMillis())
      server.sendToAllUDP(packet)

      Thread.sleep(16)
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
