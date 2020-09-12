package modules.visuals

import com.badlogic.gdx.graphics.Color
import modules.lcsModule.GetLcs
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect

open class AtlasTexture(private val path: String, val region: String = "", var fitAll: Boolean=false, block: LcsRect = GetLcsRect.ofCentreSquare(), visualSize: VisualSize = VisualSize.STATIC, scaleFactor: Float = 1f) : OmniVisual(block, visualSize = visualSize,scaleFactor = scaleFactor) {
    private var ratioToFirst = mutableListOf<Pair<Float,Float>>()
    protected var sprites = createSprites()
    protected var activeFrame = 0


    override fun draw(batch: SpriteBatch) {
        sprites[activeFrame].draw(batch)
    }

    override fun changeActiveSprite(ns: Int) {
        activeFrame = ns
    }

    override fun update() {}


    private fun createSprites(): List<Sprite> {
        val msl = mutableListOf<Sprite>()
        mutableListOf<Sprite>().also {
            TextureAtlas(path).also {textureAtlas ->
                var firstSize =  GetLcsRect.ofCentreSquare()
                (if(region=="") textureAtlas.createSprites() else textureAtlas.createSprites(region)).forEachIndexed{index, sprite->
                    if(index==0){
                        firstSize = GetLcsRect.byParameters(GetLcs.byPixel(sprite.width),GetLcs.byPixel(sprite.height))
                        originalBlock = firstSize
                    }
                    ratioToFirst.add(Pair(sprite.width/firstSize.width.asPixel(),sprite.height/firstSize.height.asPixel()))
                    msl.add(sprite)
                }
            }
            return msl.toList()
        }
    }

    override fun recolour(c: Color) {
        sprites.forEach {
            it.color = c
        }
    }

    override fun copy(): OmniVisual {
        return when (this) {
            is StepAtlasAnimation -> {
                StepAtlasAnimation(path, region, fitAll, block, step, visualSize,scaleFactor)
            }
            is TimedAtlasAnimation -> {
                TimedAtlasAnimation(path, region, fitAll, block, fps, visualSize,scaleFactor)
            }
            else -> {
                AtlasTexture(path, region, fitAll, block, visualSize,scaleFactor)
            }
        }
    }

    override fun dispose() {
        sprites.forEach {
            it.texture.dispose()
        }
    }

    override fun updateVisual() {
        val changeSize = (sprites[0].width!=imageBlock.width.asPixel())||(sprites[0].height!=imageBlock.height.asPixel())
        sprites.forEachIndexed() {index, it ->
            if(changeSize){
                if(fitAll){
                    it.setSize(imageBlock.width.asPixel(),imageBlock.height.asPixel())
                } else{
                    it.setSize(imageBlock.width.asPixel()*ratioToFirst[index].first,imageBlock.height.asPixel()*ratioToFirst[index].second)
                }
            }
            it.x = imageBlock.cX.asPixel() - it.width / 2
            it.y = imageBlock.cY.asPixel() - it.height / 2
        }
    }


}