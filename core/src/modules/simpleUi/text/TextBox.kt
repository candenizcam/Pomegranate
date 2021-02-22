package modules.simpleUi.text

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.Point
import modules.simpleUi.DisplayBuilding
import modules.uiPlots.DrawingRectangle
import modules.visuals.FontGenerator

open class TextBox: DisplayBuilding {
    constructor(text: String, fontPath: String, alignment: PunGlyph.TextAlignment = PunGlyph.TextAlignment.CENTRE, maxPunto: Int? = null, minPunto: Int? = null, colour: Color = Color.WHITE){
        this.minPunto = minPunto?:this.minPunto
        this.maxPunto = maxPunto?:this.maxPunto
        glyph = PunGlyph(FontGenerator.getFont(fontPath,activePunto),text)
        glyph.textAlignment = alignment
        this.text = text
        this.colour = colour
        this.fontPath = fontPath
    }


    var text : String
        set(value) {
            field = value
            widthRecord = 0f
            glyph.text = field
        }
    var glyph: PunGlyph
    var fontPath: String
    protected var activePunto = FontGenerator.frequentPuntoList.last()
    protected  var minPunto = FontGenerator.frequentPuntoList.first()
    protected  var maxPunto = FontGenerator.frequentPuntoList.last()
    private var widthRecord = 0f
    private var colour: Color
    override fun getColour(): Color {
        return colour
    }


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

    //override fun getColour(): Color {
    //    TODO("Not yet implemented")
    //}

    override fun recolour(c: Color) {
        colour = c
    }

    override fun copy(): DisplayBuilding {
        return TextBox(text, fontPath,glyph.textAlignment,maxPunto, minPunto, colour)
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

    override fun hoverFunction(hovering: Boolean, relativePoint: Point?) {
        //TODO("Not yet implemented")
    }
}