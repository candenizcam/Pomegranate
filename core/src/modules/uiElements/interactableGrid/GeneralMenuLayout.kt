package modules.uiElements.interactableGrid

import com.badlogic.gdx.graphics.Color
import modules.lcsModule.GetLcsRect
import modules.uiElements.HorizontalIncrementer
import modules.uiElements.PinupImage
import modules.uiElements.layouts.ColLayout
import modules.uiElements.layouts.RowLayout
import modules.visuals.fromFont.BlockText

class GeneralMenuLayout(rowsNo: Int, colsNo: Int, val igd: InteractableGridData): RowLayout("generalMenuLayout", GetLcsRect.ofFullScreen()) {
    val rowText = BlockText("$rowsNo", 36, Color.WHITE, "fonts/PTMono-Regular.ttf")
    val colText = BlockText("$colsNo", 36, Color.WHITE, "fonts/PTMono-Regular.ttf")
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
    var pxRatioList = listOf("640x480", "800x600", "960x720", "1024x576", "1024x768", "1152x648", "1280x720", "1280x800", "1280x960", "1366x768", "1400x1050", "1440x900", "1440x1080", "1600x900", "1600x1200", "1680x1050", "1856x1392", "1920x1080", "1920x1200", "1920x1440", "2048x1536", "2560x1440", "2560x1600", "3840x2160","6400x6400")


    init{
        divideBlocksToBiased(listOf(1f,1.5f,1f,1.5f,1f,1.5f,1f))
        replaceElement(1, ColLayout("widther",block).also { widther->
            widther.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            widther.replaceElement(1, PinupImage("widtherText", BlockText("Pixel Ratio:", 36, Color.WHITE, "fonts/PTMono-Regular.ttf")))
            widther.replaceElement(3, HorizontalIncrementer("widthInc","fonts/PTMono-Regular.ttf", Color(0.4f,0.4f,0.4f,1f),36,0, optionsList = pxRatioList).also {
                it.numChangeFunc = {
                    pxRatio = it.theValue as String
                }
            })
        })

        replaceElement(3, ColLayout("rower",block).also { rower->
            rower.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            rower.replaceElement(1, PinupImage("rowerText", BlockText("Row Number:", 36, Color.WHITE, "fonts/PTMono-Regular.ttf")))
            rower.replaceElement(3, HorizontalIncrementer("rowInc","fonts/PTMono-Regular.ttf", Color(0.4f,0.4f,0.4f,1f),36,5,optionsList = (1..100).toList()).also {
                it.numChangeFunc = {
                    rowNo = (it.theValue as Int)
                }
            })

        })
        replaceElement(5, ColLayout("coler",block).also { coler->
            coler.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            coler.replaceElement(1, PinupImage("colerText", BlockText("Col Number:", 36, Color.WHITE, "fonts/PTMono-Regular.ttf")))
            coler.replaceElement(3, HorizontalIncrementer("colInc","fonts/PTMono-Regular.ttf", Color(0.4f,0.4f,0.4f,1f),36,5,optionsList = (1..100).toList()).also {
                it.numChangeFunc = {
                    colNo = (it.theValue as Int)
                }
            })
        })


    }


}