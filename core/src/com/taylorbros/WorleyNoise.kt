package com.taylorbros

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils

class WorleyNoise : ApplicationAdapter() {

    var batch: SpriteBatch? = null
    var pixelMap: Pixmap? = null
    var pixelTexture: Texture? = null

    override fun create() {
        batch = SpriteBatch()
        pixelMap = Pixmap(Gdx.graphics.width, Gdx.graphics.height, Pixmap.Format.RGBA8888)
    }

    override fun render() {
        for (x in 0 until Gdx.graphics.width) {
            for (y in 0 until Gdx.graphics.height) {
                pixelMap!!.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1f)
                pixelMap!!.drawPixel(x, y)
            }
        }
        pixelTexture = Texture(pixelMap)
        batch!!.begin()
        batch!!.draw(pixelTexture, 0f, 0f)
        batch!!.end()
    }

    override fun dispose() {
        batch!!.dispose()
        pixelMap!!.dispose()
        pixelTexture!!.dispose()
    }
}