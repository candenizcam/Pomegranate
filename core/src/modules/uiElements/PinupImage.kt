package modules.uiElements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.visuals.OmniVisual

/** Used to display a single OmniVisual
 * As this is the ui element with which single image elements are made
 * This will not be using the district system
 */
class PinupImage(id: String, var image: OmniVisual) : UiElement(id) {
    override fun draw(batch: SpriteBatch, alpha: Float) {
        if (visible) {
            image.reBlock(getBlock())
            image.draw(batch,alpha)
        }
    }

    override fun dispose() {}


    override fun update() {
        super.update()
        image.update()
    }

    fun recolour(c: Color) {
        image.recolour(c)
    }

}