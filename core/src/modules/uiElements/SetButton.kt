package modules.uiElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.visuals.OmniVisual
import modules.visuals.fromPixmap.PixmapGenerator
import modules.visuals.VisualSize

/** Set button executes function clicked when clicked, but is similar to pinup visual otherwise
 * it needs to be loaded with basic size parameters, in the form of block
 * and it needs on and off visuals
 */
class SetButton(id: String) : UiElement(id) {
    override var block: LcsRect = GetLcsRect.ofZero()


    private var drawIndex = 0
    var visualList = listOf<OmniVisual>()
        private set
    var clicked = { println("$id clicked") }

    /** This constructor takes a block and it will take visuals later
     */
    constructor(id: String, block: LcsRect) : this(id) {
        this.block = block
        setVisuals(PixmapGenerator.singleColour().also {
            it.visualSize = VisualSize.FIT_ELEMENT
            it.recolour(Color.WHITE)
        }, PixmapGenerator.singleColour().also {
            it.visualSize = VisualSize.STATIC
            it.recolour(Color.DARK_GRAY)
        })
    }

    /** This constructor takes images and it will take the block later
     */
    constructor(id: String, onVisual: OmniVisual, offVisual: OmniVisual) : this(id) {
        setVisuals(onVisual, offVisual)
    }

    /** This constructor takes all the inputs at start
     */
    constructor(id: String, block: LcsRect, onVisual: OmniVisual, offVisual: OmniVisual) : this(id) {
        this.block = block
        setVisuals(onVisual, offVisual)
    }

    /** Sets visuals after block is initialized
     * on and off visuals are self explanatory
     * fitImage stretches the image to the size of the block
     */
    fun setVisuals(onVisual: OmniVisual, offVisual: OmniVisual) {
        visualList = listOf(onVisual, offVisual)
        adjustVisuals()
    }



    /** helper to setVisual that does the actual execution
     *
     */
    private fun adjustVisuals() {
        visualList.forEach {
            it.resize(block.width, block.height)
            it.relocate(block.cX, block.cY)
        }
    }

    /** Handles touching
     * hierarchy is handled by the callers in layout
     */
    override fun touchHandler(mayTouch: Boolean): Boolean {
        if (mayTouch) {
            val containing = block.contains(GetLcs.ofX(), GetLcs.ofY())
            if (drawIndex == 1) {
                drawIndex = if (Gdx.input.isTouched) {
                    if (containing) 1 else 0
                } else {
                    clicked()
                    0
                }
            } else {
                if (Gdx.input.justTouched()) {
                    drawIndex = if (containing) 1 else 0
                }
            }
            return containing
        } else {
            drawIndex = 0
            return false
        }

    }


    /** Runs on every update
     */
    override fun update() {}

    /** Relocates the button to x y
     */
    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width, block.height, x, y)
        visualList.forEach {
            it.relocate(x, y)
        }

    }

    /** Resizes the block by w and h
     */
    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w, h, block.cX, block.cY)
        visualList.forEach {
            it.resize(w, h)
        }
    }

    /** Called on every draw
     */
    override fun draw(batch: SpriteBatch, alpha: Float) {
        if (visible) {
            visualList[drawIndex].draw(batch,alpha)
        }
    }

    override fun dispose() {
        visualList.forEach {
            it.dispose()
        }
    }

    override fun getValue(): Int {
        return drawIndex
    }
}