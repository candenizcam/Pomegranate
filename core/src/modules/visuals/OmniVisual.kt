package modules.visuals

import com.badlogic.gdx.graphics.Color
import modules.LcsModule.GetLcs
import modules.LcsModule.LcsVariable as lv
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.LcsRect

/** OmniVisual is the base class for visuals
 *
 */
abstract class OmniVisual(x:lv= GetLcs.byLcs(0f), y: lv= GetLcs.byLcs(0f), w: lv= GetLcs.byLcs(0f), h: lv= GetLcs.byLcs(0f),var visualSize: VisualSize= VisualSize.STATIC) {
    var cX: lv = x
    var cY: lv = y
    var width: lv = w
    var height: lv = h
    var originalWidth: lv = GetLcs.ofZero()
    var originalHeight: lv = GetLcs.ofZero()





    abstract fun relocate(x: lv, y: lv)

    abstract fun fitElement(w: lv, h: lv)

    abstract fun fitWithRatio(w: lv, h: lv)

    fun resize(w: lv, h: lv){
        if((width!=w)||(height!=h)){
            when(visualSize){
                VisualSize.STATIC->{}
                VisualSize.FIT_ELEMENT->{fitElement(w,h)}
                VisualSize.FIT_WITH_RATIO->{fitWithRatio(w,h)}
            }
        }
    }



    fun resize(b: LcsRect){
        resize(b.width,b.height)
    }

    abstract fun draw(batch: SpriteBatch)

    abstract fun changeActiveSprite(ns: Int)

    abstract fun update()

    abstract fun recolour(c: Color)

    abstract fun copy(): OmniVisual

    abstract fun dispose()


}