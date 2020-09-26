package modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class TwoVisuals(var front: OmniVisual, var back: OmniVisual, visualSize: VisualSize = VisualSize.STATIC) : OmniVisual(visualSize = visualSize) {
    override fun draw(batch: SpriteBatch, alpha: Float) {
        back.draw(batch,alpha)
        front.draw(batch,alpha)

    }

    override fun changeActiveSprite(ns: Int) {}

    fun swapFrontSprite() {
        back = front.also {
            front = back
        }
    }

    override fun update() {
        back.update()
        front.update()
    }

    override fun recolour(c: Color) {
        back.recolour(c)
        front.recolour(c)
    }

    override fun copy(): OmniVisual {
        return TwoVisuals(front.copy(), back.copy(), visualSize)
    }

    override fun dispose() {
        front.dispose()
        back.dispose()
    }

    override fun updateVisual() {
        front.reBlock(block)
        back.reBlock(block)
    }
}