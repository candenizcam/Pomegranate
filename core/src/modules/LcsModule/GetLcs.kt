package modules.LcsModule

import com.badlogic.gdx.Gdx

/** GetLcs object is used to create LcsVariables
 * It must be initialized in the create() function of the Main class before use
 */
object GetLcs {
    var initialWidth = 0f // with of the screen on loading
    var initialHeight = 0f // height of the screen on loading
    var lcsCoeff = 0f// a side of a LCS square

    fun lcsInitialize(){
        initialWidth = Gdx.graphics.width.toFloat()
        initialHeight = Gdx.graphics.height.toFloat()
        lcsCoeff = initialWidth.coerceAtMost(initialHeight)
    }

    /** Creates a variable using a LCS value
     */
    fun byLcs(n: Float): LcsVariable {
        return LcsVariable(n, lcsCoeff)
    }

    /** Creates a variable using a pixel value
     */
    fun byPixel(n: Float): LcsVariable {
        return LcsVariable(n / lcsCoeff, lcsCoeff)
    }

    /** Creates a variable as a fraction of the initial screen width
     */
    fun ofWidth(times: Float): LcsVariable {
        return LcsVariable(initialWidth / lcsCoeff * times, lcsCoeff)
    }

    /** Creates a variable as a fraction of the initial screen height
     */
    fun ofHeight(times: Float): LcsVariable {
        return LcsVariable(initialHeight / lcsCoeff * times, lcsCoeff)
    }


}