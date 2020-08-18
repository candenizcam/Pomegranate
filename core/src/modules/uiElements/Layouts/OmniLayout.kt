package modules.uiElements.Layouts

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcs
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import modules.uiElements.PlaceholderElement
import modules.uiElements.UiElement
import java.lang.Exception


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
                    elements.forEach {
                        if(it.id=="${id}_$index") throw Exception("ID clash at $id")
                    }
                    elements.add(PlaceholderElement("${id}_$index"))
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
        for (i in elements.indices){
            if(i==n){
                elements[n] = adjustElementTo(e,subBlocks[n])
            } else{
                if (elements[i].id==e.id) throw Exception("ID clash at $id")
            }
        }
    }

    /** Overloads the above function
     *
     */
    fun replaceElement(id:String,e: UiElement, stretch: Boolean=false){
        e.stretch = stretch
        elements.forEach {
            if(it.id==id){
                val i = elements.indexOfFirst {it2-> it2.id == id }
                adjustElementTo(e,subBlocks[i])
                elements[i] = e
                return
            } else if(it.id==e.id){
                throw Exception("ID clash at $id")
            }
        }
    }



    fun getElement(id:String): UiElement {
        val l = id.split("&")
        elements.forEach {
            if (it.id ==l.first()){
                return if(l.size==1){
                    it
                } else{
                    if(it is OmniLayout){
                        it.getElement(l.subList(1,l.lastIndex+1).joinToString("&"))
                    } else{
                        throw Exception("id depth is not matched")
                    }

                }
            }
        }
        throw Exception("id not found")

    }

    /** Handles touch for all hierarchies
     */
    override fun touchHandler(): Boolean {
        subBlocks.reversed().forEachIndexed {index,it->
            if(it.contains(GetLcs.ofX(),GetLcs.ofY())){
                if (elements.reversed()[index].touchHandler()) return true
            }
        }
        return false

    }

    fun toTop(id: String){
        val n = elements.indexOfFirst {it.id==id  }
        if(n<0){
            throw Exception("Index Not Found")
        }
        subBlocks.add(subBlocks.removeAt(n))
        elements.add(elements.removeAt(n))
    }

    fun toBottom(id: String){
        val n = elements.indexOfFirst {it.id==id  }
        if(n>=0){
            throw Exception("Index Not Found")
        }
        subBlocks.add(0,subBlocks.removeAt(n))
        elements.add(0,elements.removeAt(n))

    }





}

