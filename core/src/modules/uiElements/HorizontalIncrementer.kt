package modules.uiElements

import com.badlogic.gdx.graphics.Color
import modules.lcsModule.GetLcsRect
import modules.uiElements.layouts.ColLayout
import modules.visuals.BlockText

/** As the name suggest this is an incrementer device, applied horizontally
 * it has an incrementer and a large incerementer which allows us to define what large incremental difference means
 */
class HorizontalIncrementer(id: String, fontPath: String, bgColour: Color, textSize: Int, initialNumber: Int, textColour: Color = Color.WHITE,private var largeIncrementer: Int = 10,var optionsList: List<Any>): ColLayout(id, GetLcsRect.ofFullScreen()) {
    lateinit var theValue: Any
    var theNumber = initialNumber
        private set(value){
            field =value
            theValue = optionsList[value]
        }
    var blockText = BlockText("${optionsList[theNumber]}",textSize, textColour,fontPath)

    var decButton = FastGenerator.genericSetButton("dec","<",textSize,bgColour =bgColour, textColour = textColour,fontPath = fontPath).also {
        it.clicked = {changeNumber(-1)}
        blockText.changeText("${optionsList[theNumber]}")
    }
    var decDecButton = FastGenerator.genericSetButton("decdec","<<",textSize,bgColour =bgColour, textColour = textColour,fontPath = fontPath).also {
        it.clicked = {
            changeNumber(-1*largeIncrementer)
        }

    }
    var incButton = FastGenerator.genericSetButton("inc",">",textSize,bgColour =bgColour, textColour = textColour,fontPath = fontPath).also {
        it.clicked = {changeNumber(1)}
        blockText.changeText("${optionsList[theNumber]}")
    }
    var incIncButton = FastGenerator.genericSetButton("incinc",">>",textSize,bgColour =bgColour, textColour = textColour,fontPath = fontPath).also {
        it.clicked = {changeNumber(1*largeIncrementer)}
        blockText.changeText("${optionsList[theNumber]}")
    }

    var textImage = PinupImage("text",blockText)
    var numChangeFunc = {}
    init {
        divideBlocksToBiased(listOf(1f,1f,2f,1f,1f))
        replaceElement(0,decDecButton)
        replaceElement(1,decButton)
        replaceElement(2,textImage)
        replaceElement(3,incButton)
        replaceElement(4,incIncButton)

    }

    fun setValue(v: Any){
        val a = optionsList.indexOfFirst {
            it==v
        }
        if(a>-1){
            theNumber = a
            blockText.changeText("${optionsList[theNumber]}")
            numChangeFunc()
        }
    }


    private fun changeNumber(n: Int){
        val newNum = (theNumber + n).coerceAtLeast(0).coerceAtMost(optionsList.size-1)
        if(newNum != theNumber){
            theNumber = newNum
            blockText.changeText("${optionsList[theNumber]}")
            numChangeFunc()
        }
    }
}