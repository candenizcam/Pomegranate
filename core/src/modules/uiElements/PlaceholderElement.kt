package modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsVariable

class PlaceholderElement(id: String): UiElement(id) {
    override var block = GetLcsRect.getZero()

    override fun update() {}

    override fun relocate(x: LcsVariable, y: LcsVariable) {}
    override fun resize(w: LcsVariable, h: LcsVariable) {}

    override fun draw(batch: SpriteBatch) {}
}