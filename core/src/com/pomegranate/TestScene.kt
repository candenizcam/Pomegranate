package com.pomegranate

import com.badlogic.gdx.graphics.Color
import modules.LcsModule.GetLcs
import modules.LcsModule.GetLcsRect
import modules.scenes.Scene
import modules.uiElements.Layouts.ColLayout
import modules.uiElements.Layouts.OmniLayout
import modules.uiElements.Layouts.PinboardLayout
import modules.uiElements.PinupImage
import modules.uiElements.SetButton
import modules.uiElements.Slider
import modules.visuals.ColouredBox
import modules.visuals.TimedAtlasAnimation
import modules.visuals.VisualSize

class TestScene: Scene("testScene",0f) {
    override val layout = PinboardLayout("main",GetLcsRect.ofFullScreen()).also {layout->
        val image_1 = ColouredBox().also{
            it.visualSize = VisualSize.FIT_ELEMENT
            it.recolour(Color.CYAN)
        }
        val sampleLayout = ColLayout("sampleRow",rect = GetLcsRect.ofFullScreen()).also{
            it.divideBlocksToBiased(listOf(GetLcs.initialWidth- GetLcs.lcsCoeff, GetLcs.lcsCoeff*2, GetLcs.initialWidth- GetLcs.lcsCoeff))
        }
        layout.addPlot("above",0.1f,0.4f,0.1f,0.4f)
        layout.replaceElement("above",SetButton("button_3",image_1.copy().also { it.recolour(Color(0f,1f,0f,1f)) },image_1.copy().also { it.recolour(Color(0f,0.5f,0f,1f)) }))
        layout.addElement(sampleLayout, GetLcsRect.ofFullScreen())

    }

    init{
        val image_1 = ColouredBox().also{
            it.visualSize = VisualSize.FIT_ELEMENT
            it.recolour(Color.CYAN)
        }
        val sl2 = PinboardLayout("middleBoard",GetLcsRect.ofFullScreen()).also{
            it.addPlot("deep",0.1f,0.4f,0.1f,0.4f)
            it.addPlot("surf",0.3f,0.4f,0.3f,0.4f)
            it.replaceElement("deep", SetButton("button_1",image_1.copy().also { it.recolour(Color(1f,0f,0f,1f)) },image_1.copy().also { it.recolour(Color(0.5f,0f,0f,1f)) }))
            it.replaceElement("surf", SetButton("button_2",image_1.copy().also { it.recolour(Color(0f,0f,1f,1f)) },image_1.copy().also { it.recolour(Color(0f,0f,0.5f,1f)) }))
            it.toTop("button_1")
        }
        val image_2 = TimedAtlasAnimation("placeholderAtlas/cats.atlas","still",fps=2f).also{
            it.visualSize = VisualSize.FIT_WITH_RATIO
            it.recolour(Color.CYAN)
        }
        //it.replaceElement("sampleRow&sampleRow_0", Slider("button_5",2f,rail=image_1,knob=image_1.copy().also { it.recolour(Color.DARK_GRAY) },horizontal = false))
        replaceElement("sampleRow&sampleRow_2", PinupImage("pi1",image_2))
        replaceElement("sampleRow&sampleRow_1",sl2)

    }


}