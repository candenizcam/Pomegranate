package com.pomegranate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import modules.visuals.*
import modules.visuals.fromPath.SingleTexture
import modules.visuals.fromPath.TimedAtlasAnimation

class TestVisuals() {
    val st = SingleTexture(Gdx.files.internal("badlogic.jpg"), scalingType = ScalingType.FIT_ELEMENT, scaleFactor = 0.5f)
    val ta = TimedAtlasAnimation(Gdx.files.internal("placeholderAtlas/cats.atlas"), "", fps = 2f, scalingType = ScalingType.FIT_ELEMENT, scaleRatio = 0.5f, fitAll = true).also{
        it.recolour(Color.FIREBRICK)
    }
    val ta2 = TimedAtlasAnimation(Gdx.files.internal("placeholderAtlas/cats.atlas"), "still", fps = 2f, scalingType = ScalingType.FIT_ELEMENT, scaleRatio = 0.5f, fitAll = true).also{
        it.recolour(Color.CYAN)
    }
    val ta3 = TimedAtlasAnimation(Gdx.files.internal("placeholderAtlas/cats.atlas"), "move", fps = 2f, scalingType = ScalingType.FIT_ELEMENT, scaleRatio = 0.5f, fitAll = true).also{
        it.recolour(Color.LIME)
    }
    //val bt = BlockText("text",24,colour = Color.WHITE,fontPath = "fonts/PTMono-Regular.ttf",block = GetLcsRect.ofCentreSquare(),visualSize = VisualSize.FIT_ELEMENT)
}