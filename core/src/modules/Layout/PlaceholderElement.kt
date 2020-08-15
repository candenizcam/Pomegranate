package modules.Layout

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcs
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable

class PlaceholderElement(id: String, rect : LcsRect): UiElement(id) {
    override var block = rect

    override fun update() {}

    override fun relocate(x: LcsVariable, y: LcsVariable) {}
    override fun resize(w: LcsVariable, h: LcsVariable) {}

    override fun draw(batch: SpriteBatch) {}
}