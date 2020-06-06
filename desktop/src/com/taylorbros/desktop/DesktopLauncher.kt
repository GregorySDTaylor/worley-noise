package com.taylorbros.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.taylorbros.WorleyNoiseApp

object DesktopLauncher {

    @JvmStatic
    fun main(arg:Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Worley Noise"
        config.width = 400
        config.height = 400
        LwjglApplication(WorleyNoiseApp(), config)
    }

}