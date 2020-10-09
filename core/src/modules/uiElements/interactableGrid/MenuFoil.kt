package modules.uiElements.interactableGrid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.uiElements.FastGenerator
import modules.uiElements.PinupImage
import modules.uiElements.layouts.ColLayout
import modules.uiElements.layouts.PinboardLayout
import modules.uiElements.layouts.RowLayout
import modules.visuals.PixmapGenerator
import modules.visuals.TwoVisuals
import modules.visuals.textureHandling.SingleTexture

/** This foil contains the menu
 * upon declaring the layout next few lines are defining the buttons etc. which, makes me feel like this should be smoother somehow
 * there is not much, after that
 */
class MenuFoil(val igd: InteractableGridData) {
    val blockMenuLayout =  BlockMenuLayout("blockMenuLayout")
    val tilesMenuLayout = BlockMenuLayout("tilesMenuLayout")
    val generalMenuLayout = GeneralMenuLayout(6,6,igd)
    val frontMenuLayout = ImageSelectedLayout("frontSelected")
    val backMenuLayout = ImageSelectedLayout("backSelected")


    private var menuLayout = PinboardLayout("menuFoilBg",GetLcsRect.ofFullScreen()).also {
        it.addElement(PinupImage("bg", SingleTexture(PixmapGenerator.singleColour(GetLcsRect.byParameters(it.block.width,it.block.height), Color(0f,0f,0f,0.9f)))),it.block)
        it.addElement(RowLayout("bgRows",it.block).also { it2->
            it2.divideBlocksToBiased(listOf(1f,5f))

        },it.block)

        blockMenuLayout.visible = false
        generalMenuLayout.visible=true
        frontMenuLayout.visible = false
        backMenuLayout.visible=false
        tilesMenuLayout.visible = false

        (it.getElement("bgRows") as RowLayout).replaceElement(0,ColLayout("tabs",it.block).also {
            it.divideBlocksToBiased(listOf(1f,1f,1f,1f,1f))
            val generalButton = FastGenerator.genericSetButton("toGeneral","General",36,Color(0f,0f,0f,0f),Color.WHITE,"fonts/PTMono-Regular.ttf")
            val blocksButton = FastGenerator.genericSetButton("toBlock","Blocks",36,Color(0f,0f,0f,0f),Color(0.5f,0.5f,0.5f,0.5f),"fonts/PTMono-Regular.ttf")
            val frontButton = FastGenerator.genericSetButton("toFront","Front Visuals",36,Color(0f,0f,0f,0f),Color(0.5f,0.5f,0.5f,0.5f),"fonts/PTMono-Regular.ttf")
            val backButton  = FastGenerator.genericSetButton("toBack","Back Visuals",36,Color(0f,0f,0f,0f),Color(0.5f,0.5f,0.5f,0.5f),"fonts/PTMono-Regular.ttf")
            val tilesButton  = FastGenerator.genericSetButton("toTiles","Tiles",36,Color(0f,0f,0f,0f),Color(0.5f,0.5f,0.5f,0.5f),"fonts/PTMono-Regular.ttf")

            blocksButton.clicked = {
                (blocksButton.visualList[0] as TwoVisuals).front.recolour(Color.WHITE)
                (frontButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (backButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (tilesButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (generalButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                changeActiveFoil(Foils.BLOCKS)
            }
            frontButton.clicked = {
                (blocksButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (frontButton.visualList[0] as TwoVisuals).front.recolour(Color.WHITE)
                (backButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (tilesButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (generalButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                changeActiveFoil(Foils.FRONT)
            }
            backButton.clicked = {
                (blocksButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (frontButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (backButton.visualList[0] as TwoVisuals).front.recolour(Color.WHITE)
                (tilesButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (generalButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                changeActiveFoil(Foils.BACK)
            }
            tilesButton.clicked = {
                (blocksButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (frontButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (backButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (tilesButton.visualList[0] as TwoVisuals).front.recolour(Color.WHITE)
                (generalButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                changeActiveFoil(Foils.TILES)
            }
            generalButton.clicked = {
                (blocksButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (frontButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (backButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (tilesButton.visualList[0] as TwoVisuals).front.recolour(Color(0.5f,0.5f,0.5f,0.5f))
                (generalButton.visualList[0] as TwoVisuals).front.recolour(Color.WHITE)
                changeActiveFoil(Foils.GENERAL)
            }
            it.replaceElement(0,generalButton)
            it.replaceElement(1,blocksButton)
            it.replaceElement(2,tilesButton)
            it.replaceElement(3,frontButton)
            it.replaceElement(4,backButton)

        })


        //(it.getElement("bgRows") as RowLayout).replaceElement(1,blockMenuLayout)

        (it.getElement("bgRows") as RowLayout).replaceElement(1,PinboardLayout("others",GetLcsRect.ofCentreSquare()).also {
            it.addElement(blockMenuLayout,it.block)
            it.addElement(tilesMenuLayout,it.block)
            it.addElement(frontMenuLayout,it.block)
            it.addElement(backMenuLayout,it.block)
            it.addElement(generalMenuLayout,it.block)
        })


    }

    fun changeActiveFoil(f: Foils){
        igd.foil = f
        generalMenuLayout.visible = f==Foils.GENERAL
        blockMenuLayout.visible = f==Foils.BLOCKS
        tilesMenuLayout.visible = f==Foils.TILES
        frontMenuLayout.visible = f==Foils.FRONT
        backMenuLayout.visible = f==Foils.BACK
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
        /*
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

         */
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

    fun dispose(){
        blockMenuLayout.dispose()
        frontMenuLayout.dispose()
        backMenuLayout.dispose()
        generalMenuLayout.dispose()
        tilesMenuLayout.dispose()
    }


}