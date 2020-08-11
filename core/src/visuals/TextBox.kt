package com.pungo.engine.model.visuals

import LcsModule.GetLcs
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import visuals.OmniVisual
import LcsModule.LcsVariable as lv

/** Creates a textbox with the set text
 * align: -1 left, 0 right, 1 centre
 * height currently is not applied as a vertical limit, it may be in the future.
 */
class TextBox(val text: String, size: Int, colour: Color, w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f), var align: Int=-1): OmniVisual(w, h) {
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
        GlyphLayout(font, text,font.color,width.asPixel(),align,true).also{
            if (align==-1){
                font.draw(batch,it,cX.asPixel()-it.width/2,cY.asPixel()+it.height/2)
            } else if (align==0){
                font.draw(batch,it,cX.asPixel()+it.width/2,cY.asPixel()+it.height/2)
            } else if (align==1){
                font.draw(batch,it,cX.asPixel(),cY.asPixel())
            }
        }
    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {}

    fun createFont(size: Int): BitmapFont {
        val ftfg = FreeTypeFontGenerator(Gdx.files.internal("fonts/PTMono-Regular.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = size // font size 12 pixels
        parameter.color = Color(1f,1f,1f,1f)

        return ftfg.generateFont(parameter).also {
            ftfg.dispose()
        }

    }
}
