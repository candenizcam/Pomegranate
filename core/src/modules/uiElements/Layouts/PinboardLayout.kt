package modules.uiElements.Layouts

import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.uiElements.UiElement

class PinboardLayout(id:String, rect: LcsRect): OmniLayout(id,rect) {
    override var block: LcsRect = rect
        set(value) {
            adjustSubBlocks(value)
            field = value

        }

    fun addSubBlock(id: String, rect: LcsRect){

    }

    fun removeElement(id: String){
        elements.forEach {
            if(it.id==id){
                val i = elements.indexOfFirst {it2-> it2.id == id }
                elements.removeAt(i)
                subBlocks.removeAt(i)
                return
            }
        }

    }

    fun addElement(e: UiElement, r: LcsRect,stretch: Boolean = false){
        elements.forEach {
            if(it.id==e.id){
                throw(Exception("ID CLASH IN $id"))
            }
        }
        subBlocks.add(r)
        e.stretch = stretch
        elements.add(adjustElementTo(e,r))
    }

    fun replaceElement(id:String,e: UiElement, r:LcsRect){
        elements.forEach {
            if(it.id==id){
                val i = elements.indexOfFirst {it2-> it2.id == id }
                subBlocks[i] = r
                adjustElementTo(e,r)
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
            val widthRatio = newBlock.width/block.width
            val heightRatio = newBlock.height/block.height
            val ws = (it.wStart-block.wStart)*widthRatio + newBlock.wStart
            val we = (it.wEnd-block.wStart)*widthRatio + newBlock.wStart
            val hs = (it.hStart-block.hStart)*heightRatio + newBlock.hStart
            val he = (it.hEnd-block.hStart)*heightRatio + newBlock.hStart
            pb.add(GetLcsRect.byBorders(ws,we,hs,he))
        }
        subBlocks = pb
    }

    override fun adjustElements() {
        subBlocks.forEachIndexed { index, it2 ->
            elements[index] = adjustElementTo(elements[index],it2)
        }
    }
}