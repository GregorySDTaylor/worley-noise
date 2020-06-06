package com.taylorbros

import com.badlogic.gdx.graphics.Texture

abstract class WorleyNoise() {

    abstract fun update()
    abstract fun drawTexture(): Texture
    abstract fun dispose()

}