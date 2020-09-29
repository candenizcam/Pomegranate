package modules.uiElements

import com.badlogic.gdx.graphics.Color
import modules.visuals.*
import modules.visuals.fromFont.BlockText
import modules.visuals.fromPixmap.PixmapGenerator


/** This object is used to generate ui objects without the hustle
 * Perfect for those who want to use, but not learn ui elements
 */
object FastGenerator {
    fun genericSetButton(id: String, text: String, textSize: Int,bgColour: Color, textColour: Color, fontPath: String, pressedColorRatio: Float= 0.5f,visualSize: VisualSize=  VisualSize.FIT_ELEMENT): SetButton {
        val v12 = PixmapGenerator.singleColour(c = bgColour,visualSize = visualSize)
        val v22 = PixmapGenerator.singleColour(c = Color(bgColour.r*pressedColorRatio, bgColour.g*pressedColorRatio, bgColour.b*pressedColorRatio, bgColour.a),visualSize = visualSize)
        val v11 = BlockText(text, textSize, textColour, fontPath, visualSize = visualSize)
        val v21 = BlockText(text, textSize, Color(bgColour.r * pressedColorRatio, bgColour.g * pressedColorRatio, bgColour.b * pressedColorRatio, bgColour.a), fontPath, visualSize = visualSize)
        return SetButton(id, TwoVisuals(v11, v12), TwoVisuals(v21, v22))
    }
}