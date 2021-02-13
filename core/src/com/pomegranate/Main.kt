package com.pomegranate

import com.badlogic.gdx.graphics.Color
import com.pungo.modules.scenes.LayerManager
import modules.application.PungineAdapter
import modules.basic.Colours

class Main : PungineAdapter(Colours.byHSV(0.5f,0.1f,0.1f,1f)) {
    var c = 0f
    override fun create() {
        super.create()
        LayerManager.scenesToAdd.add(Pair(TestScene(),true))
    }
}

