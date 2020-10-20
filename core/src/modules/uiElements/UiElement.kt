package modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.uiPlots.District

abstract class UiElement(var id: String) {
    init {
        if (id.contains("&")) throw Exception("ID: $id contains illegal character")
    }

    protected val district = District(id)
    var penetrable = true

    var visible = true //adjusts visibility

    var mouseHovering = false //true if mouse is hovering on it in a meaningful way

    /** For a non intractable object penetration i
     */
    open fun touchHandler(mayTouch: Boolean = true): Boolean{
        return if(penetrable){
            return hovering()&&mayTouch
        }else{
            false
        }
    }

    open fun update(){
        district.update()
    }

    abstract fun draw(batch: SpriteBatch, alpha: Float=1f)

    abstract fun dispose()

    open fun getValue(): Int {
        return 0
    }

    /** this function returns true if it is hovering
     * it does not switch hovering to true, because mouse being on something does not necessarily mean it is hovering
     */
    fun hovering(): Boolean {
        //println("district: ${district.block.wStart.asPixel()} ${district.block.wEnd.asPixel()} ${district.block.hStart.asPixel()} ${district.block.hEnd.asPixel()}")
        //println("point: ${GetLcs.ofX().asPixel()} ${GetLcs.ofY().asPixel()}")
        return district.block.contains(GetLcs.ofX(),GetLcs.ofY())
    }

    fun relocate(x: LcsVariable, y: LcsVariable){
        district.block = district.block.relocateTo(x,y)
    }

    fun resize(w: LcsVariable, h: LcsVariable){
        district.block = district.block.resizeTo(w,h)
    }

    fun reBlock(b: LcsRect){
        district.block = b
    }

    fun getBlock(): LcsRect {
        return district.block.copy()
    }

}