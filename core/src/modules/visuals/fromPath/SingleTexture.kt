package modules.visuals.fromPath

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.visuals.OmniVisual
import modules.visuals.VisualSize

class SingleTexture(private val path: FileHandle,colour: Color = Color.WHITE, visualSize: VisualSize = VisualSize.STATIC, scaleFactor: Float = 1f): OmniVisual(visualSize,scaleFactor) {
    private var sprite=Sprite(TextureCache.openTexture(path)).also{sprite->
        originalBlock = GetLcsRect.byParameters(GetLcs.byPixel(sprite.width),GetLcs.byPixel(sprite.height))
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
        SingleTexture(path, sprite.color, visualSize, scaleFactor).also {
            it.reBlock(block)
            return it
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