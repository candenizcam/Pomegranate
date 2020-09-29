package modules.visuals.fromPath

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import modules.visuals.VisualSize
import modules.visuals.fromPath.AtlasTexture

class TimedAtlasAnimation(path: FileHandle, region: String = "", colour: Color = Color.WHITE, fitAll: Boolean=false, val fps: Float, visualSize: VisualSize = VisualSize.STATIC, scaleRatio: Float=1f) : AtlasTexture(path, region, colour, fitAll, visualSize,scaleRatio) {
    var timeAccumulator = 0f

    override fun update() {
        timeAccumulator += Gdx.graphics.deltaTime
        if (timeAccumulator > 1f / fps) {
            val change = timeAccumulator.div(1f / fps)

            changeActiveSprite((change + activeFrame).rem(textures.size).toInt())
            timeAccumulator = timeAccumulator.rem(1f / fps)
        }
    }


}