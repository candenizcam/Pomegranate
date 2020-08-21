package modules.scenes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcsRect
import modules.uiElements.Layouts.OmniLayout
import modules.uiElements.Layouts.PinboardLayout
import modules.uiElements.UiElement


open class Scene(val id: String, var zOrder: Float, protected val layout: OmniLayout = PinboardLayout(id,GetLcsRect.ofFullScreen())) {
    var visible = true

    fun draw(batch: SpriteBatch){
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


}