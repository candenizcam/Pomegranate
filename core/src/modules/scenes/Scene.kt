package modules.scenes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcsRect
import modules.uiElements.layouts.OmniLayout
import modules.uiElements.layouts.PinboardLayout
import modules.uiElements.UiElement


open class Scene(val id: String, var zOrder: Float, protected open val layout: OmniLayout = PinboardLayout(id,GetLcsRect.ofFullScreen())) {
    var visible = true

    open fun draw(batch: SpriteBatch){
        if(visible){
            layout.draw(batch)
        }

    }

    open fun update(){
        if(visible){
            layout.touchHandler()
            layout.update()
        }
    }

    fun getMainLayout(): OmniLayout {
        return layout
    }

    fun replaceElement(id: String, e: UiElement){
        val l = id.split("&")
        println(l)

        layout.getElement(l.subList(0,l.lastIndex).joinToString("&")).also{
            if (it is OmniLayout){
                it.replaceElement(l.last(),e)
            }
        }

    }

    open fun dispose(){
        layout.dispose()
    }


}