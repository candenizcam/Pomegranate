package modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect

class ColouredBox(block: LcsRect = GetLcsRect.byParameters(GetLcs.byLcs(1f), GetLcs.byLcs(1f)), var colour: Color = Color(1f, 1f, 1f, 1f), visualSize: VisualSize = VisualSize.STATIC, scaleFactor: Float = 1f) : OmniVisual(block, visualSize = visualSize, scaleFactor = scaleFactor) {
    private var s: Sprite = createSprite() //creates a block with the relevant colour


    /** Draws the batch
     */
    override fun draw(batch: SpriteBatch, alpha: Float) {
        s.draw(batch,alpha)
    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {}

    override fun updateVisual() {
        if ((s?.width != imageBlock.width.asPixel()) || (s?.height != imageBlock.height.asPixel())) {
            s?.setSize(imageBlock.width.asPixel(), imageBlock.height.asPixel())
        }
        s?.x = imageBlock.cX.asPixel() - s?.width / 2
        s?.y = imageBlock.cY.asPixel() - s?.height / 2
    }


    /** Changes the colour of the block
     */
    override fun recolour(c: Color) {
        colour = c
        s.color = c
    }

    override fun copy(): OmniVisual {
        ColouredBox(block, colour, visualSize,scaleFactor).also {
            return it
        }
    }

    override fun dispose() {
        s.texture.dispose()
    }

    /** Creates the sprite
     * also, boy do i like this little bit of code
     */
    private fun createSprite(): Sprite {
        Pixmap(11, 11, Pixmap.Format.RGBA8888).also {
            it.setColor(1f, 1f, 1f, 1f)
            it.fill()
            Sprite(Texture(it)).also { it2 ->
                it.dispose()
                it2.color = colour
                originalBlock = GetLcsRect.byParameters(GetLcs.byPixel(it2.width), GetLcs.byPixel(it2.height))
                imageBlock = originalBlock.copy()
                if (visualSize == VisualSize.STATIC) {
                    it2.setSize(originalBlock.width.asPixel() * scaleFactor, originalBlock.height.asPixel() * scaleFactor)
                }
                return it2
            }
        }
    }
}