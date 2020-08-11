package visuals

import LcsModule.GetLcs
import com.badlogic.gdx.Gdx
import LcsModule.LcsVariable as lv

/** Makes animation change frame based on horizontal movement (w)
 *
 */
class StepAtlasAnimation(path: String, region: String="", width: lv = GetLcs.byPixel(1f), height: lv = GetLcs.byPixel(1f), val step: lv) : AtlasTexture(path,region,width,height) {
    val floatStep = step.asPixel()

    var stepAccumulator = 0

    override fun update() {
        val new_pos = (cX/step).asPixel().toInt().rem(sprites.size)
        changeActiveSprite(new_pos)
        /*
        val almostRem = (new_pos-stepAccumulator).rem(sprites.size).toInt()


        changeActiveSprite((almostRem + sprites.size).rem(sprites.size))
        stepAccumulator = (cX/floatStep).toInt()

         */
    }



}