package modules.simpleUi

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.FastGeometry
import com.pungo.modules.basic.geometry.Rectangle
import modules.basic.Colours

class SetButton: Building {
    constructor(upDisplayer: Displayer, downDisplayer: Displayer?=null, upRectangle: Rectangle=FastGeometry.unitSquare(), downRectangle: Rectangle=FastGeometry.unitSquare()){
        buttonVisuals.add(ButtonVisuals(ButtonId.UP,upDisplayer,upRectangle))
        buttonVisuals.add(ButtonVisuals(ButtonId.DOWN,downDisplayer?:upDisplayer.copy().also {
            val c = upDisplayer.imageCollection.getColour()
            it.imageCollection.recolour(Colours.byRGB(c.r*0.5f,c.g*0.5f,c.b*0.5f))
                                                                                             },downRectangle))
    }

    constructor(visual: Displayer,  offRatio: Float, inactiveRatio: Float?=null, hoverRatio: Float?=null,rectangle: Rectangle=FastGeometry.unitSquare()){
        val baseColour = visual.imageCollection.getColour()
        buttonVisuals.add(ButtonVisuals(ButtonId.UP,visual,rectangle))
        buttonVisuals.add(ButtonVisuals(ButtonId.DOWN,visual.copy().also { it.imageCollection.recolour(Colours.byRGB(baseColour.r*offRatio,baseColour.g*offRatio,baseColour.b*offRatio)) },rectangle))
        if(inactiveRatio!=null){
            val inactiveColour = Colours.byRGB(baseColour.r*inactiveRatio,baseColour.g*inactiveRatio,baseColour.b*inactiveRatio)
            buttonVisuals.add(ButtonVisuals(ButtonId.INACTIVE,visual.copy().also { it.imageCollection.recolour(inactiveColour) },rectangle))
        }
        if(hoverRatio!=null){
            val hoverColour = Colours.byRGB(baseColour.r*hoverRatio,baseColour.g*hoverRatio,baseColour.b*hoverRatio)
            buttonVisuals.add(ButtonVisuals(ButtonId.HOVER,visual.copy().also { it.imageCollection.recolour(hoverColour) },rectangle))
        }
    }


    var activeVisual = ButtonId.UP
    var clicked = {
        println("heeyheeey")
    }
    var inactive = false

    fun setHovering(displayer: Displayer, rectangle: Rectangle = FastGeometry.unitSquare()){
        buttonVisuals = buttonVisuals.filter { it.id!=ButtonId.HOVER }.toMutableList()
        buttonVisuals.add(ButtonVisuals(ButtonId.HOVER,displayer,rectangle))
    }

    fun setInactive(displayer: Displayer, rectangle: Rectangle = FastGeometry.unitSquare()){
        buttonVisuals = buttonVisuals.filter { it.id!=ButtonId.INACTIVE }.toMutableList()
        buttonVisuals.add(ButtonVisuals(ButtonId.INACTIVE,displayer,rectangle))
    }

    var buttonVisuals = mutableListOf<ButtonVisuals>()

    override fun update() {
        buttonVisuals.forEach { it.visual.update() }
    }

    override fun draw(batch: SpriteBatch, rectangle: Rectangle, baseWidth: Float, baseHeight: Float,u1: Float, u2: Float, v1: Float, v2: Float) {
        var av = buttonVisuals.filter { it.id==activeVisual }
        if(av.isEmpty()){
            av =  buttonVisuals.filter { it.id == ButtonId.UP }
        }
        av[0].visual.draw(batch,rectangle.getSubRectangle(av[0].rectangle),baseWidth,baseHeight,u1,u2,v1,v2)
    }

    override fun hoverFunction(hovering: Boolean) {
        activeVisual = if(inactive){
            ButtonId.INACTIVE
        }else{
            if(hovering){
                if(Gdx.input.isTouched){
                    if(activeVisual==ButtonId.DOWN||Gdx.input.justTouched()){
                        ButtonId.DOWN
                    } else{
                        activeVisual
                    }
                }else{
                    if(activeVisual==ButtonId.DOWN){
                        clicked()
                    }
                    ButtonId.HOVER
                }
            }else{
                ButtonId.UP
            }
        }
    }

    data class ButtonVisuals(var id: ButtonId, var visual: Displayer, var rectangle: Rectangle)

    enum class ButtonId{
        UP,
        DOWN,
        INACTIVE,
        HOVER
    }
}