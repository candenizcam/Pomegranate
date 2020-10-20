package modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.basic.geometry.FastGeometry
import modules.basic.geometry.Rectangle
import modules.visuals.OmniVisual
import java.lang.Exception

/** Also called the Damla's item, this item contains a multitude of elements and display patterns can be altered by various commands
 *
 */
class MultiMediaItem(id: String): UiElement(id) {
    /** This guy adds an element with desired location and size qualities
     */
    fun addElement(element: UiElement,rectangle: Rectangle =FastGeometry.unitSquare()){
        district.addFullPlot(element.id,rectangle).element = element
    }

    /** This guy is just to make things easier for the above function
     */
    fun addElement(id: String, visual: OmniVisual, rectangle: Rectangle =FastGeometry.unitSquare()){
        addElement(PinupImage(id,visual),rectangle)
    }

    fun setVisibility(id: String,visible: Boolean){
        district.findPlot(id).element!!.visible = visible
    }


    override fun draw(batch: SpriteBatch, alpha: Float) {
        district.draw(batch,alpha)
    }

    override fun dispose() {}
}