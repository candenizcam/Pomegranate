package com.pomegranate

import modules.LcsModule.GetLcs
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.uiElements.Layouts.ColLayout
import modules.uiElements.Layouts.OmniLayout
import modules.uiElements.PinupImage
import modules.uiElements.Layouts.RowLayout
import modules.LcsModule.GetLcsRect
import modules.scenes.Scene
import modules.uiElements.Layouts.PinboardLayout
import modules.uiElements.SetButton
import modules.visuals.BlockText
import modules.visuals.ColouredBox
import modules.visuals.OmniVisual
import modules.visuals.SingleTexture


class Main : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var ls: OmniVisual
    lateinit var box: OmniVisual
    lateinit var tb: BlockText
    lateinit var pi: PinupImage
    lateinit var rl: OmniLayout
    lateinit var sc: Scene

    override fun create() {
        GetLcs.lcsInitialize()
        batch = SpriteBatch()
        val image_1 = ColouredBox().also{
            it.recolour(Color.CYAN)
        }

        val image_2 = ColouredBox().also{
            it.recolour(Color.DARK_GRAY)
        }
        val image_3 = ColouredBox().also{
            it.recolour(Color.FOREST)
        }
        val image_4 = ColouredBox().also{
            it.recolour(Color(1f,0f,0f,1f))
        }

        val image_5 = ColouredBox().also{
            it.recolour(Color(0.5f,0f,0f,1f))
        }
        val image_6 = ColouredBox().also{
            it.recolour(Color(0f,0f,1f,1f))
        }

        val image_7 = ColouredBox().also{
            it.recolour(Color(0f,0f,0.5f,1f))
        }
        val image_8 = ColouredBox().also{
            it.recolour(Color(0f,1f,0f,1f))
        }

        val image_9 = ColouredBox().also{
            it.recolour(Color(0f,0.5f,0f,1f))
        }

        val sampleLayout = ColLayout("sampleRow",rect = GetLcsRect.ofFullScreen()).also{
            it.divideBlocksToBiased(listOf(GetLcs.initialWidth-GetLcs.lcsCoeff,GetLcs.lcsCoeff*2,GetLcs.initialWidth-GetLcs.lcsCoeff))
        }
        val sl2 = PinboardLayout("middleBoard",GetLcsRect.ofFullScreen()).also{
            it.addPlot("deep",0.1f,0.4f,0.1f,0.4f)
            it.addPlot("surf",0.3f,0.4f,0.3f,0.4f)
            it.replaceElement("deep",SetButton("button_1",image_4,image_5))
            it.replaceElement("surf",SetButton("button_2",image_6,image_7))
            it.toTop("button_1")
        }

        sc = Scene("s1",1f)
        sc.getMainLayout().also {
            if(it is PinboardLayout){

                it.addPlot("above",0.1f,0.4f,0.1f,0.4f)
                it.replaceElement("above",SetButton("button_3",image_8,image_9))
                it.addElement(sampleLayout, GetLcsRect.ofFullScreen())
            }
        }
        sc.replaceElement("sampleRow&sampleRow_0",SetButton("button_1",image_1,image_2))
        sc.replaceElement("sampleRow&sampleRow_2",PinupImage("pi1",image_3))
        sc.replaceElement("sampleRow&sampleRow_1",sl2)

    }

    override fun render() {
        Gdx.gl.glClearColor(0.4f, 0.05f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        sc.update()
        batch.begin()
        sc.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}