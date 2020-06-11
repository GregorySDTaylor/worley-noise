package com.taylorbros.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.taylorbros.WorleyNoiseApp

object DesktopLauncher {

    @JvmStatic
    fun main(arg:Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Worley Noise"
        config.width = 1920
        config.height = 1080
        LwjglApplication(WorleyNoiseApp(), config)
    }

}