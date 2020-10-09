package modules.visuals.fromPath

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import java.lang.Exception
import kotlin.concurrent.thread

object TextureCache {
    private var textureList = mutableMapOf<FileHandle,Texture>()
    private var textureAtlasList = mutableMapOf<FileHandle, TextureAtlas>()


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