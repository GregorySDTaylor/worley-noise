package com.taylorbros

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import kotlin.collections.HashSet
import com.badlogic.gdx.graphics.Texture

class WorleyNoise(renderWidth: Int, renderHeight: Int, pointCount: Int, maxDistance: Float) {

    private val padding = (0.5 * if (renderWidth > renderHeight) renderHeight else renderWidth).toInt()
    private val lowerBoundX = 0 - padding
    private val upperBoundX = renderWidth + padding
    private val lowerBoundY = 0 - padding
    private val upperBoundY = renderWidth + padding
    private val pixelMap = Pixmap(renderWidth, renderHeight, Pixmap.Format.RGBA8888)
    private val maxDistance = maxDistance

    var renderPoints = false

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
        drawNoise()
        if (renderPoints) {
            drawPoints()
        }
        return Texture(pixelMap)
    }

    private fun drawNoise() {
        for (x in 0 .. pixelMap.width) {
            for (y in 0 .. pixelMap.height) {
                val distances = pointSet.map { it.dst(x.toFloat(), y.toFloat()) }
                val sortedDistances = distances.sortedBy { it }
                val closestPointValue = distanceToColorValue(sortedDistances[0])
                val color = Color.rgba8888(closestPointValue, closestPointValue, closestPointValue, 1F)
                pixelMap.drawPixel(x, y, color)
            }
        }
    }

    private fun distanceToColorValue(distance: Float): Float {
        return if (distance > maxDistance) 1F else distance / maxDistance
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