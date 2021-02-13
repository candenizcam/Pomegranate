package com.pomegranate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.Scene

import modules.basic.Colours

import modules.simpleUi.Displayer
import modules.simpleUi.PunGlyph
import modules.simpleUi.TextBox
import modules.uiPlots.SceneDistrict


class TestScene: Scene("testScene",0f,sceneScaling = SceneDistrict.ResizeReaction.RATED) {
    init {



        mainDistrict.addFullPlot("bg",Rectangle(-0.5f,-0.1f,0f,1f)).also {
            it.element = Displayer(Gdx.files.internal("badlogic.jpg")).also {
                it.imageCollection.yieldImage().also {it2->
                    it2!!.u=0f
                    it2!!.u2=1f
                    it2!!.v=0f
                    it2!!.v2=1f

                }
            }
        }


        var l = 0.15f
        var r = 0.65f
        var lr = l*0.5f + r*0.5f
        var b = 0.05f
        var t = 0.55f
        var bt = b*0.5f + t*0.5f
        mainDistrict.addFullPlot("b2g1",Rectangle(l,lr,b,bt)).also {
            it.element = Displayer(Color.CHARTREUSE)
        }
        mainDistrict.addFullPlot("b2g2",Rectangle(lr,r,b,bt)).also {
            it.element = Displayer(Color.GOLD)
        }
        mainDistrict.addFullPlot("b2g3",Rectangle(l,lr,bt,t)).also {
            it.element = Displayer(Color.CORAL)
        }
        mainDistrict.addFullPlot("b2g4",Rectangle(lr,r,bt,t)).also {
            it.element = Displayer(Color.BLUE)
        }

        mainDistrict.addFullPlot("b22g",Rectangle(l,r,b,t)).also {
            it.element = TextBox("  Pungine is the best engine in the world, Pungine is the best engine in the world, Pungine is the best engine in the world",alignment = PunGlyph.TextAlignment.TOP_LEFT,maxPunto = 16)
        }

        mainDistrict.addFullPlot("b223g",Rectangle(l,r,b,t)).also {
            it.element = TextBox("  Pungine is the best engine in the world, Pungine is the best engine in the world, Pungine is the best engine in the world",alignment = PunGlyph.TextAlignment.LEFT,16).also {
                it.colour = Color.RED

            }
        }

        mainDistrict.addFullPlot("b224g",Rectangle(l,r,b,t)).also {
            it.element = TextBox("  Pungine is the best engine in the world, Pungine is the best engine in the world, Pungine is the best engine in the world",alignment = PunGlyph.TextAlignment.BOTTOM_LEFT,16)
        }
        /*

        mainDistrict.addFullPlot("bg2", Rectangle(0f,0.5f,0f,1f)).also {
            it.element = SetButton("cb",FastGenerator.colouredBox("cb1",Colours.byRGBA256(100,100,100)),FastGenerator.colouredBox("cb2",Colours.byRGBA256(50,50,50)))
        }

         */


        /*
        mainDistrict.addFullPlot("otherbg", Rectangle(0f,0.5f,0f,1f)).also {
            val d = Displayer(ImageCollection(TextureCache.jsonOpener(Gdx.files.internal("pidgeon/pigeon_poop_export.json")))).also {
                it.imageCollection.frameChanger = it.imageCollection.FpsFrameChanger(15f)
            }
            it.element = d
        }

        mainDistrict.addFullPlot("bg",Rectangle(0.5f,1f,0f,1f)).also {
            //it.element = FastGenerator.colouredBox("hey",Colours.byRGBA256(200,200,200))
            //it.element = Displayer(Gdx.files.internal("badlogic.jpg"))
            //it.element = Displayer(Colours.byRGBA256(200,100,100))

            val d = Displayer(ImageCollection(TextureCache.jsonOpener(Gdx.files.internal("pidgeon/pigeon_poop_export.json")))).also {
                it.imageCollection.frameChanger = it.imageCollection.FpsFrameChanger(25f)
            }
            val d2 = d.copy().also {
                it.imageCollection.recolour(Colours.byRGB(0.5f,0.5f,0.5f))
            }
            //it.element = d

            it.element = SetButton(d,d2).also {it2->
                it2.clicked = {
                    if((d.imageCollection.frameChanger as ImageCollection.FpsFrameChanger).active){
                        d.imageCollection.frameChanger.deactivate()
                        d2.imageCollection.frameChanger.deactivate()
                    }else{
                        d.imageCollection.frameChanger.start()
                        d2.imageCollection.frameChanger.start()
                    }

                }
                //it.setHovering(Displayer(Colours.byRGBA256(250,125,125)))
                //it.inactive = true
            }

         */




            /*
            it.element = Displayer(ImageCollection(TextureCache.jsonOpener(Gdx.files.internal("pidgeon/pigeon_poop_export.json")))).also {
                it.imageCollection.frameChanger = it.imageCollection.FpsFrameChanger(25f)
            }

             */


        //mainDistrict.block = mainDistrict.block.getFittingRect(1f,1f)
    }
    //val frame = SceneScaling.RATIO
    //val st = (FastGenerator.colouredBox("cb", Colours.byRGBA256(25,125,15)).image as SingleTexture).subTexture.also {
    //    it.setSize(PuniversalValues.appWidth*0.5f,PuniversalValues.appHeight*0.5f)
    //}
    //val st = SubTexture2(Texture(Gdx.files.internal("badlogic.jpg"))).also {
    //    it.setFlip(false,true)
   // }

    override fun update() {
        /*
        when(sceneScaling){
            SceneScaling.RATIO ->{
                mainDistrict.block =GetLcsRect.byParameters(
                    GetLcs.byPixel(PuniversalValues.ratedWidth),
                    GetLcs.byPixel(PuniversalValues.ratedHeight),
                    GetLcs.byPixel(PuniversalValues.appCentre.x),
                    GetLcs.byPixel(PuniversalValues.appCentre.y)
                )
            }
        }

         */

        /*
        mainDistrict.block = GetLcsRect.byParameters(
            w = GetLcs.byPixel(SceneScaling.RATIO.scaledWidth()),
            h = GetLcs.byPixel(SceneScaling.RATIO.scaledHeight()),
            //w = GetLcs.byPixel(Gdx.graphics.width*0.5f),
            //h = GetLcs.byPixel(Gdx.graphics.height*0.5f),
            cX = GetLcs.byPixel(Gdx.graphics.width/2),
            cY = GetLcs.byPixel(Gdx.graphics.height/2)
        )

         */


        //st.setCenter()
        super.update()

    }

    override fun draw(batch: SpriteBatch) {
        super.draw(batch)
        //tr.reBlock()
        //st.setCenter(PuniversalValues.appWidth/2f,PuniversalValues.appHeight/2f)
        //st.setSize(PuniversalValues.appWidth*0.5f,PuniversalValues.appHeight*0.5f)

        //val appWidth = PuniversalValues.appWidth
        //val appHeight = PuniversalValues.appHeight
        //st.draw(batch,1f, block = PunRect(
        //    PunVariable(PunVariable.As.PuniversalWidth),PunVariable(PunVariable.As.PuniversalHeight),
        //    Quips.cursor
        //),SceneScaling.RATIO
        //)
    }

}

