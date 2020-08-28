package modules.visuals

import modules.LcsModule.GetLcs
import modules.LcsModule.LcsVariable as lv
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class SingleTexture(private val path: String, w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f),visualSize: VisualSize= VisualSize.STATIC): OmniVisual(w=w,h=h,visualSize = visualSize) {
    init{
        width = w
        height = h
    }
    private var s: Sprite = createSprite()

    override fun relocate(x: lv, y: lv) {
        cX = x
        cY = y
        s.x = x.asPixel() - s.width/2
        s.y = y.asPixel() - s.height/2
    }

    override fun fitElement(w: lv, h: lv) {
        width = w
        height = h
        s.setSize(width.asPixel(),height.asPixel())
    }

    override fun fitWithRatio(w: modules.LcsModule.LcsVariable, h: modules.LcsModule.LcsVariable) {
        width=w
        height=h
        val rat = (width/originalWidth).asLcs().coerceAtMost((height/originalHeight).asLcs())
        s.setSize(originalWidth.asPixel()*rat,originalHeight.asPixel()*rat)
    }

    override fun draw(batch: SpriteBatch) {
        s.draw(batch)
    }

    override fun changeActiveSprite(ns: Int) {}
    override fun update() {}

    override fun recolour(c: Color) {
        s.color = c
    }

    override fun copy(): OmniVisual {
        SingleTexture(path,width,height).also {
            it.relocate(cX,cY)
            return it
        }
    }

    override fun dispose() {
        s.texture.dispose()
    }


    private fun resizePixmap(px: Pixmap,w: lv,h: lv): Pixmap{
        return Pixmap((w.asPixel()).toInt(), (h.asPixel()).toInt(), px.format).also { it2 ->
            it2.filter = Pixmap.Filter.NearestNeighbour
            it2.blending = Pixmap.Blending.None
            it2.drawPixmap(px, 0, 0, px.width, px.height, 0, 0, it2.width, it2.height)
        }
    }

    private fun createSprite(): Sprite {
        Pixmap(Gdx.files.internal(path)).also {
            originalWidth = GetLcs.byPixel(it.width.toFloat())
            originalHeight = GetLcs.byPixel(it.height.toFloat())
            when(visualSize){
                VisualSize.STATIC,VisualSize.SCALE_ORIGINAL->{
                    width = originalWidth
                    height=originalHeight
                    return Sprite(Texture(it)).apply{
                        it.dispose()
                    }
                }
                VisualSize.FIT_ELEMENT->{
                    resizePixmap(it,width,height).also { it2->
                        return Sprite(Texture(it2)).apply{
                            it.dispose()
                            it2.dispose()
                        }
                    }
                }
                VisualSize.FIT_WITH_RATIO->{
                    val rat = (width/originalWidth).asLcs().coerceAtMost((height/originalHeight).asLcs())
                    resizePixmap(it,originalWidth*rat,originalHeight*rat).also { it2->
                        return Sprite(Texture(it2)).apply{
                            it.dispose()
                            it2.dispose()
                        }
                    }
                }
            }

        }
    }
}