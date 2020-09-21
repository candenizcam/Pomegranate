package modules.uiElements.interactableGrid

import com.badlogic.gdx.graphics.Color
import modules.uiElements.HorizontalIncrementer
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.uiElements.MultiSetButton
import modules.uiElements.PinupImage
import modules.uiElements.SetButton
import modules.uiElements.layouts.ColLayout
import modules.uiElements.layouts.RowLayout
import modules.visuals.*

class BlockMenuLayout(rowsNo: Int, colsNo: Int, val igd: InteractableGridData): RowLayout("blockMenuLayout",GetLcsRect.ofFullScreen()) {
    val rowText = BlockText("$rowsNo",36,Color.WHITE,"fonts/PTMono-Regular.ttf")
    val colText = BlockText("$colsNo",36,Color.WHITE,"fonts/PTMono-Regular.ttf")
    var rowNo: Int = rowsNo
        set(value) {
            field =value
            rowText.changeText("$value")
        }
    var colNo: Int = colsNo
        set(value) {
            field =value
            colText.changeText("$value")
        }
    var pxRatio = "640x480"

    private var brusherButton = MultiSetButton("brusherButton")
    init{
        divideBlocksToBiased(listOf(1f,2f,3f,1.5f,2.5f,1.5f,2f,1.5f,2f))
        replaceElement(1, ColLayout("brusher",block).also { brusher->
            brusher.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            brusher.replaceElement(1, PinupImage("brusherText", BlockText("Select Brush:",36, Color.WHITE,"fonts/PTMono-Regular.ttf")))
            brusher.replaceElement(3,brusherButton)
        })

        replaceElement(3,ColLayout("widther",block).also {widther->
            widther.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            widther.replaceElement(1,PinupImage("widtherText", BlockText("Pixel Ratio:",36,Color.WHITE,"fonts/PTMono-Regular.ttf")))
            widther.replaceElement(3,HorizontalIncrementer("widthInc","fonts/PTMono-Regular.ttf",Color(0.4f,0.4f,0.4f,1f),36,0, optionsList = igd.pxRatioList).also {
                it.numChangeFunc = {
                    pxRatio = it.theValue as String
                }
            })
        })

        replaceElement(5,ColLayout("rower",block).also {rower->
            rower.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            rower.replaceElement(1,PinupImage("rowerText", BlockText("Row Number:",36,Color.WHITE,"fonts/PTMono-Regular.ttf")))
            rower.replaceElement(3,HorizontalIncrementer("rowInc","fonts/PTMono-Regular.ttf",Color(0.4f,0.4f,0.4f,1f),36,5,optionsList = (1..100).toList()).also {
                it.numChangeFunc = {
                    rowNo = (it.theValue as Int)
                }
            })

        })
        replaceElement(7,ColLayout("coler",block).also {coler->
            coler.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            coler.replaceElement(1,PinupImage("colerText", BlockText("Col Number:",36,Color.WHITE,"fonts/PTMono-Regular.ttf")))
            coler.replaceElement(3,HorizontalIncrementer("colInc","fonts/PTMono-Regular.ttf",Color(0.4f,0.4f,0.4f,1f),36,5,optionsList = (1..100).toList()).also {
                it.numChangeFunc = {
                    colNo = (it.theValue as Int)
                }
            })
        })


    }

    fun updateBrusherButton(brushes: List<Pair<String, OmniVisual>>, brushSwitchFun: (String)->Unit){
        println("ubb called")

        brusherButton.addButton(generateEraser {brushSwitchFun(brushes[0].first)})

        brushes.forEachIndexed {index,it->
            val b = SetButton(it.first,it.second.copy(),it.second.copy().also {it2->
                val c = (it2 as ColouredBox).colour
                it2.recolour(Color(c.r*0.5f,c.g*0.5f,c.b*0.5f,1f))
            }).also { it2->
                if(index==brushes.lastIndex){
                    it2.clicked = {brushSwitchFun("eraser")}
                }else{
                    it2.clicked = {brushSwitchFun(brushes[index+1].first)}
                }
            }
            brusherButton.addButton(b)
        }
    }

    private fun generateEraser(f: ()->Unit): SetButton {
        val eraseOV = TwoVisuals(
                BlockText("Eraser",36, Color.WHITE,"fonts/PTMono-Regular.ttf"),
                ColouredBox(GetLcsRect.byParameters(GetLcs.byPixel(100f), GetLcs.byPixel(100f)),Color.CORAL).also { it.visualSize = VisualSize.FIT_ELEMENT }
        )
        eraseOV.visualSize = VisualSize.FIT_ELEMENT
        return SetButton("eraser",eraseOV.copy(),eraseOV.copy().also { it2->
            val c = ((it2 as TwoVisuals).back as ColouredBox).colour
            it2.back.recolour(Color(c.r*0.5f,c.g*0.5f,c.b*0.5f,1f))
        }).also { it2->
            it2.clicked = f
        }
    }
}




