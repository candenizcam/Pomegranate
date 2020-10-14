package modules.uiElements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.visuals.OmniVisual

/** Used to display a single OmniVisual
 */
class PinupImage(id: String, var image: OmniVisual) : UiElement(id) {
    init {
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        if (visible) {
            image.reBlock(getBlock())
            image.draw(batch,alpha)
        }
    }

    override fun dispose() {}

    override fun touchHandler(mayTouch: Boolean): Boolean {
        return hovering() && mayTouch
    }

    override fun update() {
        image.update()
    }

    fun recolour(c: Color) {
        image.recolour(c)
    }

}