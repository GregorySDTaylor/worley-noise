package com.taylorbros

import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class WorleyNoise() {

    abstract fun update()
    abstract fun batchDraw(batch: SpriteBatch)
    abstract fun dispose()

}