package modules.uiElements.interactableGrid

import com.badlogic.gdx.graphics.Color
import modules.uiElements.SelectableImage
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.uiElements.HorizontalIncrementer
import modules.uiElements.PinupImage
import modules.uiElements.layouts.ColLayout
import modules.uiElements.layouts.RowLayout
import modules.visuals.*

class BlockMenuLayout(id: String): RowLayout(id,GetLcsRect.ofFullScreen()) {

    //var pages = (0..0)
    var activePage = 0
    //private var brusherButton = MultiSetButton("brusherButton")
    private var selectablesList = mutableListOf<SelectableImage>(generateEraser())
    init{
        divideBlocksToBiased(listOf(0.05f,0.1f,0.05f,0.7f,0.1f))
        replaceElement(1, ColLayout("brusher",block).also { brusher->
            brusher.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            brusher.replaceElement(1, PinupImage("brusherText", BlockText("Brush Selection Page:",36, Color.WHITE,"fonts/PTMono-Regular.ttf")))
            brusher.replaceElement(3, getPageIncrementer())
            //brusher.replaceElement(3,brusherButton)
        })
        val rows = RowLayout("rows",GetLcsRect.ofFullScreen()).also {
            it.divideBlocksToBiased(listOf(1f,1f,1f))
            it.replaceElement(0,ColLayout("row0",GetLcsRect.ofFullScreen()).also {
                it.divideBlocksToBiased(listOf(1f,1f,1f,1f,1f))
            })
            it.replaceElement(1,ColLayout("row1",GetLcsRect.ofFullScreen()).also {
                it.divideBlocksToBiased(listOf(1f,1f,1f,1f,1f))
            })
            it.replaceElement(2,ColLayout("row2",GetLcsRect.ofFullScreen()).also {
                it.divideBlocksToBiased(listOf(1f,1f,1f,1f,1f))
            })
        }
        //(rows.getElement("row0") as ColLayout).replaceElement(0,)


        replaceElement(3,rows)
        disperseTools()


    }

    private fun getPageIncrementer(): HorizontalIncrementer{
        val pages = 0..(selectablesList.size/15)
        return HorizontalIncrementer("page","fonts/PTMono-Regular.ttf", Color(0.4f,0.4f,0.4f,1f),36,0, optionsList = pages.toList()).also {
            it.numChangeFunc = {
                activePage = it.theValue as Int
                disperseTools()
            }

        }

    }

    fun generateEraser(): SelectableImage {
        val eraseOV = TwoVisuals(
                BlockText("Eraser",36, Color.WHITE,"fonts/PTMono-Regular.ttf"),
                ColouredBox(GetLcsRect.byParameters(GetLcs.byPixel(100f), GetLcs.byPixel(100f)),Color.CORAL).also { it.visualSize = VisualSize.FIT_ELEMENT }
        )
        eraseOV.visualSize = VisualSize.FIT_ELEMENT
        return SelectableImage("eraser",Color.FOREST,eraseOV)
    }


    fun updateBrusherButton(brushes: List<Pair<String, OmniVisual>>, brushSwitchFun: (String)->Unit){
        selectablesList = mutableListOf(generateEraser().also {
            it.selectFunc = {
                brushSwitchFun("eraser")
                selectablesList.forEachIndexed{index,it2->
                    if(index>0) it2.selected = false
                }
            }


        })
        brushes.forEachIndexed{ index,it->
            SelectableImage(it.first,Color.FOREST,it.second.copy()).also {it2->
                it2.selectFunc = {
                    selectablesList.forEachIndexed{index2,it3->
                        if(index+1 != index2 ) it3.selected = false
                    }

                    brushSwitchFun(it.first)
                }
                selectablesList.add(it2)

            }

        }



        (getElement("brusher") as ColLayout).replaceElement("page",getPageIncrementer())
        disperseTools()

    }

    fun disperseTools(){
        selectablesList.forEachIndexed() { index, it ->
            if (index in activePage * 15 until (activePage + 1) * 15) {
                val relevantIndex = index - activePage * 15
                ((getElement("rows") as RowLayout).getElement("row${relevantIndex / 5}") as ColLayout).replaceElement(relevantIndex.rem(5), it)
            }
        }
    }

    override fun adjustElements() {
        super.adjustElements()
        try{
            disperseTools()
        }catch (e: Exception){
        }

    }
}




