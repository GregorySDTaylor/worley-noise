package com.taylorbros

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class WorleyNoiseApp : ApplicationAdapter() {

    var batch: SpriteBatch? = null
    var noise: WorleyNoise? = null


    override fun create() {
        batch = SpriteBatch()
        noise = WorleyNoise3D(Gdx.graphics.width, Gdx.graphics.height, 300, 100F)
    }

    override fun render() {
        noise!!.update()
        batch!!.begin()
        batch!!.draw(noise!!.drawTexture(), 0f, 0f)
        batch!!.end()
    }

    override fun dispose() {
        batch!!.dispose()
        noise!!.dispose()
    }
}