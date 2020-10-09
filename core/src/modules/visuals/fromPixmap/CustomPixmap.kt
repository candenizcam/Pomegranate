package modules.visuals.fromPixmap

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.visuals.OmniVisual
import modules.visuals.ScalingType
import modules.visuals.SubTexture

class CustomPixmap(val pxMap: Pixmap, var color: Color = Color.WHITE, scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f): OmniVisual() {
    private var subTexture= SubTexture(Texture(pxMap)).also {
        block = GetLcsRect.byParameters(GetLcs.byPixel(it.width),GetLcs.byPixel(it.height))
        //visualSizeData = visualSizeData.copy(originalRect = block)

    }
    private var flip = Pair(false,false)


    /** Draws the batch
     */
    override fun draw(batch: SpriteBatch, alpha: Float) {
        subTexture.visualSizeData.updateImageBlock(block)
        subTexture.draw(batch,alpha)
        //batch.color = color
        //val imageBlock = visualSizeData.imageBlock
        //val originalBlock = visualSizeData.originalRect
        //batch.draw(subTexture,imageBlock.wStart.asPixel(),imageBlock.hStart.asPixel(),imageBlock.width.asPixel(),imageBlock.height.asPixel(),0,0,originalBlock.width.asPixel().toInt(),originalBlock.height.asPixel().toInt(),flip.first,flip.second)
        //batch.color = Color.WHITE
    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {}

    override fun recolour(c: Color) {
        subTexture.color = color
        color = c
    }

    override fun setFlip(x: Boolean, y: Boolean) {
        flip = Pair(x,y)
    }

    override fun copy(): OmniVisual {
        return CustomPixmap(pxMap, color, subTexture.visualSizeData.scalingType, subTexture.visualSizeData.scaleFactor)
    }

    override fun dispose() {
        subTexture.texture.dispose()
        try{
            pxMap.dispose()
        }catch (e: Exception){

        }
    }
}