package com.pomegranate

import modules.LcsModule.GetLcs
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.visuals.TextBox
import modules.visuals.ColouredBox
import modules.visuals.OmniVisual
import modules.visuals.SingleTexture


class Main : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var ls: OmniVisual
    lateinit var box: OmniVisual
    lateinit var tb: TextBox
    override fun create() {
        GetLcs.lcsInitialize()
        batch = SpriteBatch()
        ls = SingleTexture("badlogic.jpg", GetLcs.byLcs(0.5f), GetLcs.byLcs(0.5f)).also{
            it.relocate(GetLcs.ofWidth(0.5f), GetLcs.ofHeight(0.5f))
        }
        box = ColouredBox(GetLcs.byLcs(0.7f), GetLcs.byLcs(0.1f)).also{
            it.relocate(GetLcs.ofWidth(0.5f), GetLcs.ofHeight(0.5f))
            it.recolour(Color(0.2f,0.2f,0f,1f))
        }
        tb = TextBox("Kannst du die engel sehen?\nSie widen fallen nur für dich.\nKannst du die engel sehen?\nHey",0,Color(1f,1f,1f,1f),w= GetLcs.byLcs(0.7f),h= GetLcs.byLcs(0.1f)).also{
            it.relocate(GetLcs.ofWidth(0.5f), GetLcs.ofHeight(0.5f))
        }
        println("aaa${"aaabbb".indexOf("c")}")


    }

    override fun render() {
        Gdx.gl.glClearColor(0.4f, 0.05f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        batch.begin()
        //ls.draw(batch)
        //batch.draw(img, 0f, 0f)
        box.draw(batch)
        tb.draw(batch)

        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}