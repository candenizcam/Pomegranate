package modules.uiElements.widgets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.uiElements.FastGenerator
import modules.uiElements.PinupImage
import modules.uiElements.UiElement
import modules.visuals.fromFont.BlockText

/** As the name suggest this is an incrementer device, applied horizontally
 * it has an incrementer and a large incerementer which allows us to define what large incremental difference means
 */
class HorizontalIncrementer(id: String, fontPath: String, bgColour: Color, textSize: Int, initialNumber: Int, textColour: Color = Color.WHITE,private var largeIncrementer: Int = 10,var optionsList: List<Any>): UiElement(id) {
    lateinit var theValue: Any
    var theNumber = initialNumber
        private set(value){
            field =value
            theValue = optionsList[value]
        }
    var blockText = BlockText("${optionsList[theNumber]}", textSize, textColour, fontPath)

    var decButton = FastGenerator.genericSetButton("dec", "<", textSize, bgColour = bgColour, textColour = textColour, fontPath = fontPath).also {
        it.clicked = {changeNumber(-1)}
        blockText.changeText("${optionsList[theNumber]}")
    }
    var decDecButton = FastGenerator.genericSetButton("decdec", "<<", textSize, bgColour = bgColour, textColour = textColour, fontPath = fontPath).also {
        it.clicked = {
            changeNumber(-1*largeIncrementer)
        }

    }
    var incButton = FastGenerator.genericSetButton("inc", ">", textSize, bgColour = bgColour, textColour = textColour, fontPath = fontPath).also {
        it.clicked = {changeNumber(1)}
        blockText.changeText("${optionsList[theNumber]}")
    }
    var incIncButton = FastGenerator.genericSetButton("incinc", ">>", textSize, bgColour = bgColour, textColour = textColour, fontPath = fontPath).also {
        it.clicked = {changeNumber(1*largeIncrementer)}
        blockText.changeText("${optionsList[theNumber]}")
    }

    var textImage = PinupImage("text",blockText)
    var numChangeFunc = {}
    init {
        district.splitToPlots("increment",row=1,col=5).also {
            it[0].element = decDecButton
            it[1].element = decButton
            it[2].element = textImage
            it[3].element = incButton
            it[4].element = incIncButton
        }
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

    override fun touchHandler(mayTouch: Boolean): Boolean {
        return if(visible){
            district.touchHandler(mayTouch)
        }else{
            false
        }
    }

    override fun update() {
        district.update()
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        if(visible){
            district.draw(batch,alpha)
        }

    }

    override fun dispose() {
    }
}