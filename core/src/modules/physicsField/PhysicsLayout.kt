package com.pungo.engine.physicsField

import com.badlogic.gdx.graphics.Color
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.uiElements.PinupImage
import modules.uiElements.layouts.OmniLayout
import modules.uiElements.UiElement

class PhysicsLayout(id: String, rect: LcsRect, r:Int, c:Int):OmniLayout(id,rect) {
    val pf = PhysicsField(r, c)
    override var block: LcsRect = rect
        set(value) {
            adjustSubBlocks(value)
            field = value

        }


    fun addPhysicsSquare(id: String,  row: Float, column: Float,side: Float = 1f, vX: Float = 0f, vY: Float = 0f, mass: Float = 0f, mobility: Boolean) {
        addPhysicsItem(RectangleMass(id, w = side, h = side, mass = mass, cX = column + 0.5f, cY = row + 0.5f, vX = vX, vY = vY, mobility = mobility))
    }

    fun addPhysicsItem(i: PhysicsItem) {
        if (pf.items.any { it.id == id }) {
            throw Exception("ID clash at add physics item for $id")
        }
        pf.addItem(i)
    }

    fun attachToPhysicsItem(id: String, e: UiElement, takeElementSize: Boolean = true) {
        val thatItem = pf.items[pf.items.indexOfFirst { it.id == id }]
        val index = elements.indexOfFirst { it.id==id }
        if(index>0){
            subBlocks.removeAt(index)
        }
        if(takeElementSize){
            addElement(e,GetLcsRect.byParameters(block.width/pf.colNo*thatItem.pid.w,block.height/pf.rowNo*thatItem.pid.h,block.width/pf.colNo*thatItem.pid.cX,block.height/pf.rowNo*thatItem.pid.cY),true)
        } else{
            addElement(e,e.block)
        }
        thatItem.elementPointer = e
    }

    fun moveElement(id:String,x: Float, y: Float){

        elements.forEachIndexed { index, it ->
            if(it.id==id){
                it.relocate(block.wStart + block.width*x,block.hStart + block.height*y)
                subBlocks[index] = it.block
            }
        }
    }


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
            adjustElementTo(elements[index],it2)
        }
    }

    /** Adds an element by id
     */
    fun addElement(e: UiElement, r: LcsRect, stretch: Boolean = false){
        elements.forEach {
            if(it.id==e.id){
                throw(Exception("ID CLASH IN $id"))
            }
        }
        subBlocks.add(r)
        adjustElementTo(e,r)
        elements.add(e)
    }

    override fun update() {
        pf.update()
        pf.items.forEach {
            moveElement(it.id,it.pid.cX/pf.colNo,it.pid.cY/pf.rowNo)
        }
        elements.forEach {
            it.update()
        }

    }

    fun getPhysicsCoordOfLcsPoint(x: LcsVariable,y: LcsVariable): Pair<Float, Float> {
        var phiX = ((x-block.wStart)/block.width).asLcs()*pf.colNo
        var phiY = ((y-block.hStart)/block.height).asLcs()*pf.rowNo
        if(phiX<0||phiX>pf.colNo) phiX = -1f
        if(phiY<0||phiY>pf.rowNo) phiY = -1f
        return Pair(phiX,phiY)
    }

    fun getLcsVariablesForPhysicsCoord(x: Float,y:Float): Pair<LcsVariable, LcsVariable> {
        val lcX = block.width*(x/pf.colNo) + block.wStart
        val lcY = block.height*(y/pf.rowNo) + block.hStart
        return Pair(lcX,lcY)
    }

    fun getLcsOfPhysicsWidth(w: Float): LcsVariable {
        return block.width*(w/pf.colNo)
    }

    fun getLcsOfPhysicsHeight(h: Float): LcsVariable{
        return block.height*(h/pf.rowNo)
    }

    fun getPhysicsWidthOfLcs(w: LcsVariable): Float {
        return (w/block.width).asLcs()*pf.colNo
    }

    fun getPhysicsHeightOfLcs(h: LcsVariable): Float {
        return (h/block.height).asLcs()*pf.rowNo
    }


}