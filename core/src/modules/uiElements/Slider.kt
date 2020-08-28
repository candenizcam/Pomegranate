package modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcs
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import modules.visuals.ColouredBox
import modules.visuals.OmniVisual

/**
 * horizontal false is vertical
 */
class Slider(id: String,var resolution: Float=100f,var horizontal: Boolean=true): UiElement(id){
    var rail: OmniVisual = ColouredBox()
    var knob: OmniVisual = ColouredBox()
    var knobPosition=0f

    override var block: LcsRect = GetLcsRect.getZero()
    constructor(id: String, resolution: Float=100f, block: LcsRect,horizontal: Boolean) : this(id,resolution,horizontal){
        this.block = block
    }

    constructor(id: String, resolution: Float=100f, block: LcsRect=GetLcsRect.getZero(),rail: OmniVisual, knob: OmniVisual,horizontal: Boolean) : this(id,resolution,block,horizontal){
        if(block.width.asLcs()==0f){
            this.block = GetLcsRect.byParameters(rail.originalWidth,rail.height,rail.cX,rail.cY)
        }
        this.rail=rail
        this.knob=knob

    }

    private fun updateKnobPosition(){
        if(horizontal){
            knob.relocate(block.wStart + block.width/resolution*knobPosition,block.cY)
        } else{
            knob.relocate(block.cX,block.hStart + block.height/resolution*knobPosition)
        }


    }

    override fun touchHandler(mayTouch: Boolean): Boolean {
        return false
    }

    override fun update() {
        updateKnobPosition()

    }

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        rail.relocate(x,y)
        knob.relocate(x,y)

    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        rail.resize(w,h)
        knob.resize(w,h)
    }

    override fun draw(batch: SpriteBatch) {
        rail.draw(batch)
        knob.draw(batch)
    }

    override fun dispose() {
        rail.dispose()
        knob.dispose()
    }
}