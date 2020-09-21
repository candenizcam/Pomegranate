package com.pomegranate


import com.badlogic.gdx.graphics.Color
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.scenes.Scene
import modules.uiElements.*
import modules.uiElements.layouts.ColLayout
import modules.uiElements.layouts.PinboardLayout
import modules.visuals.*


class TestScene: Scene("testScene",0f) {
    override val layout = PinboardLayout("main", GetLcsRect.ofFullScreen()).also { layout ->
        val tv = TestVisuals()
        val but = FastGenerator.genericSetButton("hey","text",36, Color.GOLD, Color.WHITE,"fonts/PTMono-Regular.ttf")
        //val sc = PixmapGenerator.singleColour(c=Color.FOREST)
        val sc = PixmapGenerator.circle(visualSize = VisualSize.FIT_WITH_RATIO)

        //layout.addElement(but,GetLcsRect.byParameters(GetLcs.ofWidth(0.5f),GetLcs.byLcs(0.4f),GetLcs.ofWidth(0.5f),GetLcs.ofHeight(0.6f)))
        layout.addElement(PinupImage("id",sc),GetLcsRect.byParameters(GetLcs.ofWidth(0.5f),GetLcs.byLcs(0.4f),GetLcs.ofWidth(0.5f),GetLcs.ofHeight(0.6f)))
        layout.addElement(PinupImage("id2",sc.copy()),GetLcsRect.byParameters(GetLcs.ofWidth(0.5f),GetLcs.byLcs(0.4f),GetLcs.ofWidth(0.1f),GetLcs.ofHeight(0.1f)))
        sc.recolour(Color.CORAL)
    }
        /*
        val image_1 = ColouredBox().also{
            it.visualSize = VisualSize.FIT_ELEMENT
            it.recolour(Color.CYAN)
        }
        val sampleLayout = ColLayout("sampleRow",rect = GetLcsRect.ofFullScreen()).also{
            it.divideBlocksToBiased(listOf(GetLcs.initialWidth- GetLcs.lcsCoeff, GetLcs.lcsCoeff*2, GetLcs.initialWidth- GetLcs.lcsCoeff))
        }
        layout.addPlot("above",0.2f,0.4f,0.1f,0.4f)
        //layout.replaceElement("above",SetButton("button_3",image_1.copy().also { it.recolour(Color(1f,1f,0f,1f)) },image_1.copy().also { it.recolour(Color(0f,0.5f,0f,1f)) }))
        /*
        layout.replaceElement("above", MultiSetButton("button_3").also {
            it.addButton(SetButton("button_red",image_1.copy().also { it.recolour(Color(1f,0f,0f,1f)) },image_1.copy().also { it.recolour(Color(0.5f,0f,0f,1f)) }).also {
                it.clicked = {
                    println("firered")
                }
            })
            it.addButton(SetButton("button_green",image_1.copy().also { it.recolour(Color(0f,1f,0f,1f)) },image_1.copy().also { it.recolour(Color(0f,0.5f,0f,1f)) }).also {
                it.clicked = {println("leafgreen")}
            })
            it.removeButton("button_green")
        })

         */
        /*
        layout.replaceElement("above", TypingBox("tb","0",charLimit = 30,numOnly = true).also {
            it.textChangeFun = {}
        })

         */



        layout.addElement(sampleLayout, GetLcsRect.ofFullScreen())

    }


    init{
        val image_1 = ColouredBox().also{
            it.visualSize = VisualSize.FIT_ELEMENT
            it.recolour(Color.CYAN)
        }
        val sl2 = PinboardLayout("middleBoard",GetLcsRect.ofFullScreen()).also{
            //it.addPlot("deep",0.1f,0.4f,0.1f,0.4f)
            //it.addPlot("surf",0.3f,0.4f,0.3f,0.4f)
            //it.replaceElement("deep", SetButton("button_1",image_1.copy().also { it.recolour(Color(1f,0f,0f,1f)) },image_1.copy().also { it.recolour(Color(0.5f,0f,0f,1f)) }))
            //it.replaceElement("deep", SetButton("button_1",it.getElement("deep").block))
            //it.replaceElement("surf", SetButton("button_2",image_1.copy().also { it.recolour(Color(0f,0f,1f,1f)) },image_1.copy().also { it.recolour(Color(0f,0f,0.5f,1f)) }))

            //it.toTop("button_1")
        }
        val image_2 = TimedAtlasAnimation("placeholderAtlas/cats.atlas","still",fps=2f,visualSize = VisualSize.FIT_ELEMENT).also{
            //it.visualSize = VisualSize.FIT_ELEMENT
            it.recolour(Color.CYAN)
        }
        replaceElement("sampleRow&sampleRow_0", PinupImage("c1",ColouredBox(colour = Color.BROWN,visualSize = VisualSize.FIT_ELEMENT)))
        //replaceElement("sampleRow&sampleRow_0", Slider("button_5",5..10,GetLcsRect.byParameters(GetLcs.byPixel(GetLcs.initialWidth- GetLcs.lcsCoeff)/2, GetLcs.byLcs(1f),GetLcs.ofZero(),GetLcs.ofZero()),horizontal =false))
        //replaceElement("sampleRow&sampleRow_2", PinupImage("pi1",image_2))
        //replaceElement("sampleRow&sampleRow_1",sl2)

    }

         */



}