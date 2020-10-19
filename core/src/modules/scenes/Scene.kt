package modules.scenes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcsRect
import modules.uiElements.UiElement
import modules.uiPlots.District

/** Scenes are used to arrange and use districts
 *
 */
open class Scene(val id: String, var zOrder: Float) {
    var visible = true
    var mainDistrict = District("${id}_district")
        private set

    open fun draw(batch: SpriteBatch){
        if(visible){
            mainDistrict.draw(batch)
        }

    }

    open fun update(){
        if(visible){
            mainDistrict.update()
            mainDistrict.plots.forEach {
                it.element?.touchHandler(true)
            }
        }
    }


    open fun dispose(){
        mainDistrict.dispose()
    }

    open fun mouseMoved(screenX: Int, screenY: Int) {

    }

    open fun keyTyped(character: Char) {

    }

    open fun keyUp(keycode: Int) {

    }

    open fun keyDown(keycode: Int) {

    }

    open fun touchUp(screenX: Int, screenY: Int) {

    }

    open fun touchDragged(screenX: Int, screenY: Int) {

    }

    open fun touchDown(screenX: Int, screenY: Int) {

    }
}