package modules.visuals.fromPath

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.visuals.OmniVisual
import modules.visuals.VisualSize

open class AtlasTexture(private val path: FileHandle, val region: String = "", var colour: Color = Color.WHITE, var fitAll: Boolean=false, visualSize: VisualSize = VisualSize.STATIC, scaleFactor: Float = 1f) : OmniVisual(visualSize = visualSize,scaleFactor = scaleFactor) {
    private var ratioToFirst = mutableListOf<Pair<Float,Float>>()
    protected var textures = createTextures()
    protected var activeFrame = 0
    private lateinit var atlas: TextureAtlas
    var flip = Pair(false,false)


    override fun draw(batch: SpriteBatch, alpha: Float) {
        batch.color = colour
        val relBlock = imageBlock.resizeTo(ratioToFirst[activeFrame].first,ratioToFirst[activeFrame].second)
        batch.draw(textures[activeFrame],relBlock.wStart.asPixel(),relBlock.hStart.asPixel(),relBlock.width.asPixel(),relBlock.height.asPixel())
        batch.color = Color.WHITE
    }

    override fun changeActiveSprite(ns: Int) {
        activeFrame = ns
    }

    override fun update() {}


    private fun createTextures(): List<TextureRegion> {
        val msl = mutableListOf<TextureRegion>()



        mutableListOf<Sprite>().also {
            atlas = TextureCache.openAtlasTexture(path).also { textureAtlas ->
                var firstSize =  GetLcsRect.ofCentreSquare()
                (if(region=="") textureAtlas.regions else textureAtlas.findRegions(region)).forEachIndexed{index, region->
                    if(index==0){
                        firstSize = GetLcsRect.byParameters(GetLcs.byPixel(region.originalWidth), GetLcs.byPixel(region.originalHeight))
                        originalBlock = firstSize
                    }
                    ratioToFirst.add(Pair(region.originalWidth/firstSize.width.asPixel(),region.originalHeight/firstSize.height.asPixel()))
                    msl.add(region)
                }
            }
            return msl.toList()
        }
    }

    override fun recolour(c: Color) {
        colour = c
    }

    override fun copy(): OmniVisual {
        return when (this) {
            is StepAtlasAnimation -> {
                StepAtlasAnimation(path, region, colour, fitAll, step, visualSize, scaleFactor).also {
                    it.reBlock(block)
                }
            }
            is TimedAtlasAnimation -> {
                TimedAtlasAnimation(path, region, colour, fitAll, fps, visualSize, scaleFactor).also {
                    it.reBlock(block)
                }
            }
            else -> {
                AtlasTexture(path, region, colour, fitAll, visualSize, scaleFactor).also {
                    it.reBlock(block)
                }
            }
        }
    }

    override fun dispose() {
        atlas.dispose()


    }

    override fun updateVisual() {
    }


    override fun setFlip(x: Boolean, y: Boolean) {
        flip = Pair(x,y)

    }


}