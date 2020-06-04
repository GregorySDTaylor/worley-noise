package com.taylorbros

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import kotlin.collections.HashSet
import com.badlogic.gdx.graphics.Texture

class WorleyNoise(renderWidth: Int, renderHeight: Int, pointCount: Int) {

    private val padding = (0.5 * if (renderWidth > renderHeight) renderHeight else renderWidth).toInt()
    private val lowerBoundX = 0 - padding
    private val upperBoundX = renderWidth + padding
    private val lowerBoundY = 0 - padding
    private val upperBoundY = renderWidth + padding
    private val pixelMap = Pixmap(renderWidth, renderHeight, Pixmap.Format.RGBA8888)

    var renderPoints = true

    private val pointSet: MutableSet<Vector2>

    init {
        pointSet = HashSet<Vector2>()
        repeat(pointCount) {
            val randomX = MathUtils.random(lowerBoundX, upperBoundX).toFloat()
            val randomY = MathUtils.random(lowerBoundY, upperBoundY).toFloat()
            pointSet.add(Vector2(randomX, randomY))
        }
    }

    public fun drawTexture(): Texture {
        if (renderPoints) {
            drawPoints()
        }
        return Texture(pixelMap)
    }

    private fun drawPoints() {
        pixelMap.setColor(0F, 1F, 0F, 1F)
        pointSet.forEach {
            pixelMap.fillCircle(it.x.toInt(), it.y.toInt(), 10)
        }
    }

    fun dispose() {
        pixelMap.dispose()
    }

}