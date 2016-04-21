package me.shreyasr.networked

import java.io.IOException

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import com.esotericsoftware.kryonet.{Client, Connection, KryoSerialization, Listener}
import com.twitter.chill.ScalaKryoInstantiator
import me.shreyasr.networked.NetworkedGame.RenderingRes
import me.shreyasr.networked.system.render.util.{BasicRenderSystem, RenderSystem}
import me.shreyasr.networked.system.render.{MainRenderSystem, PostRenderSystem, PreBatchRenderSystem, PreRenderSystem}
import me.shreyasr.networked.system.{InputSendSystem, RenderDataUpdateSystem, UpdateSystem}
import me.shreyasr.networked.util.network.{ListQueuedListener, PacketToClient}
import me.shreyasr.networked.util.{Asset, EntityFactory, InputDataQueue, KryoRegistrar}

import scala.collection.JavaConverters._

object NetworkedGame {

  val GLOBAL_DELAY: Int = 200

  class RenderingRes extends ClientRes {
    val batch = new SpriteBatch
    val shape = new ShapeRenderer
    val assetManager = new AssetManager
    val font = new BitmapFont
  }

  class ClientRes extends BaseRes {
    var game: NetworkedGame = null
    val player = EntityFactory.createRenderablePlayer()
    val packetQueue = new InputDataQueue
    val client = new Client(8192, 2048, new KryoSerialization(new ScalaKryoInstantiator().newKryo()))
    val listener = new ListQueuedListener()
  }

  class BaseRes {
    val engine = new Engine
  }
}

class NetworkedGame extends ApplicationAdapter {

  lazy val res = new RenderingRes()
  import res._

	override def create() {
    Asset.loadAll(assetManager)

    engine.addEntity(player)

    val p = { var i = 0; () => { i += 1; i} }
    engine.addSystem(new InputSendSystem(p(), res))
    engine.addSystem(new UpdateSystem(p(), res))
    engine.addSystem(new RenderDataUpdateSystem(p(), res))

    engine.addSystem(new PreRenderSystem(p()))
    engine.addSystem(new PreBatchRenderSystem(p(), res))
    engine.addSystem(new MainRenderSystem(p(), res))
    engine.addSystem(new BasicRenderSystem(p()) {
      override def update(deltaTime: Float): Unit =
        font.draw(batch, (System.currentTimeMillis()-lastUpdateTime).toString, 16, Gdx.graphics.getHeight-16)
    })
    engine.addSystem(new PostRenderSystem(p(), res))

    KryoRegistrar.register(client.getKryo)
    listener.setListener(ClientListener)
    client.addListener(listener)
    client.start()
    try {
      client.connect(5000, "127.0.0.1", 54555, 54777)
    } catch {
      case ioe: IOException => println(ioe)
      case e: Exception => println(e)
    }
    println(client.getUdpPort)
	}

  var lastUpdateTime = System.currentTimeMillis()

	override def render() {
    listener.run()
    engine.update(Gdx.graphics.getRawDeltaTime * 1000)
    engine.getSystems.asScala
      .filter(_.isInstanceOf[RenderSystem])
      .foreach(_.update(Gdx.graphics.getRawDeltaTime * 1000))
	}

  object ClientListener extends Listener {
    override def received(conn: Connection, obj: scala.Any): Unit = {
      obj match {
        case packet: PacketToClient => packetQueue.addPacket(packet)
          val currentPacket = packetQueue.getPacket(System.currentTimeMillis())
          lastUpdateTime = currentPacket.time
          currentPacket.entityData.foreach(data => {
            if (engine.getById(data.entityId).isEmpty) {
              engine.addEntity(EntityFactory.createRenderablePlayer(data.entityId))
            }
            val entity = engine.getById(data.entityId).get
            entity.add(data.stateDataComponent)
            entity.add(data.inputDataComponent)
          })
        case _ =>
      }
    }
  }
}
