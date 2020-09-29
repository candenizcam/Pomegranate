package modules.visuals.fromPath

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import modules.visuals.VisualSize
import modules.visuals.fromPath.AtlasTexture
import modules.lcsModule.LcsVariable as lv

/** Makes animation change frame based on horizontal movement (w)
 *
 */
class StepAtlasAnimation(path: FileHandle, region: String = "", colour: Color = Color.WHITE, fitAll: Boolean=false, val step: lv, visualSize: VisualSize = VisualSize.STATIC, scaleFactor: Float = 1f) : AtlasTexture(path, region, colour, fitAll, visualSize, scaleFactor) {
    val floatStep = step.asPixel()
    var stepAccumulator = 0

    override fun update() {
        val newPos = (block.cX / step).asPixel().toInt().rem(textures.size)
        changeActiveSprite(newPos)
    }
}