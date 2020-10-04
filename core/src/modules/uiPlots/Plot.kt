package modules.uiPlots

import modules.basic.geometry.FastGeometry
import modules.basic.geometry.Rectangle
import modules.uiElements.UiElement

class Plot(val id: String, var estate: Rectangle = FastGeometry.unitSquare(), var z: Int=0, var element:  UiElement? = null) {


    fun merge(mergedId: String, other: Plot, z: Int?=null): Plot {
        return Plot(mergedId, estate + other.estate,z ?: (this.z).coerceAtLeast(other.z))
    }


    fun gridEqual(tag: String, r: Int=1, c: Int=1): MutableList<Plot> {
        val returning = mutableListOf<Plot>()
        repeat(r){ri->
            repeat(c){ci->
                val left = estate.left + estate.width/c*ci
                val right = estate.left + estate.width/c*(ci+1)
                val top = estate.top - estate.height/r*(ri+1)
                val bottom = estate.top - estate.height/r*(ri)
                val rect = Rectangle(left,right,top,bottom)
                val plot = Plot("${tag}_r${ri}_c$ci",rect,z+1)
                returning.add(plot)
            }
        }
        return returning
    }


    fun gridBiased(tag: String, r: List<Float>, c: List<Float>): MutableList<Plot> {
        var nRows = r.map{it/r.sum()}
        var nCols = c.map{it/c.sum()}
        val returning = mutableListOf<Plot>()
        var rowAcc = 0f
        nRows.forEachIndexed {ri,row->
            var colAcc = 0f
            val bottom = 1 - (rowAcc + row)
            val top = 1 - rowAcc
            nCols.forEachIndexed {ci,col->
                val left = colAcc
                val right = colAcc + col
                colAcc +=col
                val plot = Plot("${tag}_r${ri}_c$ci",Rectangle(left,right,top,bottom),z+1)
                returning.add(plot)

            }
            rowAcc +=row

        }
        return returning




    }

    /** Just in case
     */
    fun dispose(){
        try {
            element!!.dispose()
        } catch (e: Exception){
            println("error at disposing")
        }


    }

    fun update(){
        element?.update()
    }

}