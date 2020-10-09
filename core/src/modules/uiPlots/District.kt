package modules.uiPlots

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.basic.geometry.FastGeometry
import modules.basic.geometry.Point
import modules.basic.geometry.Rectangle
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsVariable
import java.lang.NullPointerException

open class District(var districtId: String) {
    var plots = mutableListOf(Plot("main"))
    var block = GetLcsRect.ofFullScreen()

    init{

    }

    /** This is used to find the plots in a specified lcs variable point on the screen
     * mouse click could be a good example
     */
    fun findPlot(x: LcsVariable, y: LcsVariable): List<Plot> {
        return findPlot(Point(block.getWidthRatio(x),block.getHeightRatio(y)))
    }

    /** This finds plots in specific main rectangle coordinate (between 0 & 1)
     */
    fun findPlot(p: Point): List<Plot> {
        return plots.filter{it.estate.contains(p)}
    }


    /** This finds the plot with the spesific id
     */
    fun findPlot(id: String): Plot {
        try{
            return plots.filter{it.id  == id}[0]
        } catch (e: Exception){
            throw Exception("no id: $id found")
        }
    }

    /** This generates the id by adding row & col values
     *
     */
    fun findPlot(id: String, row: Int, col: Int): Plot{
        try{
            return plots.filter{it.id  == "${id}_r${row}_c${col}"}[0]
        } catch (e: Exception){
            throw Exception("no id ${"${id}_r${row}_c${col}"} found")
        }
    }

    fun addToPlots(p: Plot){
        plots.add(p)
        if(plots.size != plots.distinctBy { it.id }.size) throw Exception("id clasht at district: $districtId")
    }

    fun addToPlots(p: List<Plot>){
        plots.addAll(p)
        if(plots.size != plots.distinctBy { it.id }.size) throw Exception("id clasht at district: $districtId")
    }

    /** This merges two plots to create a super plot and adds that to plots
     * the bottom left and top right corners of the given ids are used
     */
    fun superPlot(plotId: String, p1: Plot, p2: Plot, z: Int?=null): Plot {
        return p1.merge(plotId,p2,z)
    }

    /** This function takes an id and a rectangle as input and creates a slicing of the given rectangle, then adds stuff to the plots
     * id is the name of the plot
     * r is the rectangle that define borders
     * row & col are for the partition
     * retainOriginal adds the unpartitioned plot to the plots
     */
    fun splitToPlots(id: String, r: Rectangle = FastGeometry.unitSquare(), row: Int=1, col: Int=1, retainOriginal: Boolean = false, z: Int=0){
        val bigPlot = Plot(id,r,z)
        val smallPlots = bigPlot.gridEqual(id,row,col)
        if(retainOriginal) plots.add(bigPlot)
        addToPlots(smallPlots)
    }

    fun splitToPlots(id: String, r: Rectangle = FastGeometry.unitSquare(), rows: List<Float> = listOf(1f), cols: List<Float> = listOf(1f), retainOriginal: Boolean = false, z: Int=0){
        val bigPlot = Plot(id,r,z)
        val smallPlots = bigPlot.gridBiased(id,rows,cols)
        if(retainOriginal) plots.add(bigPlot)
        addToPlots(smallPlots)
    }
    



    fun draw(batch: SpriteBatch, alpha: Float=1f){
        plots.sortedBy { it.z }.forEach {
            try{
                it.element?.reBlock( block.getLcsRectFromGeo(it.estate))
                it.element?.draw(batch,alpha)
            } catch(e: NullPointerException){}
        }

    }

    fun update(){
        plots.forEach {
            it.update()
        }
    }

    fun dispose(){
        plots.forEach {
            it.dispose()
        }
    }
}