package modules.Layout

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcs
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import modules.visuals.OmniVisual

/** Used to display a single OmniVisual
 */
class PinupImage(id:String, private var image: OmniVisual, var width: LcsVariable= GetLcs.byPixel(0f), var height: LcsVariable = GetLcs.byPixel(0f), fitImage: Boolean=true): UiElement(id) {
    var cX = GetLcs.ofZero()
    var cY = GetLcs.ofZero()
    override lateinit var block: LcsRect
    init{
        if (fitImage){
            width = image.width
            height = image.height
        } else {
            image.resize(width,height)
        }
        block = GetLcsRect.byParameters(image.width,image.height,GetLcs.byLcs(0f),GetLcs.byLcs(0f))
        println("init $id")
    }

    /** This constructor enables a block input
     */
    constructor(id: String, image: OmniVisual, block: LcsRect, fitImage: Boolean =false) : this(id,image,fitImage=fitImage){
        this.block = block
        width = block.width
        height = block.height
        if (fitImage){
            this.image.resize(width,height)
        }
        this.image.relocate(block.cX,block.cY)
    }


    override fun draw(batch: SpriteBatch) {
        if(visible){
            image.draw(batch)
        }

    }

    override fun update() {}

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        cX = x
        cY = y
        image.relocate(x,y)
        block = GetLcsRect.byParameters(image.width,image.height,image.cX,image.cY)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        width=w
        height=h
        block = GetLcsRect.byParameters(image.width,image.height,image.cX,image.cY)
        image.resize(w,h)
    }

    fun recolour(c: Color){
        image.recolour(c)
    }

}