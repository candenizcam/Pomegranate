package modules.visuals.fromPath

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import modules.visuals.ScalingType
import modules.lcsModule.LcsVariable as lv

/** Makes animation change frame based on horizontal movement (w)
 *
 */
class StepAtlasAnimation(path: FileHandle, region: String = "", colour: Color = Color.WHITE, fitAll: Boolean=false, val step: lv, scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f) : AtlasTexture(path, region, colour, fitAll, scalingType, scaleFactor) {
    val floatStep = step.asPixel()
    var stepAccumulator = 0

    override fun update() {
        val newPos = (block.cX / step).asPixel().toInt().rem(textures.size)
        changeActiveSprite(newPos)
    }
}