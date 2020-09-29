package modules.uiElements.interactableGrid

import com.badlogic.gdx.graphics.Color
import modules.lcsModule.GetLcsRect
import modules.uiElements.FastGenerator
import modules.uiElements.HorizontalIncrementer
import modules.uiElements.PinupImage
import modules.uiElements.layouts.ColLayout
import modules.uiElements.layouts.RowLayout
import modules.visuals.fromFont.BlockText

class ImageSelectedLayout(id: String): RowLayout(id, GetLcsRect.ofFullScreen()) {
    val delButton = FastGenerator.genericSetButton("del","DELETE",36, Color.LIGHT_GRAY,Color.WHITE,"fonts/PTMono-Regular.ttf")
    val copyButton = FastGenerator.genericSetButton("copy","COPY",36, Color.LIGHT_GRAY,Color.WHITE,"fonts/PTMono-Regular.ttf")

    val cXIncrementer= HorizontalIncrementer("colInc","fonts/PTMono-Regular.ttf",Color(0.4f,0.4f,0.4f,1f),36,5,largeIncrementer = 100,optionsList = (0..10000).toList())
    var rX = 0
        set(value) {
            field = value
            cXIncrementer.setValue(value)
        }
    val cYIncrementer = HorizontalIncrementer("colInc","fonts/PTMono-Regular.ttf",Color(0.4f,0.4f,0.4f,1f),36,5,optionsList = (0..10000).toList())
    var rY = 0
        set(value) {
            field = value
            cYIncrementer.setValue(value)
        }
    val toTop = FastGenerator.genericSetButton("toTop","To Top",36,Color.DARK_GRAY,Color.WHITE,"fonts/PTMono-Regular.ttf")
    val oneUp = FastGenerator.genericSetButton("oneUp","Up",36,Color.DARK_GRAY,Color.WHITE,"fonts/PTMono-Regular.ttf")
    val oneDown = FastGenerator.genericSetButton("oneDown","Down",36,Color.DARK_GRAY,Color.WHITE,"fonts/PTMono-Regular.ttf")
    val toBottom = FastGenerator.genericSetButton("toBottom","To Bottom",36,Color.DARK_GRAY,Color.WHITE,"fonts/PTMono-Regular.ttf")

    /*
    val cZIncrementer = HorizontalIncrementer("zInc","fonts/PTMono-Regular.ttf",Color(0.4f,0.4f,0.4f,1f),36,5,optionsList = (0..10000).toList())
    var rZ = 0
        set(value) {
            field = value
            cZIncrementer.setValue(value)
        }

     */

    init{
        divideBlocksToBiased(listOf(1f,2f,1f,2f,1f,2f,1f,2f,1f))
        replaceElement(1,ColLayout("buttons",GetLcsRect.ofFullScreen()).also {
            it.divideBlocksToBiased(listOf(1f,3f,1f,3f,1f))
            it.replaceElement(1,delButton)
            it.replaceElement(3,copyButton)
        })


        val relocateX = ColLayout("relocateX",GetLcsRect.ofFullScreen()).also{relocateX->
            relocateX.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            relocateX.replaceElement(1, PinupImage("rX", BlockText("Reloacate X:", 36, Color.WHITE, "fonts/PTMono-Regular.ttf")))
            relocateX.replaceElement(3, cXIncrementer)
        }

        val relocateY = ColLayout("relocateY",GetLcsRect.ofFullScreen()).also{relocateY->
            relocateY.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            relocateY.replaceElement(1, PinupImage("rY", BlockText("Relocate Y:", 36, Color.WHITE, "fonts/PTMono-Regular.ttf")))
            relocateY.replaceElement(3, cYIncrementer)
        }

        val relocateZ = ColLayout("relocateZ",GetLcsRect.ofFullScreen()).also{relocateY->
            relocateY.divideBlocksToBiased(listOf(0.1f,0.2f,0.1f,0.5f,0.1f))
            relocateY.replaceElement(1, PinupImage("rZ", BlockText("Relocate Z:", 36, Color.WHITE, "fonts/PTMono-Regular.ttf")))
            relocateY.replaceElement(3, ColLayout("zGuys",GetLcsRect.ofFullScreen()).also {
                it.divideBlocksToBiased(listOf(0.05f,0.2f,0.025f,0.2f,0.05f,0.2f,0.025f,0.2f,0.05f))
                it.replaceElement(1,toTop)
                it.replaceElement(3,oneUp)
                it.replaceElement(5,oneDown)
                it.replaceElement(7,toBottom)
            })
        }
        replaceElement(3,relocateX)
        replaceElement(5,relocateY)
        replaceElement(7,relocateZ)
    }
}