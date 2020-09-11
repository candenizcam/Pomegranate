package modules.uiElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcs
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable
import modules.visuals.ColouredBox
import modules.visuals.OmniVisual
import modules.visuals.VisualSize

/**
 * horizontal false is vertical
 */
class Slider(id: String, resolution: IntRange = 0..100, var horizontal: Boolean = true) : UiElement(id) {
    private var valRange = resolution.toList()
    private var resolution = valRange.size

    private var rail: OmniVisual = ColouredBox()
    private var knob: OmniVisual = ColouredBox()
    private var knobPosition = 1
    var knobPositionChangeFunction = { println("knob function ${valRange[knobPosition]}") }
    private var manipulated = false
    override var block: LcsRect = GetLcsRect.getZero()

    constructor(id: String, resolution: IntRange = 0..100, block: LcsRect, horizontal: Boolean) : this(id, resolution, horizontal) {
        this.block = block
        println("block: ${block.width.asPixel()} ${block.height.asPixel()}")
        rail = ColouredBox(block.width, block.height).also {
            it.resize(block)
            it.visualSize = VisualSize.FIT_ELEMENT
            it.recolour(Color.DARK_GRAY)
        }
        if (horizontal) {
            knob = ColouredBox(block.width / (this.resolution), block.height).also {
                it.visualSize = VisualSize.FIT_WITH_RATIO
                it.recolour(Color.WHITE)
            }
        } else {
            knob = ColouredBox(block.width, block.height / (this.resolution)).also {
                it.visualSize = VisualSize.FIT_WITH_RATIO
                it.recolour(Color.WHITE)
            }
        }

    }

    constructor(id: String, resolution: IntRange = 0..100, block: LcsRect = GetLcsRect.getZero(), rail: OmniVisual, knob: OmniVisual, horizontal: Boolean) : this(id, resolution, block, horizontal) {
        if (block.width.asLcs() == 0f) {
            this.block = GetLcsRect.byParameters(rail.originalWidth, rail.height, rail.cX, rail.cY)
        }
        this.rail = rail
        this.knob = knob

    }

    private fun updateKnobPosition() {
        if (horizontal) {
            knob.relocate(block.wStart + block.width / resolution * (knobPosition + 0.5f), block.cY)
        } else {
            knob.relocate(block.cX, block.hStart + block.height / resolution * (knobPosition + 0.5f))
        }
    }

    override fun touchHandler(mayTouch: Boolean): Boolean {
        if (block.contains(GetLcs.ofX(), GetLcs.ofY())) {
            if (Gdx.input.justTouched()) {
                manipulated = true
            }
            if (!Gdx.input.isTouched) {
                manipulated = false
            }
        } else {
            manipulated = false
        }
        if (manipulated) {

            if (horizontal) {
                val kp = ((GetLcs.ofX() - block.wStart).asLcs() / block.width.asLcs() * resolution).toInt()
                if (knobPosition != kp) {
                    knobPositionChangeFunction()
                    knobPosition = kp
                }

                knob.relocate(GetLcs.ofX().limitAbove(block.wEnd - knob.originalWidth / 2).limitBelow(block.wStart + knob.originalWidth / 2), block.cY)
            } else {
                val kp = ((GetLcs.ofY() - block.hStart).asLcs() / block.height.asLcs() * resolution).toInt()
                if (knobPosition != kp) {
                    knobPositionChangeFunction()
                    knobPosition = kp
                }
                knob.relocate(block.cX, GetLcs.ofY().limitAbove(block.hEnd - knob.originalHeight / 2).limitBelow(block.hStart + knob.originalHeight / 2))
            }
        }



        return false
    }

    override fun update() {
        if (!manipulated) {
            updateKnobPosition()
        }
    }

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width, block.height, x, y)
        rail.relocate(x, y)
        //knob.relocate(x,y)

    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w, h, block.cX, block.cY)
        rail.resize(w, h)
        knob.resize(w, h)
    }

    override fun draw(batch: SpriteBatch) {
        rail.draw(batch)
        knob.draw(batch)
    }

    override fun dispose() {
        rail.dispose()
        knob.dispose()
    }

    override fun getValue(): Int {
        return valRange[knobPosition]
    }
}