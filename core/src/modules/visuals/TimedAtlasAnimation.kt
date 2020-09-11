package modules.visuals

import modules.lcsModule.GetLcs
import modules.lcsModule.LcsVariable as lv
import com.badlogic.gdx.Gdx

class TimedAtlasAnimation(path: String, region: String = "", w: lv = GetLcs.byLcs(1f), h: lv = GetLcs.byLcs(1f), val fps: Float, visualSize: VisualSize = VisualSize.STATIC) : AtlasTexture(path, region, w, h, visualSize) {
    var timeAccumulator = 0f

    override fun update() {
        timeAccumulator += Gdx.graphics.deltaTime
        if (timeAccumulator > 1f / fps) {
            val change = timeAccumulator.div(1f / fps)

            changeActiveSprite((change + activeFrame).rem(sprites.size).toInt())
            timeAccumulator = timeAccumulator.rem(1f / fps)
        }
    }


}