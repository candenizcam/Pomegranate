package modules.visuals

import modules.LcsModule.GetLcs
import modules.LcsModule.LcsVariable as lv
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class ColouredBox(w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f), var colour: Color = Color(1f,1f,1f,1f)): OmniVisual(w=w,h=h) {
    private var s: Sprite = createSprite() //creates a block with the relevant colour



    override fun relocate(x: lv, y: lv) {
        cX = x
        cY = y
        s.x = x.asPixel() - s.width/2
        s.y = y.asPixel() - s.height/2
    }

    /** Draws the batch
     */
    override fun draw(batch: SpriteBatch){
        s.draw(batch)

    }

    override fun changeActiveSprite(ns: Int) {}
    override fun update() {}

    /** Changes the size of the block, experimental and honestly, don't do this
     */
    override fun resize(w: lv, h: lv) {
        width = w
        height = h
        val x = s.x
        val y = s.y
        s = createSprite()
        s.x = x
        s.y = y
    }

    /** Changes the colour of the block
     */
    override fun recolour(c: Color){
        colour = c
        s.color = c
    }

    /** Creates the sprite
     * also, boy do i like this little bit of code
     */
    private fun createSprite(): Sprite {
        Pixmap(width.asPixel().toInt()+1, height.asPixel().toInt()+1, Pixmap.Format.RGBA8888).also {
            it.setColor(1f,1f,1f,1f)
            it.fill()
            Sprite(Texture(it)).also { it2->
                it.dispose()
                it2.color = colour
                return it2
            }
        }
    }
}