package modules.visuals

import modules.LcsModule.GetLcs
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import modules.LcsModule.LcsVariable as lv

/** Creates a textbox with the set text
 * align: -1 left, 0 right, 1 centre
 * height currently is not applied as a vertical limit, it may be in the future.
 * padding: an extra distance to top and bottom by pixel
 */
class TextBox(val text: String, size: Int, colour: Color, w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f), var align: Int=-1, var padding: lv=GetLcs.byLcs(0f)): OmniVisual(w, h) {
    var displayText= text
    var gl = GlyphLayout()
    init{
        width = w
        height = h
    }
    var font = createFont(size).also{
        it.color = colour
    }



    override fun relocate(x: lv, y: lv) {
        cX = x
        cY = y
        println("width is ${width.asPixel()}")
    }

    override fun resize(w: lv, h: lv) {
        width = w
        height = h
    }

    override fun draw(batch: SpriteBatch) {
        when (align) {
            -1 -> {
                font.draw(batch,gl,cX.asPixel()-gl.width/2,cY.asPixel()+gl.height/2)
            }
            0 -> {
                font.draw(batch,gl,cX.asPixel()+gl.width/2,cY.asPixel()+gl.height/2)
            }
            1 -> {
                font.draw(batch,gl,cX.asPixel(),cY.asPixel())
            }
        }
    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {}

    /** Reduces the size of the display string by squeezing it into the defined box
     */
    private fun reduceToHeight(s: String, f: BitmapFont): String{
        var newText = s
        var gl = GlyphLayout(f, newText,f.color,width.asPixel(),align,true)
        for (i in newText.split(" ").indices){
            if(gl.height + padding.asPixel()<height.asPixel()){
                break
            }
            for (j in newText.iterator()){
                val lastChar = newText.last()
                newText = newText.dropLast(1)
                if( (lastChar==' ') || (lastChar==' ')){
                    break
                }
            }
            gl = GlyphLayout(f, newText,f.color,width.asPixel(),align,true)
            if (newText.isEmpty()){
                break
            }
        }
        return newText
    }

    /** Finds a reduced punto number that fits the text to the box
     *
     */
    private fun reduceToPunto(s: String, p: FreeTypeFontGenerator.FreeTypeFontParameter, g: FreeTypeFontGenerator): Int {
        var font : BitmapFont
        var gl : GlyphLayout
        var outPunto = p.size
        for (i in 3..1000){
            p.size = i
            font = g.generateFont(p)
            gl = GlyphLayout(font, s,font.color,width.asPixel(),align,true)

            if (gl.height + padding.asPixel() > height.asPixel()){
                break
            }
            outPunto = i
        }
        return outPunto
    }

    private fun createFont(size: Int): BitmapFont {
        val ftfg = FreeTypeFontGenerator(Gdx.files.internal("fonts/PTMono-Regular.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.color = Color(1f,1f,1f,1f)
        parameter.size = size
        if (size==0){
            parameter.size = 3
            parameter.size = reduceToPunto(displayText, parameter, ftfg)
        }
        return ftfg.generateFont(parameter).also {
            displayText = reduceToHeight(displayText,it)
            gl = GlyphLayout(it, displayText,it.color,width.asPixel(),align,true)
            ftfg.dispose()
        }
    }
}
