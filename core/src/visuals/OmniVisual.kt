package visuals

import LcsModule.GetLcs
import LcsModule.LcsVariable as lv
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

/** OmniVisual is the base class for visuals
 *
 */
abstract class OmniVisual(x:lv= GetLcs.byLcs(0f), y: lv= GetLcs.byLcs(0f), w: lv= GetLcs.byLcs(0f), h: lv= GetLcs.byLcs(0f)) {
    var cX: lv = x
    var cY: lv = y
    var width: lv = w
    var height: lv = h


    abstract fun relocate(x: lv, y: lv)

    abstract fun resize(w: lv, h: lv)

    abstract fun draw(batch: SpriteBatch)

    abstract fun changeActiveSprite(ns: Int)

    abstract fun update()



}