package modules.visuals.textureHandling

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import modules.visuals.subTexture.ScalingType
import modules.visuals.subTexture.SubTexture


object TextureCache {
    private var textureList = mutableMapOf<FileHandle,Texture>()
    private var textureAtlasList = mutableMapOf<FileHandle, TextureAtlas>()
    private var pixmapTextures = mutableListOf<Texture>() //this is for disposing

    fun addToPixmapTextures(t: Texture){
        pixmapTextures.add(t)
    }





    fun jsonOpener(){
        val s = Gdx.files.internal("pidgeon/pigeon_poop_export2.json").readString()
        val o = mapOf<String,Any>()

        //JsonReader().parse(s)


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
    }
}