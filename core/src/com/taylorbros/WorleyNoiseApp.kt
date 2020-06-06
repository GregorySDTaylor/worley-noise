package com.taylorbros

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils

class WorleyNoiseApp : ApplicationAdapter() {

    var batch: SpriteBatch? = null
    var noise: WorleyNoise? = null


    override fun create() {
        batch = SpriteBatch()
        noise = WorleyNoise(Gdx.graphics.width, Gdx.graphics.height, 50, 200F)
    }

    override fun render() {
        batch!!.begin()
        batch!!.draw(noise!!.drawTexture(), 0f, 0f)
        batch!!.end()
    }

    override fun dispose() {
        batch!!.dispose()
        noise!!.dispose()
    }
}