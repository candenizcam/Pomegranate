package modules.uiElements.interactableGrid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.engine.modules.uiElements.interactableGrid.ImageSelectedLayout
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.uiElements.FastGenerator
import modules.uiElements.PinupImage
import modules.uiElements.layouts.ColLayout
import modules.uiElements.layouts.PinboardLayout
import modules.uiElements.layouts.RowLayout
import modules.visuals.ColouredBox
import modules.visuals.TwoVisuals
import modules.visuals.VisualSize

/** This foil contains the menu
 * upon declaring the layout next few lines are defining the buttons etc. which, makes me feel like this should be smoother somehow
 * there is not much, after that
 */
class MenuFoil(val igd: InteractableGridData) {
    val blockMenuLayout =  BlockMenuLayout(5,5,igd)


    private var menuLayout = PinboardLayout("menuFoilBg",GetLcsRect.ofFullScreen()).also {
        it.addElement(PinupImage("bg",ColouredBox(GetLcsRect.byParameters(it.block.width,it.block.height), Color(0f,0f,0f,0.9f)).also { it2->
            it2.visualSize = VisualSize.FIT_ELEMENT
        }),it.block)
        it.addElement(RowLayout("bgRows",it.block).also { it2->
            it2.divideBlocksToBiased(listOf(1f,5f))

        },it.block)

        blockMenuLayout.visible = true
        igd.frontSelectedMenu.visible = false
        (it.getElement("bgRows") as RowLayout).replaceElement(0,ColLayout("tabs",it.block).also {
            it.divideBlocksToBiased(listOf(1f,1f,1f))
            val blocksButton = FastGenerator.genericSetButton("toBlock","Blocks",36,Color(0f,0f,0f,0f),Color.WHITE,"fonts/PTMono-Regular.ttf")
            val frontButton = FastGenerator.genericSetButton("toFront","Front Visuals",36,Color(0f,0f,0f,0f),Color(0.5f,0.5f,0.5f,0.5f),"fonts/PTMono-Regular.ttf")
            val backButton  = FastGenerator.genericSetButton("toBack","Back Visuals",36,Color(0f,0f,0f,0f),Color(0.5f,0.5f,0.5f,0.5f),"fonts/PTMono-Regular.ttf")
            blocksButton.clicked = {
                (blocksButton.visualList[0] as TwoVisuals).front.recolour(Color.WHITE)
                (frontButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (backButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                igd.foil = Foils.BLOCKS
                blockMenuLayout.visible = true
            }
            frontButton.clicked = {
                (blocksButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (frontButton.visualList[0] as TwoVisuals).front.recolour(Color.WHITE)
                (backButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                igd.foil = Foils.FRONT
                blockMenuLayout.visible = false

            }
            backButton.clicked = {
                (blocksButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (frontButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (backButton.visualList[0] as TwoVisuals).front.recolour(Color.WHITE)
                igd.foil = Foils.BACK
                blockMenuLayout.visible = false
            }
            it.replaceElement(0,blocksButton)
            it.replaceElement(1,frontButton)
            it.replaceElement(2,backButton)

        })
        //(it.getElement("bgRows") as RowLayout).replaceElement(1,blockMenuLayout)
        (it.getElement("bgRows") as RowLayout).replaceElement(1,PinboardLayout("others",GetLcsRect.ofCentreSquare()).also {
            it.addElement(blockMenuLayout,it.block)
            it.addElement(igd.frontSelectedMenu,it.block)
        })
    }



    fun relocate(x: LcsVariable, y: LcsVariable){
        menuLayout.relocate(x,y)
    }

    fun resize(w: LcsVariable,h: LcsVariable){
        menuLayout.resize(w,h)
    }

    fun draw(batch: SpriteBatch){
        menuLayout.draw(batch)
    }

    fun touchHandler(mayTouch: Boolean){

    }

    fun update(){
        igd.frontSelectedMenu.visible = if(igd.foil == Foils.FRONT){
            igd.frontVisualSelected
        } else{
            false
        }
        igd.backSelectedMenu.visible = if(igd.foil==Foils.BACK){
            igd.backVisualSelected
        }else{
            false
        }
    }

    fun menuOpened(){

    }

    fun menuClosed(){
        menuLayout.touchHandler(false)
    }

    fun touchHandlerForMenu(mayTouch: Boolean,block:LcsRect): Boolean {
        if(Gdx.input.isButtonJustPressed(1)){
            igd.menuOpen = false
        }
        menuLayout.touchHandler(mayTouch)
        return block.contains(GetLcs.ofX(),GetLcs.ofY())
    }


}