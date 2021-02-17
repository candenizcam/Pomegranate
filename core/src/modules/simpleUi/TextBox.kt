package modules.simpleUi

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.Rectangle
import modules.uiPlots.DrawingRectangle
import modules.visuals.FontGenerator

class TextBox: Building {
    constructor(text: String, alignment: PunGlyph.TextAlignment=PunGlyph.TextAlignment.CENTRE, maxPunto: Int? = null, minPunto: Int? = null, colour: Color = Color.WHITE){
        this.minPunto = minPunto?:this.minPunto
        this.maxPunto = maxPunto?:this.maxPunto
        glyph = PunGlyph(FontGenerator.getFont("fonts/PTMono-Regular.ttf",activePunto),text)
        glyph.textAlignment = alignment
        this.text = text
        this.colour = colour
    }


    var text : String
        set(value) {
            field = value
            widthRecord = 0f
            glyph.setText(glyph.font, text)
        }
    var glyph: PunGlyph
    private var activePunto = FontGenerator.frequentPuntoList.last()
    private var minPunto = FontGenerator.frequentPuntoList.first()
    private var maxPunto = FontGenerator.frequentPuntoList.last()
    private var widthRecord = 0f
    var colour: Color

    fun setPuntoRange(min: Int, max: Int){
        minPunto = min
        maxPunto = max
        activePunto = max
    }

    private fun updateGlyph(baseWidth: Float, baseHeight: Float){
        for (i in FontGenerator.getFontsBetween(minPunto..maxPunto,"fonts/PTMono-Regular.ttf").reversed()){
            glyph = glyph.copy(i)
            if(glyph.targetHeight(baseWidth)<=baseHeight){
                break
            }
        }
    }

    override fun update() {

        //TODO("Not yet implemented")
    }


    override fun draw(batch: SpriteBatch, drawingRectangle: DrawingRectangle) {
        if((glyph.targetHeight(drawingRectangle.baseWidth)>drawingRectangle.baseHeight)||(widthRecord!=drawingRectangle.baseWidth)){
            widthRecord = drawingRectangle.baseWidth
            updateGlyph(drawingRectangle.baseWidth,drawingRectangle.baseHeight)
        }
        glyph.font.color = colour
        glyph.draw(batch,drawingRectangle.croppedSegment, drawingRectangle.baseWidth)
    }

    override fun hoverFunction(hovering: Boolean) {
        //TODO("Not yet implemented")
    }
}