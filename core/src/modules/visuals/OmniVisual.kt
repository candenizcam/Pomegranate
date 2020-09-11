package modules.visuals

import com.badlogic.gdx.graphics.Color
import modules.lcsModule.GetLcs
import modules.lcsModule.LcsVariable as lv
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.LcsRect

/** OmniVisual is the base class for visuals
 * x & y are centre coordinates
 * w & h are width and height
 * visualSize describes fitting preference, static means size is conserved, fit element means fitting the element it is in, fit with ratio, fits to the element while staying in centre and protecting ratio
 * scale factor is a float that is used to multiply the final form after visual size operations are conducted, for static it scales directly, for others it scales the fitted form
 */
abstract class OmniVisual(x:lv= GetLcs.byLcs(0f), y: lv= GetLcs.byLcs(0f), w: lv= GetLcs.byLcs(0f), h: lv= GetLcs.byLcs(0f),var visualSize: VisualSize= VisualSize.STATIC, var scaleFactor: Float = 1f) {
    var cX: lv = x
    var cY: lv = y
    var width: lv = w
    var height: lv = h
    var originalWidth: lv = GetLcs.ofZero()
    var originalHeight: lv = GetLcs.ofZero()
    var imageWidth: lv = GetLcs.ofZero()
    var imageHeight: lv = GetLcs.ofZero()


    abstract fun relocate(x: lv, y: lv)

    abstract fun fitElement(w: lv, h: lv)

    abstract fun fitWithRatio(w: lv, h: lv)

    abstract fun fitStatic(w: lv, h: lv)


    fun resize(w: lv, h: lv) {
        if ((width != w) || (height != h)) {
            when (visualSize) {
                VisualSize.STATIC -> {
                    fitStatic(w, h)
                }
                VisualSize.FIT_ELEMENT -> {
                    fitElement(w, h)
                }
                VisualSize.FIT_WITH_RATIO -> {
                    fitWithRatio(w, h)
                }
            }
        }
    }


    fun resize(b: LcsRect) {
        resize(b.width, b.height)
    }

    abstract fun draw(batch: SpriteBatch)

    abstract fun changeActiveSprite(ns: Int)

    abstract fun update()

    abstract fun recolour(c: Color)

    abstract fun copy(): OmniVisual

    abstract fun dispose()

}