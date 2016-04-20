package me.shreyasr.networked.util

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import enumeratum.values.{IntEnum, IntEnumEntry}

sealed abstract class Asset(val value: Int, val file: String) extends IntEnumEntry {

  def get: Texture = Asset.assetManager.get[Texture](file)
}

object Asset extends IntEnum[Asset] {

  var assetManager: AssetManager = null

  def loadAll(assetManager: AssetManager) {
    Asset.assetManager = assetManager
    for (asset <- values) {
      assetManager.load(asset.file, classOf[Texture])
    }
    assetManager.finishLoading()
  }

  case object FIGHTER extends Asset(1, "fighter.png")
  case object SPACE extends Asset(2, "space.png")

  override def values = findValues
}
