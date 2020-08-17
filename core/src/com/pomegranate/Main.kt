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

    override fun create() {
        GetLcs.lcsInitialize()
        batch = SpriteBatch()
        ls = SingleTexture("badlogic.jpg", GetLcs.byLcs(0.5f), GetLcs.byLcs(0.5f)).also{
            it.relocate(GetLcs.ofWidth(0.5f), GetLcs.ofHeight(0.5f))
        }
        box = ColouredBox(GetLcs.byLcs(0.7f), GetLcs.byLcs(0.2f)).also{
            it.relocate(GetLcs.ofWidth(0.5f), GetLcs.ofHeight(0.5f))
            it.recolour(Color(0.2f,0.2f,0f,1f))
        }
        val s = "Kannst du die engel sehen?\n" +
                "Sie widen fallen nur für dich.\n" +
                "Kannst du die engel sehen?\n" +
                "Hey"
        tb = BlockText(s,0,Color(1f,0f,1f,1f),w= GetLcs.byLcs(0.7f),h= GetLcs.byLcs(0.2f)).also{
            it.relocate(GetLcs.ofWidth(0.5f), GetLcs.ofHeight(0.5f))
            it.recolour(Color(0f,1f,1f,1f))
        }
        /*
        ColouredBox(GetLcs.byLcs(0.7f), GetLcs.byLcs(0.1f)).also{
            it.relocate(GetLcs.ofWidth(0.5f), GetLcs.ofHeight(0.5f))
            it.recolour(Color(0.2f,0.6f,0f,1f))
        }.also {
            pi = PinupImage(it, GetLcs.byPixel(0f),GetLcs.byPixel(0f)).also{it2->
                it2.relocate(GetLcs.byLcs(0.5f),GetLcs.byLcs(0.055f))
                it2.recolour(Color(0f,0.1f,0f,1f))
            }
        }

         */

        val rr1 = PinboardLayout("testPin",GetLcsRect.ofFullScreen()).also{
            PinupImage("i",ColouredBox(colour =  Color.LIGHT_GRAY), width= GetLcs.byLcs(0.1f),height = GetLcs.byLcs(0.1f)).also{it2->
                //it.replaceElement(1,it2,true)
                it.addElement(it2, GetLcsRect.ofFullScreen(),true)
            }

            PinupImage("i22",ColouredBox(colour =  Color.CYAN), width= GetLcs.byLcs(0.1f),height = GetLcs.byLcs(0.1f)).also{it2->
                //it.replaceElement(1,it2,true)
                it.addElement(it2, GetLcsRect.byBorders(GetLcs.ofWidth(0.3f),GetLcs.ofWidth(0.5f),GetLcs.ofHeight(0.3f),GetLcs.ofHeight(0.5f)),true)
            }

            PinupImage("i322",ColouredBox(colour =  Color.BLUE), width= GetLcs.byLcs(0.1f),height = GetLcs.byLcs(0.1f)).also{it2->
                it.removeElement("i223")
                //it.replaceElement(1,it2,true)
                it.addElement(it2, GetLcsRect.byBorders(GetLcs.ofWidth(0.6f),GetLcs.ofWidth(0.8f),GetLcs.ofHeight(0.6f),GetLcs.ofHeight(0.8f)),true)
            }
            PinupImage("i322",ColouredBox(colour =  Color.BLUE), width= GetLcs.byLcs(0.1f),height = GetLcs.byLcs(0.1f)).also{it2->

                it.addPlot("comeonlan", 0.1f,0.2f,0.2f,0.4f)
                it.replaceElement("comeonlan",it2,true)
            }



        }


/*
        val rr1 =ColLayout("testRows", GetLcsRect.byBorders(GetLcs.ofWidth(0f), GetLcs.ofWidth(1f), GetLcs.ofHeight(0f), GetLcs.ofHeight(1f))).also {
            it.divideBlocksToBiased(listOf(5f,1f,4f))
            PinupImage("i1",ColouredBox(colour= Color.BLUE)).also{it2->
                it.replaceElement(0,it2, true)
            }
            PinupImage("i2",ColouredBox(colour =  Color.RED)).also{it2->
                it.replaceElement(1,it2,true)
            }
            PinupImage("i3",ColouredBox(colour = Color.WHITE)).also{it2->
                it.replaceElement(2,it2, true)
            }
            //it.replaceElement(0,rr1,false)

        }

 */



        rl = RowLayout("testRows", GetLcsRect.byBorders(GetLcs.ofWidth(0f), GetLcs.ofWidth(1f), GetLcs.ofHeight(0f), GetLcs.ofHeight(1f))).also {
            it.divideBlocksToBiased(listOf(5f,1f,4f))
            it.replaceElement(0,rr1,true)

            PinupImage("i22",ColouredBox(colour =  Color.CYAN), width= GetLcs.byLcs(0.1f),height = GetLcs.byLcs(0.1f)).also{it2->
                it.replaceElement(1,it2,true)
            }
            PinupImage("i2g",ColouredBox(GetLcs.ofWidth(1f),GetLcs.ofHeight(1f), Color.DARK_GRAY),GetLcsRect.byParameters(GetLcs.byLcs(0.1f),GetLcs.byLcs(0.1f),GetLcs.byLcs(0.1f),GetLcs.byLcs(0.1f)),true).also{ it2->
                it.replaceElement(1,it2,false)
            }

            SetButton("sb", GetLcsRect.byParameters(GetLcs.byLcs(0.2f),GetLcs.byLcs(0.2f),GetLcs.byLcs(0.1f),GetLcs.byLcs(0.1f))).also{it2->
                it2.block = GetLcsRect.byParameters(GetLcs.byLcs(0.1f),GetLcs.byLcs(0.1f),GetLcs.byLcs(0.1f),GetLcs.byLcs(0.1f))
                ColouredBox(GetLcs.ofWidth(1f),GetLcs.ofHeight(1f), Color.FIREBRICK).also{sbon->
                    ColouredBox(GetLcs.ofWidth(1f),GetLcs.ofHeight(1f), Color.BROWN).also{sboff->
                        it2.setVisuals(sbon,sboff,true)
                    }
                }

                it2.clicked = {println("clickedbetter")}
                it.replaceElement(2,it2,false)
            }
        }


    }

    override fun render() {
        Gdx.gl.glClearColor(0.4f, 0.05f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        rl.update()
        //println("click: ${GetLcs.ofX().asPixel()} ${GetLcs.ofY().asPixel()}")
        batch.begin()
        //box.draw(batch)
        //tb.draw(batch)
        rl.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}