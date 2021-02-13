package com.pomegranate.desktop

import kotlin.jvm.JvmStatic
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.pomegranate.Main
import modules.application.PuniversalValues

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        PuniversalValues.punWidth = 1280f
        PuniversalValues.punHeight = 720f
        config.width = 1280
        config.height = 720
        LwjglApplication(Main(), config)
    }
}