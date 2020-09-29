package modules.visuals.fromPixmap

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.visuals.OmniVisual
import modules.visuals.VisualSize

class CustomPixmap(val pxMap: Pixmap, var color: Color = Color.WHITE, visualSize: VisualSize = VisualSize.FIT_ELEMENT, scaleFactor: Float = 1f): OmniVisual(visualSize = visualSize,scaleFactor = scaleFactor) {
    private var texture= Texture(pxMap).also {
        originalBlock = GetLcsRect.byParameters(GetLcs.byPixel(it.width),GetLcs.byPixel(it.height))
        imageBlock = originalBlock.copy()
        block = originalBlock.copy()


    }
    private var flip = Pair(false,false)


    /** Draws the batch
     */
    override fun draw(batch: SpriteBatch, alpha: Float) {
        batch.color = color
        batch.draw(texture,imageBlock.wStart.asPixel(),imageBlock.hStart.asPixel(),imageBlock.width.asPixel(),imageBlock.height.asPixel(),0,0,originalBlock.width.asPixel().toInt(),originalBlock.height.asPixel().toInt(),flip.first,flip.second)
    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {}

    override fun recolour(c: Color) {
        color = c
    }

    override fun setFlip(x: Boolean, y: Boolean) {
        flip = Pair(x,y)
    }

    override fun copy(): OmniVisual {
        return CustomPixmap(pxMap, color, visualSize, scaleFactor)
    }

    override fun dispose() {
        texture.dispose()
        try{
            pxMap.dispose()
        }catch (e: Exception){

        }
    }

    override fun updateVisual() {}
}