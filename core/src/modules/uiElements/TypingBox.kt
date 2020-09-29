package modules.uiElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.inputProcessor.InputHandler
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.visuals.fromFont.BlockText
import modules.visuals.OmniVisual
import modules.visuals.fromPixmap.PixmapGenerator
import modules.visuals.VisualSize

class TypingBox(id: String,initialText: String = "", block: LcsRect = GetLcsRect.ofFullScreen(),var charLimit: Int = 0, var numOnly: Boolean = false): UiElement(id) {
    override var block = block
    set(value){
        field = value
        bg.resize(block.width,block.height)
        bg.relocate(block.cX,block.cY)
        bt.resize(block.width,block.height)
        bt.relocate(block.cX,block.cY)
        invalid.resize(block.width,block.height)
        invalid.relocate(block.cX,block.cY)
    }
    var selected = false
    private var textString = ""
    var textChangeFun = {}


    private var bg: OmniVisual = PixmapGenerator.singleColour(GetLcsRect.byParameters(block.width,block.height), Color.WHITE).also {
        it.visualSize = VisualSize.FIT_ELEMENT
    }
    private var invalid = bg.copy().also {
        it.recolour(Color.DARK_GRAY)
    }
    var invalidEntry = 0

    private var bt =  BlockText(initialText, 16, Color.BLACK, align = -1, padding = GetLcs.byPixel(5f), fontPath = "fonts/PTMono-Regular.ttf").also { it.visualSize=VisualSize.FIT_ELEMENT }


    override fun touchHandler(mayTouch: Boolean): Boolean {
        val contains = block.contains(GetLcs.ofX(), GetLcs.ofY())
        if(Gdx.input.justTouched()){ //pressed somewhere else
            selected=false
        }
        if(contains){
            if(Gdx.input.isButtonJustPressed(0)){
                selected=true
            }
        }
        return contains
    }

    override fun update() {
        if(selected){
            val oldString = textString
            InputHandler.getTypeCache(true).forEach {
                if (it=='\b'){
                    if(textString.isNotEmpty()){textString = textString.dropLast(1)}
                    else{invalidEntry=3}
                } else{
                    try {
                        if(textString.length>=charLimit) throw Exception("size")
                        it.toString().toInt()
                        textString += it
                    } catch (e: NumberFormatException){
                        if(!numOnly){
                            textString += it
                        }else{
                            invalidEntry=3
                        }
                    } catch (e: Exception){
                        invalidEntry = 3
                    }
                }
            }
            if(oldString!=textString){
                textChange()
            }
        }
    }

    private fun textChange(){
        bt.changeText(textString)
        textChangeFun()
    }

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width,block.height,x,y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w,h,block.cX,block.cY)
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        bg.draw(batch,alpha)
        bt.draw(batch,alpha)
        if(invalidEntry>0){
            invalid.draw(batch,alpha)
            invalidEntry -= 1
        }
    }

    override fun dispose() {
        bg.dispose()
        bt.dispose()
        invalid.dispose()
    }

}