package  modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.visuals.OmniVisual
import modules.visuals.VisualSize

class SpriteVisual (sprite: Sprite, block: LcsRect = GetLcsRect.byParameters(GetLcs.byLcs(1f), GetLcs.byLcs(1f)), visualSize: VisualSize = VisualSize.STATIC, scaleFactor: Float = 1f) : OmniVisual(block, visualSize = visualSize, scaleFactor = scaleFactor) {
    private var s: Sprite = sprite.also {
        this.block = this.block
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        s.draw(batch, alpha)
    }

    override fun changeActiveSprite(ns: Int) {}
    override fun update() {}

    override fun recolour(c: Color) {
        s.color = c
    }

    override fun copy(): OmniVisual {
        return SpriteVisual(s, block, visualSize, scaleFactor)
    }

    override fun dispose() {
        s.texture.dispose()
    }

    override fun setFlip(x: Boolean, y: Boolean) {
        s.flip(x,y)
    }

    override fun updateVisual() {
        if ((s?.width != imageBlock.width.asPixel()) || (s?.height != imageBlock.height.asPixel())) {
            s?.setSize(imageBlock.width.asPixel(), imageBlock.height.asPixel())
        }
        s?.x = imageBlock.cX.asPixel() - s?.width / 2
        s?.y = imageBlock.cY.asPixel() - s?.height / 2
    }

}