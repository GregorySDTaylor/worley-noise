package com.taylorbros

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.MathUtils
import kotlin.collections.HashSet
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector3

class WorleyNoise3D(renderWidth: Int, renderHeight: Int, pointCount: Int, maxDistance: Float) : WorleyNoise() {

    private val maxDistance = maxDistance
    private val lowerBoundX = 0 - maxDistance
    private val upperBoundX = renderWidth + maxDistance
    private val lowerBoundY = 0 - maxDistance
    private val upperBoundY = renderWidth + maxDistance
    private val upperBoundZ = maxDistance
    private val lowerBoundZ = -maxDistance
    private val pixelMap = Pixmap(renderWidth, renderHeight, Pixmap.Format.RGBA8888)


    var renderPoints = false

    private val pointSet: MutableSet<Vector3>

    init {
        pointSet = HashSet<Vector3>()
        repeat(pointCount) {
            val randomX = MathUtils.random(lowerBoundX, upperBoundX).toFloat()
            val randomY = MathUtils.random(lowerBoundY, upperBoundY).toFloat()
            val randomZ = MathUtils.random(lowerBoundZ, upperBoundZ).toFloat()
            pointSet.add(Vector3(randomX, randomY, randomZ))
        }
    }

    public override fun drawTexture(): Texture {
        drawNoise()
        if (renderPoints) {
            drawPoints()
        }
        return Texture(pixelMap)
    }

    private fun drawNoise() {
        for (x in 0 .. pixelMap.width) {
            for (y in 0 .. pixelMap.height) {
                val distances = pointSet.map { it.dst(x.toFloat(), y.toFloat(), 0F) }
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

    public override fun dispose() {
        pixelMap.dispose()
    }

    public override fun update() {
        pointSet.forEach {
            if (it.z < upperBoundZ) {
                it.z += 1F
            } else {
                it.z = lowerBoundZ.toFloat()
                it.x = MathUtils.random(lowerBoundX, upperBoundX).toFloat()
                it.y = MathUtils.random(lowerBoundY, upperBoundY).toFloat()
            }
        }
    }

}