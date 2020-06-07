package com.taylorbros

import com.badlogic.gdx.math.Vector3

class RectSpace(val center: Vector3, val width: Float, val length: Float, val height: Float) {
    fun intersects(space: RectSpace): Boolean {
        return !(
            space.center.x - space.width > center.x + width ||
            space.center.x + space.width < center.x - width ||
            space.center.y - space.length > center.y + length ||
            space.center.y + space.length < center.y - length ||
            space.center.z - space.height > center.z + height ||
            space.center.z + space.height < center.z - height
        )
    }

    fun contains(point: Point): Boolean {
        return (
            point.position.x >= center.x - width && point.position.x <= center.x + width &&
            point.position.y >= center.y - length && point.position.y <= center.y + length &&
            point.position.z >= center.z - height && point.position.z <= center.z + height
        )
    }
}