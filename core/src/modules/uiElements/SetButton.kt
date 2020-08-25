package modules.uiElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcs
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import modules.uiElements.UiElement
import modules.visuals.OmniVisual

/** Set button executes function clicked when clicked, but is similar to pinup visual otherwise
 * it needs to be loaded with basic size parameters, in the form of block
 * and it needs on and off visuals
 */
class SetButton(id: String): UiElement(id) {
    override var block: LcsRect = GetLcsRect.getZero()
        set(value) { //setter helps us to adjust block on every change, this is quite cool we need more of this
            field = value
            adjustVisuals()
        }



    private var drawIndex = 0
    private var visualList =  listOf<OmniVisual>()
    var clicked = {println("$id clicked")}
    private var fitImage = false

    /** This constructor takes a block and it will take visuals later
     */
    constructor(id: String, block: LcsRect): this(id){
        println("at constructor")
        this.block = block
    }

    /** This constructor takes images and it will take the block later
     */
    constructor(id: String, onVisual: OmniVisual, offVisual: OmniVisual, fitImage: Boolean = true): this(id){
        this.fitImage = fitImage
        setVisuals(onVisual,offVisual,fitImage)
    }

    /** This constructor takes all the inputs at start
     */
    constructor(id:String, block: LcsRect,onVisual: OmniVisual, offVisual: OmniVisual, fitImage: Boolean = true): this(id){
        this.block = block
        this.fitImage = fitImage
        setVisuals(onVisual, offVisual, fitImage)
    }

    /** Sets visuals after block is initialized
     * on and off visuals are self explanatory
     * fitImage stretches the image to the size of the block
     */
    fun setVisuals(onVisual: OmniVisual,offVisual: OmniVisual,fitImage: Boolean){
        this.fitImage = fitImage
        visualList = listOf(onVisual,offVisual)
        adjustVisuals()
    }

    /** helper to setVisual that does the actual execution
     *
     */
    private fun adjustVisuals(){
        visualList.forEach {
            if (fitImage){
                it.resize(block.width,block.height)
            }
            it.relocate(block.cX,block.cY)
        }
    }

    /** Handles touching
     * hierarchy is handled by the callers in layout
     */
    override fun touchHandler(): Boolean {
        val containing = block.contains(GetLcs.ofX(),GetLcs.ofY())
        if (drawIndex==1){
            drawIndex = if (Gdx.input.isTouched){
                if (containing) 1 else 0
            } else{
                clicked()
                0
            }
        } else {
            if (Gdx.input.justTouched()){
                drawIndex = if(containing) 1 else 0
            }
        }
        return containing
    }


    /** Runs on every update
     */
    override fun update() {}

    /** Relocates the button to x y
     */
    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width,block.height,x,y)
        visualList.forEach {
            it.relocate(x,y)
        }

    }

    /** Resizes the block by w and h
     */
    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w,h,block.cX,block.cY)
        visualList.forEach {
            it.resize(w,h)
        }
    }

    /*
    override fun reblock(r: LcsRect) {
        visualList.forEach {
            it.resize(r.width,r.height)
            it.relocate(r.cX,r.cY)
        }
        block = r
    }

     */

    /** Called on every draw
     */
    override fun draw(batch: SpriteBatch) {
        if (visible){
            visualList[drawIndex].draw(batch)
        }
    }

    override fun dispose() {
        visualList.forEach {
            it.dispose()
        }
    }
}