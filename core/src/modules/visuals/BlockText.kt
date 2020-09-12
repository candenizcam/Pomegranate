package modules.visuals

import modules.lcsModule.GetLcs
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable as lv

/** Creates a textbox with the set text
 * align: -1 left, 0 right, 1 centre
 * height currently is not applied as a vertical limit, it may be in the future.
 * padding: an extra distance to top and bottom by pixel
 */
class BlockText(var text: String, size: Int, colour: Color, var fontPath: String, block: LcsRect = GetLcsRect.ofCentreSquare(), var align: Int = 1, var padding: lv = GetLcs.byLcs(0f), var keepWords: Boolean = false, visualSize: VisualSize = VisualSize.STATIC) : OmniVisual(block, visualSize = visualSize) {
    var initSize = size
    var displayText = text
    var gl = GlyphLayout()
    var font = createFont(colour)
    private var imageBlockOnCreation = imageBlock.copy()

    fun changeText(s: String){
        if(s!=text){
            text = s
            font = createFont(font.color)
        }
    }

    /*
    override fun relocate() {
        imageBlock = imageBlock.relocateTo(block.cX,block.cY)

    }

     */



    override fun draw(batch: SpriteBatch) {
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
        font = createFont(c)
    }

    override fun copy(): OmniVisual {
        return BlockText(text, initSize, font.color, fontPath, block, align, padding, keepWords, visualSize)
    }

    override fun dispose() {
        try{
            font.dispose()
        } catch (e: Exception){
            println("font dispose error occured")
        }

    }

    override fun updateVisual() {
        if((imageBlockOnCreation.width.asPixel()!=imageBlock.width.asPixel())||(imageBlockOnCreation.height.asPixel()!=imageBlock.height.asPixel())){
            font = createFont(font.color)
        }
    }

    /** Reduces the size of the display string by squeezing it into the defined box
     */
    private fun reduceToHeight(s: String, f: BitmapFont): String {
        var newText = s
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

    /** Finds a reduced punto number that fits the text to the box
     *
     */
    private fun reduceToPunto(s: String, p: FreeTypeFontGenerator.FreeTypeFontParameter, g: FreeTypeFontGenerator): Int {
        var font: BitmapFont
        var gl: GlyphLayout
        var outPunto = p.size
        for (i in 3..102) {
            p.size = i
            font = g.generateFont(p)
            gl = GlyphLayout(font, s, font.color, imageBlock.width.asPixel(), align, true)

            if (gl.height + padding.asPixel() > imageBlock.height.asPixel()) {
                break
            }
            if ((s.split(" ").size == 1) && (gl.width + padding.asPixel() > imageBlock.width.asPixel())) {
                break
            }

            outPunto = i
        }
        return outPunto
    }

    private fun createFont(color: Color): BitmapFont {
        val ftfg = FreeTypeFontGenerator(Gdx.files.internal(fontPath))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.color = color
        parameter.size = initSize
        imageBlockOnCreation = imageBlock.copy()
        if (initSize == 0) {
            parameter.size = 3
            parameter.size = reduceToPunto(text, parameter, ftfg)
        }
        return ftfg.generateFont(parameter).also {
            it.color = color
            displayText = reduceToHeight(text, it)
            gl = GlyphLayout(it, displayText, it.color, imageBlock.width.asPixel(), -1, true)

            ftfg.dispose()
        }
    }
}