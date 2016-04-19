package me.shreyasr.networked.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import me.shreyasr.networked.NetworkedGame

object DesktopLauncher {

  def main(arg: Array[String]) {
    val config: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
    new LwjglApplication(new NetworkedGame, config)
  }
}
