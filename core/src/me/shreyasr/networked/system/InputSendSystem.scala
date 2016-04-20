package me.shreyasr.networked.system

import com.badlogic.ashley.core.EntitySystem
import me.shreyasr.networked.NetworkedGame
import me.shreyasr.networked.component.InputDataComponent
import me.shreyasr.networked.util.network.PacketToServer
import me.shreyasr.networked._

class InputSendSystem(priority: Int, res: NetworkedGame.ClientRes) extends EntitySystem(priority) {

  override def update(deltaTime: Float) {
    res.client.sendUDP(new PacketToServer(res.player.id, InputDataComponent.create))
  }
}
