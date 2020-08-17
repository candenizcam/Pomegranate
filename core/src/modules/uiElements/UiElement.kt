package modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import java.lang.Exception

abstract class UiElement(var id: String) {
    init{
        if(id.contains("&")) throw Exception("ID: $id contains illegal character")
    }

    abstract var block: LcsRect
    var stretch = false


    var visible = true


    abstract fun update()
    abstract fun relocate(x: LcsVariable, y: LcsVariable)
    abstract fun resize(w: LcsVariable, h: LcsVariable)

    abstract fun draw(batch: SpriteBatch)

    protected fun adjustElementTo(e: UiElement, r: LcsRect): UiElement {
        if(e.stretch){
            e.resize(r.width,r.height)
        }
        e.relocate(r.cX,r.cY)
        return e
    }

}