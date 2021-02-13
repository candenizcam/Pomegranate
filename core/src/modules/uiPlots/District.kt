package com.pungo.modules.uiPlots

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.FastGeometry
import com.pungo.modules.basic.geometry.Origin
import com.pungo.modules.basic.geometry.Point
import com.pungo.modules.basic.geometry.Rectangle
//import com.pungo.modules.lcsModule.GetLcsRect
//import com.pungo.modules.lcsModule.LcsVariable
import modules.uiPlots.DistrictSplits

//import com.pungo.modules.uiElements.TextBox

open class District(private var districtId: String): DistrictSplits(){
    open var plots = mutableListOf<Plot>() // these are references to the block
    //var block = GetLcsRect.ofFullScreen() // size is only changed by this
    //var sameSizeTextBoxList = mutableListOf<List<TextBox>>()

    /** This is used to find the plots in a specified lcs variable point on the screen
     * mouse click could be a good example
     */
    //fun findPlot(x: LcsVariable, y: LcsVariable): List<Plot> {
    //    return findPlot(Point(block.getWidthRatio(x), block.getHeightRatio(y)))
    //}

    /** This finds plots in specific main rectangle coordinate (between 0 & 1)
     */
    fun findPlot(p: Point): List<Plot> {
        return plots.filter { it.estate.contains(p) }
    }

    /** movement check returns true if there is movement
     */
    fun movementCheck(): Boolean {
        return !plots.none { it.locations.moving }
    }


    /** This finds the plot with the spesific id
     */
    fun findPlot(id: String): Plot {
        try {
            return plots.filter { it.id == id }[0]
        } catch (e: Exception) {
            throw Exception("no id: $id found")
        }
    }

    /** This generates the id by adding row & col values
     *
     */
    fun findPlot(id: String, row: Int, col: Int): Plot {
        try {
            return plots.filter { it.id == "${id}_r${row}_c${col}" }[0]
        } catch (e: Exception) {
            throw Exception("no id ${"${id}_r${row}_c${col}"} found")
        }
    }

    override fun addToPlots(p: Plot) {
        plots.add(p)
        if (plots.size != plots.distinctBy { it.id }.size) throw Exception("id clasht at district: $districtId")
    }

    override fun addToPlots(p: List<Plot>) {
        plots.addAll(p)
        if (plots.size != plots.distinctBy { it.id }.size) throw Exception("id clasht at district: $districtId")
    }








    fun setVisible(id: String, visible: Boolean) {
        /*
        plots.first { it.id == id }.element?.visible = visible

         */
    }

    fun makeAllInvisible() {
        /*
        plots.forEach {
            it.element?.visible = false
        }

         */
    }


    open fun draw(batch: SpriteBatch, alpha: Float = 1f, faceOther: Boolean = false) {
        /*
        plots.sortedBy { it.z }.forEach {
            try {
                val b = if (faceOther) block.getLcsRectFromGeo(it.estate.flipX(0.5f)) else block.getLcsRectFromGeo(it.estate)
                it.element?.reBlock(b)
            } catch (e: NullPointerException) {
            }
        }



        sameSizeTextBoxList.forEach {
            val a = it.map { it2 -> it2.fittingPuntos() }
            var b = a.first().toSet()
            repeat(a.size - 1) { i ->
                b = b.intersect(a[i + 1])
            }
            it.forEach {
                it.forcedSize = if (b.isNotEmpty()) b.last() else null
            }
        }


        plots.sortedBy { it.z }.forEach {
            try {
                it.element?.draw(batch, alpha)
            } catch (e: NullPointerException) {
            }
        }

         */


    }

    open fun update() {
        plots.forEach {
            it.update()
        }
    }

    fun dispose() {
        plots.forEach {
            it.dispose()
        }
    }

    fun movePlot(id: String, dx: Float, dy: Float) {
        findPlot(id).movePlot(dx, dy)
    }

    //fun movePlot(id: String, dx: LcsVariable, dy: LcsVariable) {
    //    findPlot(id).movePlot(block.width / dx, block.height / dy)
    //}


    fun addSameSizeTextBoxListById(idList: List<String>) {
        /*
        sameSizeTextBoxList.add(idList.map {
            findPlot(it).element as TextBox
        })

         */
    }

/*
    fun addSameSizeTextBoxList(tbList: List<TextBox>) {
        /*
        sameSizeTextBoxList.add(tbList)

         */
    }

 */


}