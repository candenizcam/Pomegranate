package modules.uiElements

import com.badlogic.gdx.graphics.Color
import modules.visuals.*
import modules.visuals.fromFont.BlockText
import modules.visuals.fromPixmap.PixmapGenerator


/** This object is used to generate ui objects without the hustle
 * Perfect for those who want to use, but not learn ui elements
 */
object FastGenerator {
    fun genericSetButton(id: String, text: String, textSize: Int, bgColour: Color, textColour: Color, fontPath: String, pressedColorRatio: Float= 0.5f, scalingType: ScalingType=  ScalingType.FIT_ELEMENT): SetButton {
        val v12 = PixmapGenerator.singleColour(c = bgColour,scalingType = scalingType)
        val v22 = PixmapGenerator.singleColour(c = Color(bgColour.r*pressedColorRatio, bgColour.g*pressedColorRatio, bgColour.b*pressedColorRatio, bgColour.a),scalingType = scalingType)
        val v11 = BlockText(text, textSize, textColour, fontPath, scalingType = scalingType)
        val v21 = BlockText(text, textSize, Color(bgColour.r * pressedColorRatio, bgColour.g * pressedColorRatio, bgColour.b * pressedColorRatio, bgColour.a), fontPath, scalingType= scalingType)
        return SetButton(id, TwoVisuals(v11, v12), TwoVisuals(v21, v22))
    }
}