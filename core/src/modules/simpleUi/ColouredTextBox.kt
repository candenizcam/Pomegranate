package modules.simpleUi

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.uiPlots.DrawingRectangle

class ColouredTextBox(text: String, fontPath: String, alignment: PunGlyph.TextAlignment=PunGlyph.TextAlignment.CENTRE, maxPunto: Int? = null, minPunto: Int? = null, textColour: Color = Color.WHITE, bgColour: Color = Color.BLACK): TextBox(text, fontPath, alignment, maxPunto, minPunto, textColour) {
    private var bgDisplayer = Displayer(bgColour)

    override fun draw(batch: SpriteBatch, drawingRectangle: DrawingRectangle) {
        bgDisplayer.draw(batch, drawingRectangle)
        super.draw(batch, drawingRectangle)
    }
}