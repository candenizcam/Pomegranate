package modules.uiElements.interactableGrid

import modules.uiElements.interactableGrid.ImageSelectedLayout
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsVariable
import kotlin.math.floor


data class InteractableGridData(var row: Int, var col: Int, var menuOpen: Boolean=false, var foil : Foils, var pxRatio: String= "1920x1080") {
    var gridBlock = GetLcsRect.ofZero()
    var frontVisualSelected: Boolean= false
    var backVisualSelected: Boolean= false


    fun getPxPair(): Pair<Int, Int> {
        pxRatio.split("x").also {
            return Pair(it[0].toInt(),it[1].toInt())
        }
    }



    fun rowAsY(r: Int): LcsVariable {
        return gridBlock.hStart + gridBlock.height - gridBlock.height/row*(r.toFloat()+0.5f)
    }

    fun colAsX(c: Int): LcsVariable {
        return gridBlock.wStart + gridBlock.width/col*(c.toFloat()+0.5f)
    }

    fun xAsCol(x: LcsVariable): Int {
        val w = gridBlock.width/col
        return floor((x-gridBlock.wStart).asLcs()/w.asLcs()).toInt()
    }

    fun yAsRow(y: LcsVariable): Int {
        val h = gridBlock.height/row
        return floor((GetLcs.ofHeight(1f)-(y+gridBlock.hStart)).asLcs()/h.asLcs()).toInt()
    }

}

