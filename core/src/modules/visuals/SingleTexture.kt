package modules.visuals

import modules.lcsModule.GetLcs
import modules.lcsModule.LcsVariable as lv
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect

class SingleTexture(private val path: String, block: LcsRect = GetLcsRect.byParameters(GetLcs.byLcs(1f),GetLcs.byLcs(1f)), visualSize: VisualSize = VisualSize.STATIC, scaleFactor: Float = 1f) : OmniVisual(block, visualSize = visualSize,scaleFactor = scaleFactor) {
    private var s: Sprite = createSprite().also {
        this.block = this.block
    }

    override fun draw(batch: SpriteBatch) {
        s.draw(batch)
    }

    override fun changeActiveSprite(ns: Int) {}
    override fun update() {}

    override fun recolour(c: Color) {
        s.color = c
    }

    override fun copy(): OmniVisual {
        SingleTexture(path, block, visualSize, scaleFactor).also {
            return it
        }
    }

    override fun dispose() {
        s.texture.dispose()
    }

    override fun updateVisual() {
        if((s?.width!=imageBlock.width.asPixel())||(s?.height!=imageBlock.height.asPixel())){
            s?.setSize(imageBlock.width.asPixel(), imageBlock.height.asPixel())
        }
        s?.x = imageBlock.cX.asPixel() - s?.width / 2
        s?.y = imageBlock.cY.asPixel() - s?.height / 2
    }

    private fun createSprite(): Sprite {
        Pixmap(Gdx.files.internal(path)).also {
            originalBlock = GetLcsRect.byParameters(GetLcs.byPixel(it.width.toFloat()),GetLcs.byPixel(it.height.toFloat()))
            return Sprite(Texture(it)).apply {
                it.dispose()
            }

        }
    }
}