package com.pomegranate

import LcsModule.GetLcs
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.engine.model.visuals.TextBox
import visuals.ColouredBox
import visuals.OmniVisual
import visuals.SingleTexture


class Main : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var ls: OmniVisual
    lateinit var box: OmniVisual
    lateinit var tb: TextBox
    override fun create() {
        GetLcs.lcsInitialize()
        batch = SpriteBatch()
        ls = SingleTexture("badlogic.jpg", GetLcs.byLcs(0.5f),GetLcs.byLcs(0.5f)).also{
            it.relocate(GetLcs.ofWidth(0.5f),GetLcs.ofHeight(0.5f))
        }
        box = ColouredBox(GetLcs.byLcs(0.5f),GetLcs.byLcs(0.5f)).also{
            it.relocate(GetLcs.ofWidth(0.5f),GetLcs.ofHeight(0.5f))
            it.recolour(Color(1f,1f,0f,1f))
        }
        tb = TextBox("Kannst du die engel sehen? Sie widen fallen nur für dich. Kannst du die engel sehen? Hey",24,Color(1f,1f,1f,1f),w=GetLcs.byLcs(0.5f),h=GetLcs.byLcs(1f)).also{
            it.relocate(GetLcs.ofWidth(0.5f),GetLcs.ofHeight(0.5f))
        }
        println(GetLcs.byLcs(0.5f).asPixel())

    }

    override fun render() {
        Gdx.gl.glClearColor(0.4f, 0.05f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        batch.begin()
        //ls.draw(batch)
        //batch.draw(img, 0f, 0f)
        tb.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}