package modules.visuals.fromPath

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.basic.geometry.Rectangle
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.visuals.OmniVisual
import modules.visuals.SubTexture
import modules.visuals.ScalingType

class SingleTexture: OmniVisual {
    var subTexture: SubTexture
        private set
    private var path: FileHandle
    constructor(path: FileHandle, colour: Color = Color.WHITE, scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f): super() {
        this.path = path
        subTexture = SubTexture(TextureCache.openTexture(path)).also { st->
            st.color = colour
            st.setScaling(scalingType,scaleFactor)
            block = GetLcsRect.byParameters(GetLcs.byPixel(st.width), GetLcs.byPixel(st.height))
            // visualSizeData.copy(originalRect = this.block.copy())
        }
    }

    constructor(sprite: SubTexture, colour: Color=Color.WHITE, scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f): super(){
        subTexture = SubTexture(sprite)
        //subTexture.color = colour
        //subTexture.setScaling(scalingType,scaleFactor)
        block = GetLcsRect.byParameters(GetLcs.byPixel(sprite.width), GetLcs.byPixel(sprite.height))
        // visualSizeData.copy(originalRect = this.block.copy())
        this.path = TextureCache.getTexturePath(subTexture.texture)
    }

    override fun getOriginalRect(): LcsRect {
        return subTexture.visualSizeData.originalRect.copy()
    }

    override fun getImageRect(block: LcsRect?): LcsRect{
        subTexture.visualSizeData.updateImageBlock(block ?: this.block)
        return subTexture.visualSizeData.imageBlock

    }

    override fun setClippingRect(r: Rectangle) {
        subTexture.clipRect = r
    }



    override fun draw(batch: SpriteBatch, alpha: Float) {
        // visualSizeData.updateImageBlock(block)
        subTexture.draw(batch,alpha,block)
    }

    override fun changeActiveSprite(ns: Int) {}
    override fun update() {}

    override fun recolour(c: Color) {
        subTexture.color = c
    }

    override fun copy(): OmniVisual {
        return SingleTexture(subTexture).also {
            it.reBlock(block)
        }
        /*
        SingleTexture(path, subTexture.color, visualSizeData.scalingType, visualSizeData.scaleFactor).also {
            it.reBlock(block)
            return it
        }

         */
    }

    override fun dispose() {
    }

    override fun setFlip(x: Boolean, y: Boolean) {
        subTexture.flip(x,y)
    }
}