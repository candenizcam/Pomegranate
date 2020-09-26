package modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect

class CustomPixmap(val pxMap: Pixmap,c: Color,visualSize: VisualSize=VisualSize.FIT_ELEMENT, scaleFactor: Float = 1f): OmniVisual(visualSize = visualSize,scaleFactor = scaleFactor) {
    private var s: Sprite = Sprite(Texture(pxMap)).also {
        it.color = c
        originalBlock = GetLcsRect.byParameters(GetLcs.byPixel(it.width), GetLcs.byPixel(it.height))
        imageBlock = originalBlock.copy()
        block = originalBlock.copy()
        if(visualSize==VisualSize.STATIC){
            it.setSize(originalBlock.width.asPixel()*scaleFactor,originalBlock.height.asPixel()*scaleFactor)
        }
    } //creates a block with the relevant colour

    /** Draws the batch
     */
    override fun draw(batch: SpriteBatch, alpha: Float) {
        s.draw(batch,alpha)
    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {}

    override fun recolour(c: Color) {
        s.color = c
    }

    override fun copy(): OmniVisual {
        return CustomPixmap(pxMap,s.color,visualSize,scaleFactor)
    }

    override fun dispose() {
        println("disposing")
        s.texture.dispose()
        try{
            pxMap.dispose()
        }catch (e: Exception){

        }
    }

    override fun updateVisual() {
        if ((s?.width != imageBlock.width.asPixel()) || (s?.height != imageBlock.height.asPixel())) {
            s?.setSize(imageBlock.width.asPixel(), imageBlock.height.asPixel())
        }
        s?.x = imageBlock.cX.asPixel() - s?.width / 2
        s?.y = imageBlock.cY.asPixel() - s?.height / 2
    }
}