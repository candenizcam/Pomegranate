package com.pomegranate

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.inputProcessor.BasicListener
import modules.lcsModule.GetLcs
import modules.scenes.LayerManager
import modules.uiPlots.Plot


class Main : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    //lateinit var d: District

    override fun create() {

        batch = SpriteBatch()
        GetLcs.lcsInitialize()
        LayerManager.add(TestScene(),true)

        Gdx.input.inputProcessor = BasicListener()

        // val t = ConvexPolygon(mutableListOf(Point(-5f,0f),Point(5f,0f), Point(0f,5f)))
        // val e = GetLcsRect.ofFullScreen().getGeoRect(GetLcsRect.ofCentreSquare())
        val p = Plot("hey")
        p.gridEqual("hp",5,5)
        //d  =District("test")
        //d.splitToPlots("hey",rows = listOf(1f,1f,1f),cols = listOf(1f,2f,1f))
        //plots.addAll(plots[0].gridBiased("hey",))
        //d.findPlot("hey",0,0).element = PinupImage("heyoo", PixmapGenerator.singleColour(c = Color.RED))
        //d.findPlot("hey",1,1).element = PinupImage("heyoo", PixmapGenerator.singleColour(c = Color.GOLD))
        //d.findPlot("hey",2,2).element = PinupImage("heyoo", PixmapGenerator.singleColour(c = Color.BLACK))
        //d.addToPlots(d.superPlot("ho",d.findPlot("hey",0,0),d.findPlot("hey",0,2),0))
        //d.findPlot("ho").element = PinupImage("heyoo", PixmapGenerator.singleColour(c = Color.BLUE))




    }

    override fun render() {
        Gdx.gl.glClearColor(0.4f, 0.05f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        LayerManager.update()
        batch.begin()
        LayerManager.draw(batch)
        //d.draw(batch,1f)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        LayerManager.dispose()
    }
}