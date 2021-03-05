package com.pomegranate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.physicsField.PhysicsLayout
import com.pungo.modules.scenes.Scene
import modules.basic.Colours

import modules.simpleUi.*
import modules.simpleUi.text.ColouredTextBox
import modules.simpleUi.text.TextBox
import modules.simpleUi.text.TextEditor

import modules.uiPlots.SceneDistrict


class TestScene: Scene("testScene",0f,sceneScaling = SceneDistrict.ResizeReaction.RATED) {
    init {
        val r1 = Rectangle(-16f,244f,1f,49f)
        val r2 = Rectangle(0.1f,0.54f,0.22f,0.14f)
        val r3 = r1.getSubRectangle(r2).invertSubRectangle(r2)



        mainDistrict.addFullPlot("bg",Rectangle(0f,1f, 0f,1f)).also{
            it.element = Displayer(Colours.byHSV(0.2f,0f,0.2f,1f))
        }


        mainDistrict.addFullPlot("grid",Rectangle(0.25f,0.75f,0.25f,0.75f)).also {

            it.element = Displayer(Gdx.files.internal("grid.png"))
        }

        mainDistrict.addFullPlot("tv",Rectangle(0.8f,1.2f,0.3f,0.7f)).also {
            it.element= TestVisuals()
        }


    }
}

