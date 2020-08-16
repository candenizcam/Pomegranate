package modules.Layout

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable


abstract class OmniLayout(id: String, rect: LcsRect): UiElement(id) {
    override var block: LcsRect = rect
    protected var elements =  mutableListOf<UiElement>()
    protected var blocks =  mutableListOf<LcsRect>()
    protected var stepsList = listOf(1f)
    init{
        isEquallyDividedTo(1)
    }



    abstract fun isDividedToBiased(n: List<Float>) //divides the layout to a list of bias numbers

    /** This function is a helper to reorganise all the elements after a difference is made to the block
     */
    private fun updateBlock(){
        val bottle = elements
        isDividedToBiased(stepsList)
        for (i in bottle.indices){
            replaceElement(i,bottle[i],bottle[i].stretch)
        }
    }

    /** Creates an equally divided layout with n members
     * This class works with isDividedToBiased but it has simpler usage
     */
    fun isEquallyDividedTo(n: Int){
        val b = mutableListOf<Float>()
        for(i in 0 until n){
            b.add(1f)
        }
        isDividedToBiased(b)
    }

    override fun update() {}

    /** Relocates the layout, to a spesific x y location
     */
    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width,block.height,x,y)
        updateBlock()
    }

    /** Resizes the layout to a spesific width and height location
     */
    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w,h,block.cX,block.cY)
        updateBlock()
    }

    override fun draw(batch: SpriteBatch) {
        if(visible){
            elements.forEach {
                it.draw(batch)
            }
        }
    }

    /** Replaces the nth element of the layout this function is the standard way to put an element to the layout
     */
    fun replaceElement(n: Int, e: UiElement, stretch: Boolean){
        e.stretch = stretch
        if(stretch){
            e.resize(blocks[n].width,blocks[n].height)
        }
        e.relocate(blocks[n].cX,blocks[n].cY)
        elements[n] = e
    }

}

