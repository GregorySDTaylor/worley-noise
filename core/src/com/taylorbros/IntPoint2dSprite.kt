package com.taylorbros

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.GridPoint2

class IntPoint2dSprite(positionX : Int, positionY : Int, velocityX : Int, velocityY : Int, baseSprite: Texture, private val maxDistance: Int) {
    val position = GridPoint2(positionX, positionY)
    val velocity = GridPoint2(velocityX, velocityY)
    var texture = baseSprite

    fun batchDraw(batch: SpriteBatch) {
        batch.draw(texture, (position.x - maxDistance).toFloat(), (position.y - maxDistance).toFloat())
    }

    fun dispose() {
        texture.dispose()
    }
}