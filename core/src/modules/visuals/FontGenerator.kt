package com.modules.visuals

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

object FontGenerator {
    val usedFonts = mutableListOf<Triple<String,Int,BitmapFont>>()








    /** Finds a reduced punto number that fits the text to the box
     *
    private fun reduceToPunto(s: String, p: FreeTypeFontGenerator.FreeTypeFontParameter, g: FreeTypeFontGenerator): Int {
        println("reduce to punto")
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
            font.dispose()
        }
        return outPunto
    }
     */

    private fun createNewFont(size: Int,fontPath: String): BitmapFont {
        val ftfg = FreeTypeFontGenerator(Gdx.files.internal(fontPath))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.color = Color.WHITE
        parameter.size = size
        /*
        if (initSize == 0) {
            parameter.size = 3
            parameter.size = reduceToPunto(text, parameter, ftfg)
            parameter.size = 12
        }

         */
        return ftfg.generateFont(parameter).also {
            ftfg.dispose()
            //it.dispose()
        }
    }

    fun createFont(fontPath: String, size: Int, text: String){
        if(usedFonts.none {it.first==fontPath && it.second==size  }){
            usedFonts.add(Triple(fontPath,size, createNewFont(size, fontPath)))
        }
    }

    fun dispose(){
        usedFonts.forEach {
            it.third.dispose()
        }
    }
}