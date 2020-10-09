package modules.visuals.fromPath

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import modules.visuals.ScalingType
import modules.visuals.SubTexture
import java.lang.Exception
import kotlin.concurrent.thread

object TextureCache {
    private var textureList = mutableMapOf<FileHandle,Texture>()
    private var textureAtlasList = mutableMapOf<FileHandle, TextureAtlas>()



    fun jsonOpener(){
        
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

    fun dispose(){
        textureList.forEach {
            it.value.dispose()
        }
    }
}