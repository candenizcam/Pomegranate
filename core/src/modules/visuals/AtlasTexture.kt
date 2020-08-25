package modules.visuals

import com.badlogic.gdx.graphics.Color
import modules.LcsModule.GetLcs
import modules.LcsModule.LcsVariable as lv
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas

open class AtlasTexture(private val path: String, val region: String="", w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f)): OmniVisual(w=w,h=h) {
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

    override fun resize(w: lv, h: lv) {
        if(!((w==width)&&(h==height))){
            width = w
            height = h
            sprites.forEach {
                it.setSize(width.asPixel(),height.asPixel())
            }
            relocate(cX,cY)
        }
    }

    override fun draw(batch: SpriteBatch) {
        sprites[activeFrame].draw(batch)
    }

    override fun changeActiveSprite(ns: Int) {
        activeFrame = ns
    }

    override fun update() {}

    private fun createSprites(): List<Sprite> {

        mutableListOf<Sprite>().also{
            TextureAtlas(path).also{it2->
                if(region==""){
                    it2.createSprites().also{it3->
                        it3.forEach { it4->
                            it4.setSize(width.asPixel(),height.asPixel())
                            it.add(it4)
                        }
                    }
                } else{
                    it2.createSprites(region).also{it3->
                        it3.forEach { it4->
                            it4.setSize(width.asPixel(),height.asPixel())
                            it.add(it4)
                        }
                    }
                }

            }
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
            StepAtlasAnimation(path, region, width, height, step)
        }else if (this is TimedAtlasAnimation){
            TimedAtlasAnimation(path, region, width, height, fps)
        }else{
            AtlasTexture(path, region,width,height)
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