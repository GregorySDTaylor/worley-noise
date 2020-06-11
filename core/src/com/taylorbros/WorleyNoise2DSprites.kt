package com.taylorbros

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.MathUtils
import kotlin.collections.HashSet
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.GridPoint2

class WorleyNoise2DSprites(
        renderWidth: Int,
        renderHeight: Int,
        pointCount: Int,
        maxDistance: Int,
        maxSpeed: Int) : WorleyNoise() {

    private val maxDistance = maxDistance
    private val lowerBoundX = 0 - maxDistance
    private val upperBoundX = renderWidth + maxDistance
    private val lowerBoundY = 0 - maxDistance
    private val upperBoundY = renderHeight + maxDistance
    private val baseSprite : Pixmap = Pixmap(maxDistance * 2, maxDistance * 2, Pixmap.Format.RGBA8888)
    init {
        val center = GridPoint2(maxDistance, maxDistance)
        for (x in 0 .. maxDistance * 2) {
            for (y in 0 .. maxDistance * 2) {
                val distance = center.dst(x, y)
                if (distance < maxDistance) {
                    val brightness = distance / maxDistance
                    baseSprite.setColor(brightness, brightness, brightness, 1f)
                    baseSprite.drawPixel(x, y)
                }
            }
        }
    }
    private val baseSpriteTexture = Texture(baseSprite)
    private val background : Pixmap = Pixmap(renderWidth, renderHeight, Pixmap.Format.RGBA8888)
    init {
        background.setColor(1f, 1f, 1f, 1f)
        background.fill()
    }
    private var backgroundTexture = Texture(background)

    private val pointSet: MutableSet<IntPoint2dSprite>

    init {
        pointSet = HashSet<IntPoint2dSprite>()
        repeat(pointCount) {
            val randomPositionX = MathUtils.random(lowerBoundX, upperBoundX)
            val randomPositionY = MathUtils.random(lowerBoundY, upperBoundY)
            val randomVelocityX = MathUtils.random(-maxSpeed, maxSpeed)
            val randomVelocityY = MathUtils.random(-maxSpeed, maxSpeed)
            pointSet.add(IntPoint2dSprite(randomPositionX, randomPositionY, randomVelocityX, randomVelocityY, baseSpriteTexture, maxDistance))
        }
    }

    public override fun batchDraw(batch: SpriteBatch) {
        batch.draw(backgroundTexture, 0f, 0f)
        pointSet.forEach { it.batchDraw(batch) }
    }


    public override fun dispose() {
        baseSprite.dispose()
        background.dispose()
        backgroundTexture.dispose()
        pointSet.forEach { it.dispose() }
    }

    public override fun update() {
        pointSet.forEach {
            if (it.position.x > upperBoundX) {
                it.position.x = lowerBoundX
            }
            if (it.position.x < lowerBoundX) {
                it.position.x = upperBoundX
            }
            if (it.position.y > upperBoundY) {
                it.position.y = lowerBoundY
            }
            if (it.position.y < lowerBoundY) {
                it.position.y = upperBoundY
            }
            it.position.x = it.position.x + it.velocity.x
            it.position.y = it.position.y + it.velocity.y
        }
    }

}