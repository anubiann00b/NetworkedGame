package me.shreyasr.networked

import com.badlogic.ashley.core.{Engine, Entity}
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import me.shreyasr.networked.NetworkedGame.Res
import me.shreyasr.networked.component.RenderDataComponent
import me.shreyasr.networked.system.render.{MainRenderSystem, PostRenderSystem, PreBatchRenderSystem, PreRenderSystem}
import me.shreyasr.networked.system.render.util.RenderSystem
import me.shreyasr.networked.util.Asset

import scala.collection.JavaConverters._

class NetworkedGame extends ApplicationAdapter {

  lazy val res = new Res()
  import res._

	override def create() {
    Asset.loadAll(assetManager)

    val p = { var i = 0; () => { i += 1; i} }
    engine.addEntity(new Entity().add(new RenderDataComponent(Asset.FIGHTER, 95, 141, 0.75f)))

    engine.addSystem(new PreRenderSystem(p()))
    engine.addSystem(new PreBatchRenderSystem(p(), res))
    engine.addSystem(new MainRenderSystem(p(), res))
    engine.addSystem(new PostRenderSystem(p(), res))
	}

	override def render() {
    engine.update(Gdx.graphics.getRawDeltaTime * 1000)
    engine.getSystems.asScala
      .filter(_.isInstanceOf[RenderSystem])
      .foreach(_.update(Gdx.graphics.getRawDeltaTime * 1000))
	}
}

object NetworkedGame {
  class Res {
    val engine = new Engine
    val batch = new SpriteBatch
    val shape = new ShapeRenderer
    val assetManager = new AssetManager
  }
}
