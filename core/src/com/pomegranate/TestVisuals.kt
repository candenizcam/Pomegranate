package com.pomegranate


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.pungo.modules.basic.geometry.Rectangle
import modules.simpleUi.Campus
import modules.simpleUi.Displayer
import modules.simpleUi.SetButton


class TestVisuals: Campus() {
    init {
        district.addFullPlot("bg").also {
            it.element = Displayer(Color.CORAL)
        }

        district.addFullPlot("button", Rectangle(0f,0.5f,0f,0.5f)).also {
            it.element = SetButton(Displayer(Color.GOLD)).also {
                it.clicked = {
                    district.findPlot("bg").element = Displayer(Color.CHARTREUSE)
                }
            }
        }
    }
}