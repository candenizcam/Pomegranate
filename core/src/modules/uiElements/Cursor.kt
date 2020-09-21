package modules.uiElements

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.visuals.OmniVisual

class Cursor(private var image: OmniVisual, block: LcsRect = GetLcsRect.ofZero()) : UiElement("Cursor") {
    override lateinit var block: LcsRect


    private var lastPressTime = 0L
    var active = true

    init {

        if (block.isZero()) {
            println("image size ${image.block.width.asPixel()} ${image.block.height.asPixel()}")
            this.block = GetLcsRect.byParameters(image.block.width, image.block.height, GetLcs.ofX(), GetLcs.ofY())
        } else {
            this.block = GetLcsRect.byParameters(block.width, block.height, GetLcs.ofX(), GetLcs.ofY())
            image.resize(block.width, block.height)
        }
        image.relocate(block.cX, block.cY)
        image.recolour(Color(1f, 0f, 0f, 0.8f))
    }


    override fun touchHandler(mayTouch: Boolean): Boolean {


        when (Gdx.app.type) {
            Application.ApplicationType.Android, Application.ApplicationType.iOS -> {
                if (Gdx.input.justTouched()) {
                    val nowTime = System.currentTimeMillis()
                    if (nowTime - lastPressTime < 500) {
                        active = active.not()
                    }
                    lastPressTime = System.currentTimeMillis()
                    //if(lastPressTime-Gdx.ti)
                }


            }
            Application.ApplicationType.Desktop -> {
                if (Gdx.input.isButtonJustPressed(0)) {
                    active = active.not()
                }

            }
            else -> {
                throw Exception("I DON'T KNOW WHAT IM WORKING ON\nI MEAN WHAT IS ${Gdx.app.type}")
            }
        }


        return false
    }

    fun recolour(c: Color){
        image.recolour(c)
    }


    override fun update() {

        touchHandler()

    }

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width, block.height, x, y)
        image.relocate(x, y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w, h, block.cX, block.cY)
        image.resize(w, h)
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        if (active) {
            relocate(GetLcs.ofX(), GetLcs.ofY())

            image.draw(batch,alpha)
        }

    }

    override fun dispose() {
        image.dispose()
    }

}