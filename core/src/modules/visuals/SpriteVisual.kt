package  modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.visuals.OmniVisual
import modules.visuals.VisualSize

class SpriteVisual (sprite: Sprite, colour: Color=Color.WHITE,  visualSize: VisualSize = VisualSize.STATIC, scaleFactor: Float = 1f) : OmniVisual(visualSize = visualSize, scaleFactor = scaleFactor) {
    private var sprite: Sprite = sprite.also {
        it.color = colour
        originalBlock = GetLcsRect.byParameters(GetLcs.byPixel(it.width),GetLcs.byPixel(it.height))
        this.block = originalBlock.copy()
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        
        batch.draw(sprite,imageBlock.wStart.asPixel(),imageBlock.hStart.asPixel(),imageBlock.width.asPixel(),imageBlock.height.asPixel())
    }

    override fun changeActiveSprite(ns: Int) {}
    override fun update() {}

    override fun recolour(c: Color) {
        sprite.color = c
    }

    override fun copy(): OmniVisual {
        return SpriteVisual(sprite, sprite.color, visualSize, scaleFactor).also {
            it.reBlock(block)
        }
    }

    override fun dispose() {
    }

    override fun setFlip(x: Boolean, y: Boolean) {
        sprite.flip(x,y)
    }

    override fun updateVisual() {
    }

}