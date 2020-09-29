package modules.uiElements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.visuals.OmniVisual
import modules.visuals.fromPixmap.PixmapGenerator

class SelectableImage(id: String, selectColor: Color, image: OmniVisual, block: LcsRect = GetLcsRect.ofFullScreen()): UiElement(id) {
    override var block: LcsRect = block
        set(value) {
            field = value
            image.resize(value.width*0.8f,value.height*0.8f)
            image.relocate(value.cX,value.cY)
            background.reBlock(value)
        }
    var background = PixmapGenerator.singleColour(block,selectColor)
    var image = SetButton("sb",image,image.copy().apply{recolour(Color.DARK_GRAY)}).also {
        it.clicked = {
            selected = selected.not()
        }
    }
    var selected = false
        set(value){
            if(value){
                selectFunc()
            }else{
                unselectFunc()
            }
            field = value
        }
    var selectFunc = {}
    var unselectFunc = {}



    override fun touchHandler(mayTouch: Boolean): Boolean {
        return image.touchHandler(mayTouch)
    }

    override fun update() {
        background.update()
        image.update()
    }

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = block.relocateTo(x,y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = block.resizeTo(w,h)
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        if(selected) background.draw(batch,alpha)
        //background.draw(batch,alpha)
        image.draw(batch,alpha)
    }

    override fun dispose() {
        image.dispose()
        background.dispose()
    }
}