package com.pomegranate


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.basic.geometry.Rectangle
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.scenes.Scene
import modules.uiElements.*
import modules.uiPlots.Plot
import modules.visuals.*
import modules.visuals.fromPath.MultipleTexture
import modules.visuals.fromPath.SingleTexture
import modules.visuals.fromPath.TextureCache
import modules.visuals.fromPixmap.PixmapGenerator
import modules.visuals.fromTiles.TileMapOpener


class TestScene: Scene("testScene",0f) {
    val sc = PixmapGenerator.circle(scalingType = ScalingType.FIT_WITH_RATIO).also {
        it.reBlock(GetLcsRect.byParameters(GetLcs.byLcs(0.5f),GetLcs.byLcs(0.6f),GetLcs.ofWidth(0.5f),GetLcs.ofHeight(0.5f)))
        it.color = Color.PINK
    }

    init{
        val tv = TestVisuals()
        //mainDistrict.addToPlots( Plot("id1",Rectangle(0.25f,0.75f,0.4f,0.8f),10,element = PinupImage("id1",tv.ta)))
        mainDistrict.addToPlots( Plot("id2",Rectangle(0.65f,1.05f,0.4f,0.8f),10,element = PinupImage("id2",tv.ta2)))
        //mainDistrict.addToPlots( Plot("id3",Rectangle(-0.05f,0.45f,0.4f,0.8f), element = PinupImage("id3",tv.ta3)))
        val cat1 = TextureCache.atlasOpener(Gdx.files.internal("placeholderAtlas/cats.atlas"))
        cat1.setScalingType(ScalingType.FIT_WITH_RATIO)
        cat1.frameChanger = cat1.FpsFrameChanger(2f)

        val cat2 = TextureCache.atlasOpener(Gdx.files.internal("placeholderAtlas/cats.atlas"),"still")
        cat2.setScalingType(ScalingType.FIT_WITH_RATIO)
        cat2.frameChanger = cat2.FpsFrameChanger(1f)

        /*
        cat1.changeActiveSubTextureFunc = {it->
            if((0..50).random() == 1) it+1 else it
        }

         */

        val name = "test"
        //val trr = TileMapOpener.openTileRenderer( Gdx.files.internal("maps/$name/tiles.assets"),Gdx.files.internal("tiles/tiles.atlas"),"")
        mainDistrict.addToPlots(Plot("centre",Rectangle(0.25f,0.75f,0.25f,0.75f),z=20))
        val tr = SingleTexture(Gdx.files.internal("badlogic.jpg"))
        //tr.subTexture.visualSizeData = tr.subTexture.visualSizeData.copy(clipRectangle = Rectangle(0.25f,0.75f,0.25f,0.75f))
        tr.setClippingRect(Rectangle(0.25f,0.75f,0.25f,0.75f))
        val tr2 = SingleTexture(Gdx.files.internal("placeholder.png"))
        val m = MultipleTexture()
        m.addToSubTextures(tr2)
        m.addToSubTextures(tr)
        //m.changeActiveSubTextureFunc = {a: Int -> listOf(0,1).random()}
        tr.recolour(Color.PURPLE)
        //m.changeActiveSprite(1)

        mainDistrict.addToPlots(Plot("left",Rectangle(0f,0.25f,0.25f,0.5f),z=20))

        mainDistrict.findPlot("left").element = PinupImage("trr",cat2)

        mainDistrict.findPlot("centre").element = PinupImage("tr",cat1)

    }


    override fun draw(batch: SpriteBatch){
        super.draw(batch)
        //sc.draw(batch)
    }


}