package modules.visuals.textureHandling

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.pomegranate.AnimateJson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import modules.lcsModule.GetLcsRect
import modules.visuals.subTexture.ScalingType
import modules.visuals.subTexture.SubTexture


object TextureCache {
    private var textureList = mutableMapOf<FileHandle,Texture>()
    private var textureAtlasList = mutableMapOf<FileHandle, TextureAtlas>()
    private var jsonAtlasList = mutableMapOf<FileHandle,AnimateJson>()
    private var pixmapTextures = mutableListOf<Texture>() //this is for disposing

    fun addToPixmapTextures(t: Texture){
        pixmapTextures.add(t)
    }





    fun jsonOpener(path: FileHandle,region: String?=null): MultipleTexture {
        val at : AnimateJson
        if(jsonAtlasList.containsKey(path)){
            at = jsonAtlasList[path]!!
        }else{
            at= AnimateJson(path)
            jsonAtlasList[path] = at
        }
        return MultipleTexture(at.getSubTextures(region))
    }




    fun atlasOpener(path: FileHandle, region : String? = null): MultipleTexture {
        openAtlasTexture(path).also{
            val that = mutableListOf<SubTexture>()
            (if(region == null){it.createSprites()} else {it.createSprites(region)}).forEach {it2->
                that.add(SubTexture(it2))
            }
            return MultipleTexture(that)
        }
    }


    fun openAtlasTexture(path: FileHandle): TextureAtlas{
        return if(textureAtlasList.containsKey(path)){
            textureAtlasList[path] as TextureAtlas
        }else{
            textureAtlasList[path] = TextureAtlas(path)
            textureAtlasList[path] as TextureAtlas
        }
    }


    fun openTexture(path: FileHandle): Texture {
        return if(textureList.containsKey(path)){
            textureList[path] as Texture
        }else{
            textureList[path] = Texture(path)
            textureList[path] as Texture
        }
    }

    fun subTextureFromPath(path: FileHandle, colour: Color = Color.WHITE, scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f): SubTexture {
        return SubTexture(openTexture(path)).also {
            it.color = colour
            it.setScaling(scalingType,scaleFactor)
        }
    }


    /* I hope to delete this baby
    /** Finds the path of a given texture
     * it is not as insane as it sounds sena, its actually very logical
     */
    fun getTexturePath(t: Texture): FileHandle {
        try{
            return textureList.filterValues { it==t }.keys.toList()[0]
        } catch (e: Exception){
            return textureAtlasList.filterValues { it==t }.keys.toList()[0]
        }

    }

     */

    fun dispose(){
        textureList.forEach {
            it.value.dispose()
        }
        textureAtlasList.forEach{
            it.value.textures.forEach { it2->
                it2.dispose()
            }
        }
        pixmapTextures.forEach {
            it.dispose()
        }
        jsonAtlasList.forEach{
            it.value.dispose()

        }
    }
}