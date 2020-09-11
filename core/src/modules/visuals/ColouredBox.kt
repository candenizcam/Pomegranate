package modules.visuals

import modules.lcsModule.GetLcs
import modules.lcsModule.LcsVariable as lv
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class ColouredBox(w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f), var colour: Color = Color(1f, 1f, 1f, 1f), visualSize: VisualSize = VisualSize.STATIC) : OmniVisual(w = w, h = h, visualSize = visualSize) {
    private var s: Sprite = createSprite() //creates a block with the relevant colour



    override fun relocate(x: lv, y: lv) {
        cX = x
        cY = y
        s.x = x.asPixel() - s.width / 2
        s.y = y.asPixel() - s.height / 2
    }

    /** Draws the batch
     */
    override fun draw(batch: SpriteBatch) {
        s.draw(batch)

    }

    override fun changeActiveSprite(ns: Int) {}
    override fun update() {}


    override fun fitElement(w: modules.lcsModule.LcsVariable, h: modules.lcsModule.LcsVariable) {
        width = w
        height = h
        imageWidth = w
        imageHeight = h
        s.setSize(w.asPixel(), h.asPixel())
    }

    override fun fitWithRatio(w: modules.lcsModule.LcsVariable, h: modules.lcsModule.LcsVariable) {
        width=w
        height=h
        val rat = (width/originalWidth).asLcs().coerceAtMost((height/originalHeight).asLcs())
        imageWidth = originalWidth*rat
        imageHeight = originalHeight*rat
        s.setSize(originalWidth.asPixel()*rat,originalHeight.asPixel()*rat)
    }

    /** Changes the colour of the block
     */
    override fun recolour(c: Color) {
        colour = c
        s.color = c
    }

    override fun copy(): OmniVisual {
        ColouredBox(width, height, colour, visualSize).also {
            it.relocate(cX, cY)
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
        Pixmap(width.asPixel().toInt() + 1, height.asPixel().toInt() + 1, Pixmap.Format.RGBA8888).also {
            it.setColor(1f, 1f, 1f, 1f)
            it.fill()
            Sprite(Texture(it)).also { it2 ->
                it.dispose()
                it2.color = colour
                originalHeight = GetLcs.byPixel(it2.height)
                originalWidth = GetLcs.byPixel(it2.width)
                imageWidth = originalWidth
                imageHeight = originalHeight
                return it2
            }
        }
    }
}