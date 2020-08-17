package modules.uiElements.Layouts

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import modules.uiElements.PlaceholderElement
import modules.uiElements.UiElement


abstract class OmniLayout(id: String, rect: LcsRect): UiElement(id) {
    override var block: LcsRect = rect
        set(value) {
            field = value
            adjustSubBlocks(value)
        }
    protected var elements =  mutableListOf<UiElement>()
    protected var subBlocks =  mutableListOf<LcsRect>()
        set(value){
            field = value
            field.forEachIndexed { index, it ->
                try{
                    elements[index]
                } catch(e: IndexOutOfBoundsException){
                    elements.add(PlaceholderElement("${id}n"))
                }
            }
            adjustElements()
        }






    protected abstract fun adjustSubBlocks(newBlock: LcsRect) //This function is a helper to reorganise all the elements after a difference is made to the block must contain a setting of subBlocks
    protected abstract fun adjustElements() //This function is a helper to reorganise all the elements after a difference is made to the block



    override fun update() {
        elements.forEach {
            it.update()
        }
    }

    /** Relocates the layout, to a spesific x y location
     */
    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width,block.height,x,y)
    }

    /** Resizes the layout to a spesific width and height location
     */
    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w,h,block.cX,block.cY)
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
        elements[n] = adjustElementTo(e,subBlocks[n])
    }





}

