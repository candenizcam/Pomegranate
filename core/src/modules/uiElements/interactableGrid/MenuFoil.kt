package modules.uiElements.interactableGrid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcs
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import modules.uiElements.MultiSetButton
import modules.uiElements.PinupImage
import modules.uiElements.SetButton
import modules.uiElements.Layouts.ColLayout
import modules.uiElements.Layouts.PinboardLayout
import modules.uiElements.Layouts.RowLayout
import modules.visuals.*

/** This foil contains the menu
 * upon declaring the layout next few lines are defining the buttons etc. which, makes me feel like this should be smoother somehow
 * there is not much, after that
 */
class MenuFoil() {
    private var brusherButton = MultiSetButton("brusherButton")
    var rowNo = 5
    var colNo = 5
    private var menuLayout = PinboardLayout("menuFoilBg",GetLcsRect.ofFullScreen()).also {
        val buttonBg = ColouredBox(colour = Color(0.4f,0.4f,0.4f,1f),visualSize = VisualSize.FIT_ELEMENT)
        val buttonPressedBg =ColouredBox(colour = Color(0.3f,0.3f,0.3f,1f),visualSize = VisualSize.FIT_ELEMENT)
        it.addElement(PinupImage("bg",ColouredBox(it.block.width,it.block.height, Color(0f,0f,0f,0.9f)).also {it2->
            it2.visualSize = VisualSize.FIT_ELEMENT
        }),it.block)
        it.addElement(RowLayout("rows",it.block).also {rowLayout->
            rowLayout.divideBlocksToBiased(listOf(2f,1f,3f,1f,3f,1f,2f))
            rowLayout.replaceElement(5,ColLayout("brusher",it.block).also {brusher->
                brusher.divideBlocksToBiased(listOf(0.1f,0.3f,0.2f,0.3f,0.1f))
                brusher.replaceElement(1,PinupImage("brusherText", BlockText("Select Brush:",36,Color.WHITE,"fonts/PTMono-Regular.ttf")))
                brusher.replaceElement(3,brusherButton)
            })
            rowLayout.replaceElement(3,ColLayout("rower",it.block).also {rower->
                rower.divideBlocksToBiased(listOf(0.1f,0.3f,0.1f,0.1f,0.05f,0.05f,0.1f,0.05f,0.05f,0.1f))
                rower.replaceElement(1,PinupImage("rowerText", BlockText("Row Number:",36,Color.WHITE,"fonts/PTMono-Regular.ttf")))
                val rowText = BlockText("$rowNo",36,Color.WHITE,"fonts/PTMono-Regular.ttf")
                rower.replaceElement(6,PinupImage("colNo", rowText))
                listOf(Triple(4,"<<",{rowNo -= 10}),Triple(5,"<",{rowNo-=1}),Triple(7,">",{rowNo+=1}),Triple(8,">>",{rowNo+=10})).forEach {p->
                    val bt = BlockText(p.second,36,Color.WHITE,"fonts/PTMono-Regular.ttf")
                    rower.replaceElement(p.first,SetButton(p.second,TwoVisuals(bt,buttonBg.copy(),visualSize = VisualSize.FIT_ELEMENT),TwoVisuals(bt,buttonPressedBg.copy(),visualSize = VisualSize.FIT_ELEMENT)).also {
                        it.clicked = {
                            p.third()
                            rowNo = rowNo.coerceAtLeast(1).coerceAtMost(200)
                            rowText.changeText("$rowNo")
                        }
                    })
                }
            })
            rowLayout.replaceElement(1,ColLayout("coler",it.block).also {rower->
                rower.divideBlocksToBiased(listOf(0.1f,0.3f,0.1f,0.1f,0.05f,0.05f,0.1f,0.05f,0.05f,0.1f))
                rower.replaceElement(1,PinupImage("colerText", BlockText("Col Number:",36,Color.WHITE,"fonts/PTMono-Regular.ttf")))
                val colText = BlockText("$colNo",36,Color.WHITE,"fonts/PTMono-Regular.ttf")
                rower.replaceElement(6,PinupImage("coNo",colText ))
                listOf(Triple(4,"<<",{colNo -= 10}),Triple(5,"<",{colNo-=1}),Triple(7,">",{colNo+=1}),Triple(8,">>",{colNo+=10})).forEach {p->
                    val bt = BlockText(p.second,36,Color.WHITE,"fonts/PTMono-Regular.ttf")
                    rower.replaceElement(p.first,SetButton(p.second,TwoVisuals(bt,buttonBg.copy(),visualSize = VisualSize.FIT_ELEMENT),TwoVisuals(bt,buttonPressedBg.copy(),visualSize = VisualSize.FIT_ELEMENT)).also {
                        it.clicked = {
                            p.third()
                            colNo = colNo.coerceAtLeast(1).coerceAtMost(200)
                            colText.changeText("$colNo")
                        }
                    })
                }
            })
        },it.block)
    }

    fun updateBrusherButton(brushes: List<Pair<String, OmniVisual>>,brushSwitchFun: (String)->Unit){
        println("ubb called")

        brusherButton.addButton(generateEraser {brushSwitchFun(brushes[0].first)})

        brushes.forEachIndexed {index,it->
            val b = SetButton(it.first,it.second.copy(),it.second.copy().also {it2->
                val c = (it2 as ColouredBox).colour
                it2.recolour(Color(c.r*0.5f,c.g*0.5f,c.b*0.5f,1f))
            }).also {it2->
                if(index==brushes.lastIndex){
                    it2.clicked = {brushSwitchFun("eraser")}
                }else{
                    it2.clicked = {brushSwitchFun(brushes[index+1].first)}
                }

            }
            brusherButton.addButton(b)
        }
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

    fun touchHandlerForMenu(mayTouch: Boolean,block:LcsRect,changeFoil: (Foils)->Unit ): Boolean {
        if(Gdx.input.isButtonJustPressed(1)){
            changeFoil(Foils.BLOCKS)
        }
        menuLayout.touchHandler(mayTouch)
        return block.contains(GetLcs.ofX(),GetLcs.ofY())
    }

    private fun generateEraser(f: ()->Unit): SetButton {
        val eraseOV = TwoVisuals(
                BlockText("Eraser",36, Color.WHITE,"fonts/PTMono-Regular.ttf"),
                ColouredBox(GetLcs.byPixel(100f),GetLcs.byPixel(100f),Color.CORAL).also { it.visualSize = VisualSize.FIT_ELEMENT }
        )
        eraseOV.visualSize = VisualSize.FIT_ELEMENT
        return SetButton("eraser",eraseOV.copy(),eraseOV.copy().also { it2->
            val c = ((it2 as TwoVisuals).back as ColouredBox).colour
            it2.back.recolour(Color(c.r*0.5f,c.g*0.5f,c.b*0.5f,1f))
        }).also { it2->
            it2.clicked = f
        }
    }
}