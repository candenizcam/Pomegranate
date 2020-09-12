package com.pomegranate

import com.badlogic.gdx.graphics.Color
import modules.lcsModule.GetLcsRect
import modules.visuals.*

class TestVisuals() {
    val cb = ColouredBox(colour = Color.GOLD,visualSize = VisualSize.FIT_ELEMENT,scaleFactor = 0.5f)
    val st = SingleTexture("badlogic.jpg",visualSize = VisualSize.FIT_ELEMENT,scaleFactor = 0.5f)
    val ta = TimedAtlasAnimation("placeholderAtlas/cats.atlas","",fps=2f,visualSize = VisualSize.FIT_ELEMENT,scaleRatio = 0.5f,fitAll = true).also{
        it.recolour(Color.CYAN)
    }
    val bt = BlockText("text",24,colour = Color.WHITE,fontPath = "fonts/PTMono-Regular.ttf",block = GetLcsRect.ofCentreSquare(),visualSize = VisualSize.FIT_ELEMENT)
}