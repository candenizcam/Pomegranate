package modules.uiElements.layouts

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.uiElements.PlaceholderElement
import modules.uiElements.UiElement


abstract class OmniLayout(id: String, rect: LcsRect) : UiElement(id) {
    override var block: LcsRect = rect
        set(value) {
            field = value
            adjustSubBlocks(value)
        }
    protected var elements = mutableListOf<UiElement>()
    protected var subBlocks = mutableListOf<LcsRect>()
        set(value) {
            field = value
            field.forEachIndexed { index, it ->
                try {
                    elements[index]
                } catch (e: IndexOutOfBoundsException) {
                    elements.forEach {
                        if (it.id == "${id}_$index") throw Exception("ID clash at $id")
                    }
                    elements.add(PlaceholderElement("${id}_$index"))
                }
            }
            adjustElements()
        }




    protected abstract fun adjustSubBlocks(newBlock: LcsRect) //This function is a helper to reorganise all the elements after a difference is made to the block must contain a setting of subBlocks
    protected abstract fun adjustElements() //This function is a helper to reorganise all the elements after a difference is made to the block



    override fun update() {
        if(visible){
            elements.forEach {
                it.update()
            }
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

    /** Replaces the nth element of the layout this function is the standard way to put an element to the layout
     */
    fun replaceElement(n: Int, e: UiElement) {
        for (i in elements.indices) {
            if (i == n) {
                adjustElementTo(e, subBlocks[n])
                elements[n] = e
            } else {
                if (elements[i].id == e.id) throw Exception("ID clash at $id")
            }
        }
    }

    /** Overloads the above function
     *
     */
    fun replaceElement(id: String, e: UiElement) {
        elements.forEach {
            if (it.id == id) {
                val i = elements.indexOfFirst { it2 -> it2.id == id }
                adjustElementTo(e, subBlocks[i])
                elements[i] = e
                return
            } else if (it.id == e.id) {
                throw Exception("ID clash at $id")
            }
        }
    }


    fun getElement(id: String): UiElement {
        elements.forEach {
            println("id: ${it.id}")
        }

        val l = id.split("&")
        elements.forEach {
            if (it.id == l.first()) {
                return if (l.size == 1) {
                    it
                } else {
                    if (it is OmniLayout) {
                        it.getElement(l.subList(1, l.lastIndex + 1).joinToString("&"))
                    } else {
                        throw Exception("id depth is not matched")
                    }

                }
            }
        }
        throw Exception("id not found")
    }

    /** This is a decorator function to above spesifically designed to get the block of an element, which can then
     * be used for initiating the element put in
     * There may be a way to automate this better
     */
    override fun touchHandler(mayTouch: Boolean): Boolean {
        return if(visible){
            var b = mayTouch.not()
            subBlocks.reversed().forEachIndexed { index, it ->
                if (it.contains(GetLcs.ofX(), GetLcs.ofY())) {
                    if (elements.reversed()[index].touchHandler(b.not())) b = true
                } else {
                    elements.reversed()[index].touchHandler(false)
                }
            }
            b
        } else{
            false
        }
    }


    fun toTop(id: String) {
        val n = elements.indexOfFirst { it.id == id }
        if (n < 0) {
            throw Exception("Index Not Found")
        }
        subBlocks.add(subBlocks.removeAt(n))
        elements.add(elements.removeAt(n))
    }

    fun toBottom(id: String) {
        val n = elements.indexOfFirst { it.id == id }
        if (n < 0) {
            throw Exception("Index Not Found")
        }
        subBlocks.add(0, subBlocks.removeAt(n))
        elements.add(0, elements.removeAt(n))

    }



    override fun draw(batch: SpriteBatch,alpha: Float) {
        if (visible) {
            elements.forEach {
                it.draw(batch,alpha)
            }
        }
    }

    override fun dispose() {
        elements.forEach {
            it.dispose()
        }
    }


}