package me.shreyasr.networked

import com.esotericsoftware.kryonet.{Connection, Listener, Server}
import me.shreyasr.networked.component.StateDataComponent
import me.shreyasr.networked.system.UpdateSystem
import me.shreyasr.networked.util.network.{ListQueuedListener, PacketToClient, PacketToServer}
import me.shreyasr.networked.util.{EntityFactory, KryoRegistrar}

import scala.collection.JavaConverters._
import scala.collection.mutable

object ServerMain extends Listener {

  def main(args: Array[String]) {
    ServerMain.run()
  }

  class ServerRes extends NetworkedGame.BaseRes {
    val server = new Server
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

      engine.getEntities.asScala
        .filter(_.has[StateDataComponent])
        .foreach(e => server.sendToAllUDP(new PacketToClient(e.id, e.get[StateDataComponent])))

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
