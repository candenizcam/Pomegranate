package modules.visuals

import modules.LcsModule.GetLcs
import modules.LcsModule.LcsVariable as lv
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class SingleTexture(private val path: String, w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f)): OmniVisual(w=w,h=h) {
    init{
        width = w
        height = h
    }
    private var s: Sprite = createSprite()

    override fun relocate(x: lv, y: lv) {
        cX = x
        cY = y
        s.x = x.asPixel() - s.width/2
        s.y = y.asPixel() - s.height/2
    }

    override fun resize(w: lv, h: lv) {
        width = w
        height = h
        s.setSize(width.asPixel(),height.asPixel())
    }

    override fun draw(batch: SpriteBatch) {
        s.draw(batch)
    }

    override fun changeActiveSprite(ns: Int) {}
    override fun update() {}

    override fun recolour(c: Color) {
        s.color = c
    }


    private fun createSprite(): Sprite {
        Pixmap(Gdx.files.internal(path)).also {
            Pixmap((width.asPixel()).toInt(), (height.asPixel()).toInt(), it.format).also {it2->
                it2.filter = Pixmap.Filter.NearestNeighbour
                it2.blending = Pixmap.Blending.None
                it2.drawPixmap(it,0,0,it.width,it.height,0,0,it2.width,it2.height)
                return Sprite(Texture(it2)).apply{
                    it.dispose()
                    it2.dispose()
                }
            }
        }
    }



}