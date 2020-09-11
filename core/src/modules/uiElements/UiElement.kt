package modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import java.lang.Exception

abstract class UiElement(var id: String) {
    init {
        if (id.contains("&")) throw Exception("ID: $id contains illegal character")
    }

    abstract var block: LcsRect


    var visible = true

    abstract fun touchHandler(mayTouch: Boolean = true): Boolean
    abstract fun update()
    abstract fun relocate(x: LcsVariable, y: LcsVariable)
    abstract fun resize(w: LcsVariable, h: LcsVariable)
    //abstract fun reblock(r: LcsRect) //this function updates the whole block, hopefully resize and relocate can be replaced with this bad boy

    abstract fun draw(batch: SpriteBatch)

    protected fun adjustElementTo(e: UiElement, r: LcsRect): UiElement {
        e.resize(r.width, r.height)
        e.relocate(r.cX, r.cY)
        return e
    }

    abstract fun dispose()
    open fun getValue(): Int {
        return 0
    }

}