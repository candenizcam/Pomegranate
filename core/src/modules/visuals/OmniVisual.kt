package modules.visuals

import com.badlogic.gdx.graphics.Color
import modules.lcsModule.LcsVariable as lv
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect

/** OmniVisual is the base class for visuals
 * block is where the visual should be, but depending on scale factor and visual size, it may not
 * visualSize describes fitting preference, static means size is conserved, fit element means fitting the element it is in, fit with ratio, fits to the element while staying in centre and protecting ratio
 * scale factor is a float that is used to multiply the final form after visual size operations are conducted, for static it scales directly, for others it scales the fitted form
 */
abstract class OmniVisual(block: LcsRect = GetLcsRect.ofZero(), visualSize: VisualSize= VisualSize.STATIC, scaleFactor: Float = 1f) {
    var block = block
        protected set(value) {
            field = value
            imageBlock = updateImageBlock()
        }
    var imageBlock = block
        protected set(value) {
            field = value
            updateVisual()
        }
    var originalBlock = GetLcsRect.ofZero()
        protected set(value) {
            field = value
        }
    var visualSize = visualSize
        set(value){
            field = value
            imageBlock = updateImageBlock()
        }
    var scaleFactor = scaleFactor
        set(value) {
            field = value
            imageBlock = updateImageBlock()
        }


    /** This is the relocate function that can be called from outside
     */
    fun relocate(x: lv, y: lv){
        block = block.relocateTo(x,y)
    }

    /** This is the resize function that can be called from outside
     */
    fun resize(b: LcsRect) {
        resize(b.width, b.height)
    }

    /** This is the resize function that can be called from outside
     */
    fun resize(w: modules.lcsModule.LcsVariable,h: modules.lcsModule.LcsVariable){
        block = block.resizeTo(w,h)
    }

    /** This directly updates the block
     */
    fun reBlock(b: LcsRect){
        block = b
    }

    abstract fun draw(batch: SpriteBatch)

    abstract fun changeActiveSprite(ns: Int)

    abstract fun update()

    abstract fun recolour(c: Color)

    abstract fun copy(): OmniVisual

    abstract fun dispose()

    private fun fitElement(): LcsRect {
        return imageBlock.resizeTo(block.width*scaleFactor,block.height*scaleFactor)
    }

    private fun fitWithRatio(): LcsRect {
        block.getFittingRect(originalBlock.width.asLcs(),originalBlock.height.asLcs()).also {
            return imageBlock.resizeTo(it.width*scaleFactor,it.height*scaleFactor)
        }
    }

    private fun fitStatic(): LcsRect {
        return imageBlock.resizeTo(originalBlock.width*scaleFactor,originalBlock.height*scaleFactor)
    }

    protected abstract fun updateVisual()

    private fun updateImageBlock(): LcsRect {
        val ib = when (visualSize) {
            VisualSize.STATIC -> {
                fitStatic()
            }
            VisualSize.FIT_ELEMENT -> {
                fitElement()
            }
            VisualSize.FIT_WITH_RATIO -> {
                fitWithRatio()
            }
        }
        return ib.relocateTo(block.cX,block.cY)
    }

}