package modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.modules.visuals.OVL
import modules.basic.geometry.FastGeometry
import modules.basic.geometry.Rectangle
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable as lv

/** OmniVisual is the base class for visuals
 * block is where the visual should be, but depending on scale factor and visual size, it may not
 * visualSize describes fitting preference, static means size is conserved, fit element means fitting the element it is in, fit with ratio, fits to the element while staying in centre and protecting ratio
 * scale factor is a float that is used to multiply the final form after visual size operations are conducted, for static it scales directly, for others it scales the fitted form
 */
abstract class OmniVisual {
    var block = GetLcsRect.ofZero()
        protected set
    init {
        OVL.allVisuals.add(this)
    }

    open fun getOriginalRect(): LcsRect{
        return block.copy()
    }

    open fun getImageRect(block: LcsRect?=null): LcsRect{

        return block ?: this.block
    }

    open fun setClippingRect(r: Rectangle){}
    /*
    fun setClippingRect(r: Rectangle){
        visualSizeData = visualSizeData.copy(clipRectangle = r)
    }

     */

    open fun setScalingType(scalingType: ScalingType, scaleFactor: Float){
    }


    /** This is the relocate function that can be called from outside
     */
    fun relocate(x: lv, y: lv) {
        block = block.relocateTo(x, y)
    }

    /** This is the resize function that can be called from outside
     */
    fun resize(b: LcsRect) {
        resize(b.width, b.height)
    }

    /** This is the resize function that can be called from outside
     */
    fun resize(w: modules.lcsModule.LcsVariable, h: modules.lcsModule.LcsVariable) {
        block = block.resizeTo(w, h)
    }

    /** This directly updates the block
     */
    fun reBlock(b: LcsRect) {
        block = b
    }

    abstract fun draw(batch: SpriteBatch, alpha: Float = 1f)

    abstract fun changeActiveSprite(ns: Int)

    abstract fun update()

    abstract fun recolour(c: Color)

    abstract fun copy(): OmniVisual

    abstract fun dispose()

    abstract fun setFlip(x: Boolean, y: Boolean)


}