package me.shreyasr.networked.desktop

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import me.shreyasr.networked.NetworkedGame

object DesktopLauncher {

  def main(arg: Array[String]) {
    val config: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
    config.vSyncEnabled = false
    config.foregroundFPS = 60
    config.backgroundFPS = 60
    new LwjglApplication(new NetworkedGame, config)
  }
}
