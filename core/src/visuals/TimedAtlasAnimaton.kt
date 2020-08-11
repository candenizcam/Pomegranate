package com.pungo.engine.model.visuals

import LcsModule.GetLcs
import LcsModule.LcsVariable as lv
import com.badlogic.gdx.Gdx
import visuals.AtlasTexture

class TimedAtlasAnimaton(path: String, region: String="", w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f), val fps: Float) : AtlasTexture(path,region,w,h) {
    var timeAccumulator = 0f

    override fun update() {
        timeAccumulator += Gdx.graphics.deltaTime
        if(timeAccumulator>1f/fps){
            val change= timeAccumulator.div(1f/fps)

            changeActiveSprite((change+activeFrame).rem(sprites.size).toInt())
            timeAccumulator = timeAccumulator.rem(1f/fps)
        }
    }


}