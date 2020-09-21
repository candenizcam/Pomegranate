package modules.uiElements.interactableGrid

import modules.uiElements.interactableGrid.ImageSelectedLayout
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsVariable
import kotlin.math.floor


data class InteractableGridData(var row: Int, var col: Int, var menuOpen: Boolean=false, var foil : Foils, var pxRatio: String= "1920x1080") {


    var gridBlock = GetLcsRect.ofZero()
    var pxRatioList = listOf("640x480", "800x600", "960x720", "1024x576", "1024x768", "1152x648", "1280x720", "1280x800", "1280x960", "1366x768", "1400x1050", "1440x900", "1440x1080", "1600x900", "1600x1200", "1680x1050", "1856x1392", "1920x1080", "1920x1200", "1920x1440", "2048x1536", "2560x1440", "2560x1600", "3840x2160")
    var frontVisualSelected: Boolean= false
    var backVisualSelected: Boolean= false
    val frontSelectedMenu = ImageSelectedLayout("frontSelected")
    val backSelectedMenu = ImageSelectedLayout("backSelected")


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

