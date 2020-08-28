package modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.LcsVariable

class TwoVisuals(var front: OmniVisual, var back: OmniVisual,visualSize: VisualSize= VisualSize.STATIC): OmniVisual(visualSize = visualSize) {
    override fun relocate(x: LcsVariable, y: LcsVariable) {
        front.relocate(x,y)
        back.relocate(x,y)
    }

    override fun fitElement(w: LcsVariable, h: LcsVariable) {
        front.resize(w,h)
        back.resize(w,h)
    }

    override fun fitWithRatio(w: LcsVariable, h: LcsVariable) {
        front.fitWithRatio(w,h)
        front.fitWithRatio(w,h)
    }

    override fun draw(batch: SpriteBatch) {
        back.draw(batch)
        front.draw(batch)

    }

    override fun changeActiveSprite(ns: Int) {
        back = front.also{
            front =back
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
        return TwoVisuals(front,back,visualSize)
    }

    override fun dispose() {
        front.dispose()
        back.dispose()
    }
}