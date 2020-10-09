package com.pomegranate

import com.badlogic.gdx.Gdx
import modules.visuals.textureHandling.SingleTexture
import modules.visuals.subTexture.ScalingType

class TestVisuals() {
    val st = SingleTexture(Gdx.files.internal("badlogic.jpg"), scalingType = ScalingType.FIT_ELEMENT, scaleFactor = 0.5f)

    //val bt = BlockText("text",24,colour = Color.WHITE,fontPath = "fonts/PTMono-Regular.ttf",block = GetLcsRect.ofCentreSquare(),visualSize = VisualSize.FIT_ELEMENT)
}