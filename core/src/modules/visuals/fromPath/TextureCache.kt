package modules.visuals.fromPath

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

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

    fun dispose(){
        textureList.forEach {
            it.value.dispose()
        }
    }
}