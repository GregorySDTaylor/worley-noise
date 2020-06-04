package com.taylorbros.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.taylorbros.WorleyNoise

object DesktopLauncher {

    @JvmStatic
    fun main(arg:Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Worley Noise"
        config.width = 1200
        config.height = 800
        LwjglApplication(WorleyNoise(), config)
    }

}