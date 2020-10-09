package modules.visuals

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import modules.basic.geometry.FastGeometry
import modules.basic.geometry.Rectangle
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect

class SubTexture: Sprite {
    private var originalURect: Rectangle
    var clipRect: Rectangle = FastGeometry.unitSquare() //this class is a rectangle in originalURect so unity means same as original u rect
        set(value) {
            field = value
            originalURect.getSubRectangle(value).also{
                u = it.left
                u2 = it.right
                v = it.bottom
                v2 = it.top
            }
        }
    var visualSizeData: VisualSizeData

    constructor(s: Sprite): super(s){
        originalURect = Rectangle(s.u,s.u2,s.v,s.v2)
        visualSizeData = if(s is SubTexture){
            s.visualSizeData.copy()
        } else{
            VisualSizeData(GetLcsRect.byParameters(GetLcs.byPixel(s.width), GetLcs.byPixel(s.height)))
        }
    }

    constructor(t: Texture): super(t){
        originalURect = FastGeometry.unitSquare()
        visualSizeData =  VisualSizeData(GetLcsRect.byParameters(GetLcs.byPixel(t.width), GetLcs.byPixel(t.height)))
    }

    constructor(t: TextureRegion): super(t){
        originalURect = Rectangle(t.u,t.u2,t.v,t.v2)
        visualSizeData =  VisualSizeData(GetLcsRect.byParameters(GetLcs.byPixel(t.texture.width*(t.u2-t.u)), GetLcs.byPixel(t.texture.height*(t.v2-t.v))))
    }


    /** While this is not the most solid code i've ever written i think this is the most efficient way of doing this
     * Also, like, shouldn't broccoli farming be difficult?
     * They flower at different times from their sprouting and they should be collected right before that which should be like, one or two days
     * So there should be people looking at every broccoli sprout determining if they should be collected and since plants don't die right after they are collected...
     * ...they must be doing something to prevent the flowering.
     */
    fun draw(batch: Batch?, alphaModulation: Float, block: LcsRect) {
        visualSizeData.updateImageBlock(block)
        //activeURect = visualSizeData.clipRectangle
        setSize(visualSizeData.imageBlock.width.asPixel(),visualSizeData.imageBlock.height.asPixel())
        setCenter(visualSizeData.imageBlock.cX.asPixel(),visualSizeData.imageBlock.cY.asPixel())
        super.draw(batch,alphaModulation)
    }

    fun setScaling(scalingType: ScalingType = visualSizeData.scalingType, scaleFactor: Float = visualSizeData.scaleFactor){
        visualSizeData = visualSizeData.copy(scalingType = scalingType, scaleFactor = scaleFactor)
    }







}