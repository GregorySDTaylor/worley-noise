package com.taylorbros

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class WorleyNoiseApp : ApplicationAdapter() {

    var batch: SpriteBatch? = null
    var noise: WorleyNoise? = null


    override fun create() {
        batch = SpriteBatch()
        noise = WorleyNoise2DSprites(Gdx.graphics.width, Gdx.graphics.height,500,100,3)
    }

    override fun render() {
        noise!!.update()
        batch!!.begin()
        noise!!.batchDraw(batch!!)
        batch!!.end()
    }

    override fun dispose() {
        batch!!.dispose()
        noise!!.dispose()
    }
}