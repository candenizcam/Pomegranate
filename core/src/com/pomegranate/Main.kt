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
import modules.scenes.LayerManager
import modules.scenes.Scene
import modules.uiElements.Layouts.PinboardLayout
import modules.uiElements.SetButton
import modules.uiElements.Slider
import modules.visuals.*


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
        LayerManager.add(TestScene(),true)
    }

    override fun render() {
        Gdx.gl.glClearColor(0.4f, 0.05f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        LayerManager.update()
        batch.begin()
        LayerManager.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}