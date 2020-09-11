package modules.visuals

import modules.lcsModule.GetLcs
import modules.lcsModule.LcsVariable as lv

/** Makes animation change frame based on horizontal movement (w)
 *
 */
class StepAtlasAnimation(path: String, region: String = "", width: lv = GetLcs.byPixel(1f), height: lv = GetLcs.byPixel(1f), val step: lv, visualSize: VisualSize = VisualSize.STATIC) : AtlasTexture(path, region, width, height, visualSize) {
    val floatStep = step.asPixel()
    var stepAccumulator = 0

    override fun update() {
        val new_pos = (cX / step).asPixel().toInt().rem(sprites.size)
        changeActiveSprite(new_pos)
    }
}