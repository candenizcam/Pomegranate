package modules.visuals

import com.badlogic.gdx.graphics.Color
import modules.LcsModule.GetLcs
import modules.LcsModule.LcsVariable as lv
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas

open class AtlasTexture(private val path: String, val region: String="", w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f),visualSize: VisualSize= VisualSize.STATIC): OmniVisual(w=w,h=h,visualSize = visualSize) {
    init {
        width = w
        height = h
    }
    var sprites = createSprites()
    var activeFrame = 0


    override fun relocate(x: lv, y: lv) {
        cX = x
        cY = y
        sprites.forEach {
            it.x = x.asPixel() - it.width/2
            it.y = y.asPixel() - it.height/2
        }
    }

    override fun fitElement(w: lv, h: lv) {
        width = w
        height = h
        sprites.forEach {
            it.setSize(width.asPixel(),height.asPixel())
        }
        relocate(cX,cY)
    }

    override fun fitWithRatio(w: modules.LcsModule.LcsVariable, h: modules.LcsModule.LcsVariable) {
        width=w
        height=h
        sprites.forEach {
            val rat = (width/originalWidth).asLcs().coerceAtMost((height/originalHeight).asLcs())
            imageWidth = originalWidth*rat
            imageHeight = originalHeight*rat
            it.setSize(originalWidth.asPixel()*rat,originalHeight.asPixel()*rat)
        }
        relocate(cX,cY)


    }

    override fun draw(batch: SpriteBatch) {
        sprites[activeFrame].draw(batch)
    }

    override fun changeActiveSprite(ns: Int) {
        activeFrame = ns
    }

    override fun update() {}


    private fun createSprites(): List<Sprite> {
        var xOrgSize = 0f
        var yOrgSize = 0f

        mutableListOf<Sprite>().also{
            TextureAtlas(path).also{it2->
                if(region==""){
                    it2.createSprites().also{it3->
                        it3.forEach { it4->
                            xOrgSize = it4.width.coerceAtLeast(xOrgSize)
                            yOrgSize = it4.height.coerceAtLeast(yOrgSize)
                            when(visualSize){
                                VisualSize.STATIC,VisualSize.SCALE_ORIGINAL->{width=GetLcs.byPixel(xOrgSize);height=GetLcs.byPixel(yOrgSize)}
                                VisualSize.FIT_ELEMENT->{it4.setSize(width.asPixel(),height.asPixel())}
                                VisualSize.FIT_WITH_RATIO->{
                                    val rat = (width/it4.width).asLcs().coerceAtMost((height/it4.height).asLcs())
                                    it4.setSize(it4.width*rat,it4.height*rat)
                                }
                            }
                            //it4.setSize(width.asPixel(),height.asPixel())
                            it.add(it4)
                        }
                    }
                } else{
                    it2.createSprites(region).also{it3->
                        it3.forEach { it4->
                            xOrgSize = it4.width.coerceAtLeast(xOrgSize)
                            yOrgSize = it4.height.coerceAtLeast(yOrgSize)
                            when(visualSize){
                                VisualSize.STATIC,VisualSize.SCALE_ORIGINAL->{width=GetLcs.byPixel(xOrgSize);height=GetLcs.byPixel(yOrgSize)}
                                VisualSize.FIT_ELEMENT->{it4.setSize(width.asPixel(),height.asPixel())}
                                VisualSize.FIT_WITH_RATIO->{
                                    val rat = (width/it4.width).asLcs().coerceAtMost((height/it4.height).asLcs())
                                    it4.setSize(it4.width*rat,it4.height*rat)
                                }
                            }
                            it.add(it4)
                        }
                    }
                }
            }
            originalHeight = GetLcs.byPixel(yOrgSize)
            originalWidth = GetLcs.byPixel(xOrgSize)
            return it.toList()
        }
    }

    override fun recolour(c: Color) {
        sprites.forEach {
            it.color = c
        }
    }

    override fun copy(): OmniVisual {
        (if(this is StepAtlasAnimation){
            StepAtlasAnimation(path, region, width, height, step,visualSize)
        }else if (this is TimedAtlasAnimation){
            TimedAtlasAnimation(path, region, width, height, fps,visualSize)
        }else{
            AtlasTexture(path, region,width,height,visualSize)
        }).also {
            it.relocate(cX,cY)
            return it
        }
    }

    override fun dispose() {
        sprites.forEach {
            it.texture.dispose()
        }
    }


}