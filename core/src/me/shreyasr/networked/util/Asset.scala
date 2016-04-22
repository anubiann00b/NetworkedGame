package me.shreyasr.networked.util

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import enumeratum.values.{ShortEnum, ShortEnumEntry}

sealed abstract class Asset(val value: Short, val file: String) extends ShortEnumEntry {

  def get: Texture = Asset.assetManager.get[Texture](file)
}

object Asset extends ShortEnum[Asset] {

  var assetManager: AssetManager = null

  def loadAll(assetManager: AssetManager) {
    Asset.assetManager = assetManager
    for (asset <- values) {
      assetManager.load(asset.file, classOf[Texture])
    }
    assetManager.finishLoading()
  }

  case object FIGHTER extends Asset(1, "fighter.png")
  case object LASER extends Asset(2, "laser_blue.png")
  case object SPACE extends Asset(3, "space.png")

  override def values = findValues
}
