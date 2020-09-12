package modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable

/** This is a collection of set buttons that, in addition to buttons' function, switches the displayed button with every click
 * I really really hope this works cos I don't want to figure out a different way to do this
 */
class MultiSetButton(id: String, override var block: LcsRect = GetLcsRect.ofZero()): UiElement(id) {
    var buttonsList = mutableListOf<SetButton>()
    var activeButton = 0


    fun addButton(b: SetButton){
        if (buttonsList.none { it.id==b.id }){
            val bottle = b.clicked
            b.clicked = {bottle();rollActiveButton() }
            buttonsList.add(b)
        }
        else{
            throw Exception("ERROR id clash in multi button $id ")
        }
    }

    fun removeButton(id: String){
        buttonsList = buttonsList.filter{ it.id!=id }.toMutableList()
    }

    private fun rollActiveButton(){
        activeButton += 1
        if(activeButton==buttonsList.size) activeButton = 0
    }

    override fun touchHandler(mayTouch: Boolean): Boolean {
        if(buttonsList.none()){
            return false
        }
        return buttonsList[activeButton].touchHandler(mayTouch)
    }

    override fun update() {
        buttonsList.forEach {
            it.update()
        }
    }



    override fun relocate(x: LcsVariable, y: LcsVariable) {
        buttonsList.forEach {
            it.relocate(x,y)
        }
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        buttonsList.forEach {
            it.resize(w,h)
        }
    }

    override fun draw(batch: SpriteBatch) {
        if(buttonsList.isNotEmpty()){buttonsList[activeButton].draw(batch)}

    }

    override fun dispose() {
        buttonsList.forEach { it.dispose() }
    }


}