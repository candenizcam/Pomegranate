package modules.visuals.fromTiles

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array
import com.pungo.modules.visuals.Tile
import com.pungo.modules.visuals.TileRenderer
import modules.visuals.OmniVisual
import modules.visuals.ScalingType
import modules.visuals.SubTexture
import modules.visuals.fromPath.SingleTexture

object TileMapOpener {
    var tileAtlasList = mutableMapOf<FileHandle, TextureAtlas>()



    private fun openAtlas(tilesPath: FileHandle, region: String): Array<Sprite> {
        val relevantAtlas =  if(tileAtlasList.containsKey(tilesPath)){
            tileAtlasList[tilesPath] as TextureAtlas
        }else{
            tileAtlasList[tilesPath] = TextureAtlas(tilesPath)
            tileAtlasList[tilesPath] as TextureAtlas
        }
        return (if (region=="") relevantAtlas.createSprites() else relevantAtlas.createSprites(region))
    }

    fun openTileRenderer(mapPath: FileHandle, tilesPath: FileHandle,region: String): TileRenderer {
        val tileRows = mapPath.readString().lines().dropLast(1)
        val sizeInfo = tileRows[0].split(",")
        val tr = TileRenderer(sizeInfo[0].toInt(), sizeInfo[1].toInt())//, GetLcsRect.ofFullScreen())
        tr.mapData = tileRows.subList(1,tileRows.size).mapIndexed {index,tile->
            val tileData = tile.drop(1).dropLast(1).split(",")
            Tile("t(${tileData[1]},${tileData[0]})",tileData[2],sizeInfo[0].toInt() - 1 - tileData[0].toFloat(),tileData[1].toFloat())
        }.toMutableList()
        tr.typesList = openAtlas(tilesPath,region).map {
            val s = SubTexture(it)
            SingleTexture(s, scalingType = ScalingType.FIT_ELEMENT)
        }.mapIndexed { index, it ->
            Pair<String, OmniVisual>("tile_$index", it)
        }.toMutableList()
        return tr

    }

    fun dispose(){
        tileAtlasList.forEach{
            it.value.dispose()
        }
    }


}