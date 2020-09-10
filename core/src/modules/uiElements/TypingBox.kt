package modules.uiElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcs
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import modules.visuals.BlockText
import modules.visuals.ColouredBox
import modules.visuals.OmniVisual
import modules.visuals.VisualSize

class TypingBox(id: String, block: LcsRect = GetLcsRect.ofFullScreen()): UiElement(id) {
    override var block = block
    set(value){
        field = value
        bg.resize(block.width,block.height)
        bg.relocate(block.cX,block.cY)
        bt.resize(block.width,block.height)
        bt.relocate(block.cX,block.cY)
    }
    var selected = false


    private var bg: OmniVisual = ColouredBox(block.width,block.height, Color.WHITE).also {
        it.visualSize = VisualSize.FIT_ELEMENT
    }

    private var bt =  BlockText("text",16,Color.BLACK)


    override fun touchHandler(mayTouch: Boolean): Boolean {
        val contains = block.contains(GetLcs.ofX(),GetLcs.ofY())
        if(Gdx.input.justTouched()){
            selected=false
            bt.changeText("hey")
        }
        if(contains){
            if(Gdx.input.isButtonJustPressed(0)){
                selected=true
                bt.changeText("ho")
            }

        }



        return contains


    }

    override fun update() {
    }

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width,block.height,x,y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w,h,block.cX,block.cY)
    }

    override fun draw(batch: SpriteBatch) {
        bg.draw(batch)
        bt.draw(batch)
    }

    override fun dispose() {
        bg.dispose()
        bt.dispose()
    }

}