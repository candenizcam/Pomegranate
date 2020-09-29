package modules.uiElements.interactableGrid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.LcsVariable
import modules.visuals.OmniVisual
import kotlin.math.abs

class BlockFoil(val igd: InteractableGridData) {
    var blockVisualTypes= mutableListOf<Pair<String, OmniVisual>>() //this is the list of brushes
    var tempGridColours = mutableListOf<GridBlock>()
    var gridColours = mutableListOf<GridBlock>() //color row col
    var drawer = false //this variable is used to keep drawing
    var brushType = "red" //this is the brush type used with block painting
    var initialDrawCoords = Pair(0,0)

    fun drawBlocks(batch: SpriteBatch,alpha: Float=1f){
        if(brushType!="eraser"){
            gridColours.forEach {
                blockVisualTypes.first { it2-> it2.first==it.type }.apply {
                    second.relocate(igd.colAsX(it.col),igd.rowAsY(it.row))
                    second.draw(batch,alpha)
                }
            }
            tempGridColours.forEach{
                blockVisualTypes.first { it2-> it2.first==it.type }.apply {
                    second.relocate(igd.colAsX(it.col),igd.rowAsY(it.row))
                    second.draw(batch,alpha)
                }
            }
        }else{
            gridColours.forEach {
                if(tempGridColours.none { it2 -> it.row == it2.row && it.col == it2.col }){
                    blockVisualTypes.first { it2-> it2.first==it.type }.apply {
                        second.relocate(igd.colAsX(it.col),igd.rowAsY(it.row))
                        second.draw(batch,alpha)
                    }
                }
            }
        }

    }

    fun touchHandlerForBlocks(): Boolean {
        if(Gdx.input.isButtonJustPressed(1)){
            drawer=false
            tempGridColours.clear()
            // igd.menuOpen = true

        } else if(Gdx.input.isButtonPressed(0)){
            if(Gdx.input.justTouched() && igd.gridBlock.contains(GetLcs.ofX(), GetLcs.ofY())){
                drawer = true
            }
            if(!Gdx.input.isTouched){
                if(drawer) gridColours.addAll(tempGridColours)
                drawer=false
            }
            if(drawer) {
                val r = igd.yAsRow(GetLcs.ofY()).coerceAtLeast(0).coerceAtMost(igd.row-1)
                val c = igd.xAsCol(GetLcs.ofX()).coerceAtLeast(0).coerceAtMost(igd.col-1)
                if (Gdx.input.justTouched()) {
                    initialDrawCoords = Pair(r, c)
                }
                tempGridColours.clear()
                val xyList = mutableListOf(initialDrawCoords.second,initialDrawCoords.second,initialDrawCoords.first,initialDrawCoords.first)
                val n = if (abs(xyList[2] - r) > abs(xyList[0] - c)) 1 else 0
                xyList[if (xyList[n*2] > listOf(c,r)[n]) 2*n else 2*n+1] = listOf(c,r)[n]
                for (i in xyList[0]..xyList[1]) {
                    for (j in xyList[2]..xyList[3]) {
                        tempGridColours.filter { it.col == j && it.row == i }.also {
                            if (it.isEmpty()) {
                                tempGridColours.add(GridBlock(brushType, j, i))
                            } else {
                                it.first().type = brushType
                            }
                        }
                    }
                }
            }
        }
        else{
            if(brushType!="eraser"){
                tempGridColours.forEach {
                    val cell = gridColours.filter { it2 -> it.row == it2.row && it.col == it2.col }.toMutableList()
                    if (cell.isEmpty()) {
                        gridColours.add(it)
                    } else {
                        gridColours.remove(cell[0])
                        gridColours.add(it)
                    }
                }
            }else{
                tempGridColours.forEach {
                    gridColours = gridColours.filterNot { it2-> it.row==it2.row && it.col==it2.col }.toMutableList()
                }
            }
            tempGridColours.clear()
            drawer=false
        }
        return igd.gridBlock.contains(GetLcs.ofX(), GetLcs.ofY())
    }


    fun menuOpened(){
        drawer = false
    }

    fun resizeBlockVisuals(w: LcsVariable,h: LcsVariable){
        blockVisualTypes.forEach {
            it.second.resize(w,h)
        }
    }

    fun dispose(){
        blockVisualTypes.forEach {
            it.second.dispose()
        }
    }
}