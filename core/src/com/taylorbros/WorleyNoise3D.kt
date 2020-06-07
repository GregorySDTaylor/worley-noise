package com.taylorbros

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.MathUtils
import kotlin.collections.HashSet
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.Vector2
import kotlin.math.abs
import kotlin.math.sqrt

class WorleyNoise3D(renderWidth: Int, renderHeight: Int, pointCount: Int, maxDistance: Float) : WorleyNoise() {

    private val maxDistance = maxDistance
    private val lowerBoundX = 0 - maxDistance
    private val upperBoundX = renderWidth + maxDistance
    private val lowerBoundY = 0 - maxDistance
    private val upperBoundY = renderWidth + maxDistance
    private val upperBoundZ = maxDistance
    private val lowerBoundZ = -maxDistance
    private val pixelMap = Pixmap(renderWidth, renderHeight, Pixmap.Format.RGBA8888)
    private val boundary = RectSpace(Vector3(0F, 0F,0F), upperBoundX, upperBoundY, upperBoundZ)

    var renderPoints = false

    private var pointTree: OcTree
    private val pointSet: MutableSet<Point>

    init {
        pointTree = OcTree(boundary, 4)
        pointSet = HashSet<Point>()

        repeat(pointCount) {
            val randomX = MathUtils.random(lowerBoundX, upperBoundX).toFloat()
            val randomY = MathUtils.random(lowerBoundY, upperBoundY).toFloat()
            val randomZ = MathUtils.random(lowerBoundZ, upperBoundZ).toFloat()

            val randomColor = Vector3(MathUtils.random(), MathUtils.random(), MathUtils.random())
            val randomVelocity = Vector3(MathUtils.random(-4, 4).toFloat(), MathUtils.random(-4, 4).toFloat(), MathUtils.random(-4, 4).toFloat())
            val point = Point(Vector3(randomX, randomY, randomZ), randomColor, randomVelocity)

            pointTree.insert(point)
            pointSet.add(point)
        }

        pixelMap.setColor(1F, 1F, 1F, 1F)
        pixelMap.fill()
        pixelMap.setColor(0F, 0F, 0F, 1F)
    }

    public override fun drawTexture(): Texture {
        pixelMap.setColor(1F, 1F, 1F, 1F)
        pixelMap.fill()
        pixelMap.setColor(0F, 0F, 0F, 1F)

        drawNoise()
        if (renderPoints) {
            drawPoints()
        }
        return Texture(pixelMap)
    }

    private fun drawNoise() {
        val drawnMap = Array<Array<Boolean>>(pixelMap.width) { Array<Boolean>(pixelMap.height) { false } }
        val validPoints = pointSet.filter { it.position.z <= maxDistance }

        for (point in validPoints) {
            val center = Vector3(point.position.x, point.position.y, 0F)
            val height = abs(point.position.z)
            val radius = sqrt((maxDistance*maxDistance)-(height*height).toDouble())

            val closePoints = getClosePoints(center.x, center.y)
            if (closePoints.isEmpty()) continue

            for (x in (center.x-radius).toInt() .. (center.x+radius).toInt()) {
                for (y in (center.y-radius).toInt() .. (center.y+radius).toInt()) {
                    if (x < 0 || y < 0 || x >= pixelMap.width || y >= pixelMap.height || drawnMap[x][y]) continue

                    val xr = x - center.x
                    val yr = y - center.y
                    if (xr*xr + yr*yr >= radius*radius) continue

                    var closest = closePoints[0]
                    var distance = closePoints[0]!!.position.dst(x.toFloat(), y.toFloat(), 0F)
                    for (closePoint in closePoints) {
                        val compDist = closePoint!!.position.dst(x.toFloat(), y.toFloat(), 0F)
                        if (compDist < distance) {
                            distance = compDist
                            closest = closePoint
                        }
                    }

//                    FOR RAINBOW DOTS
//                    val red = distanceToColorValue(distance, closest.color.x)
//                    val green = distanceToColorValue(distance, closest.color.y)
//                    val blue = distanceToColorValue(distance, closest.color.z)

                    val colorValue = distanceToColorValue(distance, 1F)
                    val color = Color.rgba8888(colorValue, colorValue, colorValue, 1F)
                    drawnMap[x][y] = true
                    pixelMap.drawPixel(x, y, color)
                }
            }
        }

//        for (x in 0 .. pixelMap.width) {
//            for (y in 0 .. pixelMap.height) {
//                val closePoints = getClosePoints(x.toFloat(), y.toFloat())
//                val distance = closePoints[0]!!.position.dst(x.toFloat(), y.toFloat(), 0F)
//                val closestPointValue = distanceToColorValue(distance, 1F)
//                val color = Color.rgba8888(closestPointValue, closestPointValue, closestPointValue, 1F)
//                pixelMap.drawPixel(x, y, color)
//            }
//        }
    }

    private fun getClosePoints(x: Float, y: Float): List<Point> {
        return pointTree.query(RectSpace(Vector3(x, y, 0F), maxDistance*2, maxDistance*2, maxDistance*2))
//        return listOf<Point?>(pointSet.minBy { it.position.dst(x, y, 0F) })
    }

    private fun distanceToColorValue(distance: Float, weight: Float): Float {
        return if ((distance / weight) > maxDistance) 1F else (distance / weight) / maxDistance
    }

    private fun drawPoints() {
        pixelMap.setColor(0F, 1F, 0F, 1F)
        pointSet.forEach {
            pixelMap.fillCircle(it.position.x.toInt(), it.position.y.toInt(), 10)
        }
    }

    private fun checkBounds(position: Vector3): Array<Boolean> {
        val inX = position.x > lowerBoundX && position.x < upperBoundX
        val inY = position.y > lowerBoundY && position.y < upperBoundY
        val inZ = position.z > lowerBoundZ && position.z < upperBoundZ
        return arrayOf(inX, inY, inZ)
    }

    public override fun dispose() {
        pixelMap.dispose()
    }

    public override fun update() {
        pointSet.forEach {
            val boundsState = checkBounds(it.position)

            if (!boundsState[0]) it.velocity.x *= -1
            if (!boundsState[1]) it.velocity.y *= -1
            if (!boundsState[2]) it.velocity.z *= -1

            it.position.add(it.velocity)
        }

        pointTree = OcTree(boundary, 4, pointSet=pointSet)
//        pointTree.update()
    }

}