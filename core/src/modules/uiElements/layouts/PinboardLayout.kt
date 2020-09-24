package modules.uiElements.layouts

import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.uiElements.PlaceholderElement
import modules.uiElements.UiElement

class PinboardLayout(id:String, rect: LcsRect): OmniLayout(id,rect) {
    override var block: LcsRect = rect
        set(value) {
            adjustSubBlocks(value)
            field = value

        }

    /** Adds a plot with ratios to the main board
     */
    fun addPlot(id: String, ws: Float, we: Float, hs: Float, he: Float) {
        val nws = block.wStart + block.width * ws
        val nwe = block.wStart + block.width * we
        val nhs = block.hStart + block.height * hs
        val nhe = block.hStart + block.height * he
        addPlot(id, GetLcsRect.byBorders(nws, nwe, nhs, nhe))
    }

    /** Adds a plot with placeholder id
     */
    fun addPlot(id: String, rect: LcsRect) {
        addElement(PlaceholderElement(id), rect)
    }

    /** Removes element by id
     */
    fun removeElement(id: String) {
        elements.forEach {
            if (it.id == id) {
                val i = elements.indexOfFirst { it2 -> it2.id == id }
                elements.removeAt(i)
                subBlocks.removeAt(i)
                return
            }
        }

    }

    /** Adds an element by id
     */
    fun addElement(e: UiElement, r: LcsRect) {
        elements.forEach {
            if (it.id == e.id) {
                throw(Exception("ID CLASH IN $id"))
            }
        }
        subBlocks.add(r)
        adjustElementTo(e, r)
        elements.add(e)
    }

    /** Replaces element with given id
     */
    fun replaceElement(id: String, e: UiElement, r: LcsRect, stretch: Boolean = false) {
        elements.forEach {
            if (it.id == id) {
                val i = elements.indexOfFirst { it2 -> it2.id == id }
                subBlocks[i] = r
                adjustElementTo(e, r)
                elements[i] = e
                return
            }
        }
    }


    /** Adjusts subblocks to new block
     */
    override fun adjustSubBlocks(newBlock: LcsRect) {
        val pb = mutableListOf<LcsRect>()
        subBlocks.forEach {
            val widthRatio = newBlock.width / block.width
            val heightRatio = newBlock.height / block.height
            val ws = (it.wStart - block.wStart) * widthRatio + newBlock.wStart
            val we = (it.wEnd - block.wStart) * widthRatio + newBlock.wStart
            val hs = (it.hStart - block.hStart) * heightRatio + newBlock.hStart
            val he = (it.hEnd - block.hStart) * heightRatio + newBlock.hStart
            pb.add(GetLcsRect.byBorders(ws, we, hs, he))
        }
        subBlocks = pb
    }

    override fun adjustElements() {
        subBlocks.forEachIndexed { index, it2 ->
            adjustElementTo(elements[index], it2)
        }
    }
}