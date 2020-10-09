package modules.visuals.fromFont

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.modules.visuals.FontGenerator
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.visuals.OmniVisual
import modules.visuals.subTexture.ScalingType
import modules.visuals.subTexture.VisualSizeData
import modules.lcsModule.LcsVariable as lv

/** Creates a textbox with the set text
 * align: -1 left, 0 right, 1 centre
 * height currently is not applied as a vertical limit, it may be in the future.
 * padding: an extra distance to top and bottom by pixel
 */
class BlockText(var text: String, size: Int, var colour: Color, var fontPath: String, block: LcsRect = GetLcsRect.ofCentreSquare(), var align: Int = 1, var padding: lv = GetLcs.byLcs(0f), var keepWords: Boolean = false, scalingType: ScalingType = ScalingType.FIT_ELEMENT) : OmniVisual() {
    var initSize = size
    var displayText = text
    var gl = GlyphLayout()
    var visualSizeData = VisualSizeData(block, scalingType = scalingType)
    init{
        //visualSizeData.updateImageBlock(block)
    }
    private var imageBlockOnCreation = visualSizeData.imageBlock
    init {
        visualSizeData = visualSizeData.copy(originalRect = this.block.copy())

        FontGenerator.createFont(fontPath,initSize,text)
        visualSizeData.updateImageBlock(block)
        //updateVisual()
    }

    fun changeText(s: String){

        if(s!=text){
            text = s
            visualSizeData.updateImageBlock(block)
            //updateVisual()
        }
    }

    override fun setFlip(x: Boolean, y: Boolean) {
        //TODO flipping for text
    }




    override fun draw(batch: SpriteBatch, alpha: Float) {
        val font = FontGenerator.usedFonts.first { it.first==fontPath&& it.second==initSize }.third
        visualSizeData.updateImageBlock(block)
        val imageBlock = visualSizeData.imageBlock
        when (align) {
            -1 -> { //left
                font.draw(batch, gl, imageBlock.cX.asPixel() - imageBlock.width.asPixel() / 2 + padding.asPixel(), imageBlock.cY.asPixel() + gl.height / 2)
            }
            0 -> { //right
                font.draw(batch, gl, imageBlock.cX.asPixel() + imageBlock.width.asPixel() / 2 - gl.width - padding.asPixel(), imageBlock.cY.asPixel() + gl.height / 2)
            }
            1 -> { //centre
                font.draw(batch, gl, imageBlock.cX.asPixel() - gl.width / 2, imageBlock.cY.asPixel() + gl.height / 2)
            }
        }


    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {}
    override fun recolour(c: Color) {
        colour = c
        val f  = FontGenerator.usedFonts.first { it.first==fontPath&& it.second==initSize }.third
        gl = GlyphLayout(f, displayText, colour, visualSizeData.imageBlock.width.asPixel(), -1, true)
    }

    override fun copy(): OmniVisual {
        return BlockText(text, initSize, colour, fontPath, block, align, padding, keepWords, visualSizeData.scalingType)
    }

    override fun dispose() {
    }


    /** Reduces the size of the display string by squeezing it into the defined box
     */
    private fun reduceToHeight(s: String, f: BitmapFont): String {
        var newText = s
        visualSizeData.updateImageBlock(block)
        val imageBlock = visualSizeData.imageBlock

        var gl = GlyphLayout(f, newText, f.color, imageBlock.width.asPixel(), align, true)
        for (i in newText.split(" ").indices) {
            if (gl.height + padding.asPixel() < imageBlock.height.asPixel()) {
                break
            }
            for (j in newText.iterator()) {
                val lastChar = newText.last()
                newText = newText.dropLast(1)
                if (lastChar == ' ') {
                    break
                }
            }
            gl = GlyphLayout(f, newText, f.color, imageBlock.width.asPixel(), align, true)
            if (newText.isEmpty()) {
                break
            }
        }
        return newText
    }

    /*
    override fun updateVisual() {
        if((imageBlockOnCreation.width.asPixel()!=imageBlock.width.asPixel())||(imageBlockOnCreation.height.asPixel()!=imageBlock.height.asPixel())) {
            val f  = FontGenerator.usedFonts.first { it.first==fontPath&& it.second==initSize }.third
            displayText = reduceToHeight(text,f)
            gl = GlyphLayout(f, displayText, colour, imageBlock.width.asPixel(), -1, true)
        }
    }

     */



}