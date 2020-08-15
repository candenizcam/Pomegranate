package modules.Layout

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcs
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import modules.visuals.OmniVisual

class PinupImage(id:String, private var image: OmniVisual, var width: LcsVariable= GetLcs.byPixel(0f), var height: LcsVariable = GetLcs.byPixel(0f)): UiElement(id) {
    var cX = GetLcs.byPixel(0f)
    var cY = GetLcs.byPixel(0f)
    override lateinit var block: LcsRect
    init{
        if ((width.asPixel() == 0f) || (height.asPixel() == 0f)){
            width = image.width
            height = image.height
        } else {
            image.resize(width,height)
        }
        block = GetLcsRect.byParameters(image.width,image.height,GetLcs.byLcs(0f),GetLcs.byLcs(0f))
    }

    override fun draw(batch: SpriteBatch) {
        image.draw(batch)
    }

    override fun update() {

    }

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        cX = x
        cY = y
        image.relocate(x,y)
        block = GetLcsRect.byParameters(image.width,image.height,image.cX,image.cY)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        width=w
        height=h
        block = GetLcsRect.byParameters(image.width,image.height,image.cX,image.cY)
        image.resize(w,h)
    }

    fun recolour(c: Color){
        image.recolour(c)
    }

}