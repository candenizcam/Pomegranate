package modules.visuals

import com.badlogic.gdx.Gdx
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect

class TimedAtlasAnimation(path: String, region: String = "", fitAll: Boolean=false, block: LcsRect= GetLcsRect.ofCentreSquare(), val fps: Float, visualSize: VisualSize = VisualSize.STATIC, scaleRatio: Float=1f) : AtlasTexture(path, region, fitAll, block, visualSize,scaleRatio) {
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