package modules.visuals

import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable as lv

/** Makes animation change frame based on horizontal movement (w)
 *
 */
class StepAtlasAnimation(path: String, region: String = "", fitAll: Boolean=false, block: LcsRect = GetLcsRect.ofCentreSquare(), val step: lv, visualSize: VisualSize = VisualSize.STATIC, scaleFactor: Float = 1f) : AtlasTexture(path, region, fitAll, block, visualSize, scaleFactor) {
    val floatStep = step.asPixel()
    var stepAccumulator = 0

    override fun update() {
        val newPos = (block.cX / step).asPixel().toInt().rem(sprites.size)
        changeActiveSprite(newPos)
    }
}