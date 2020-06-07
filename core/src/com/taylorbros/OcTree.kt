package com.taylorbros

import com.badlogic.gdx.math.Vector3

class OcTree(private val boundary: RectSpace, private val capacity: Int, private val parent: OcTree? = null, val pointSet: MutableSet<Point>? = null) {

    private val points = mutableListOf<Point>()
    private var divided = false
    private val center = boundary.center
    var children = arrayOf<OcTree?>(null, null, null, null, null, null, null, null)

    init {
        pointSet?.forEach {
            insert(it)
        }
    }

    private fun subdivide() {
        val smallWidth = boundary.width / 2
        val smallLength = boundary.length / 2
        val smallHeight = boundary.height / 2

        val tnwSpace = RectSpace(Vector3(center.x-smallWidth, center.y+smallLength, center.z+smallHeight), smallWidth, smallLength, smallHeight)
        val tnw = OcTree(tnwSpace, capacity, this)
        val tneSpace = RectSpace(Vector3(center.x+smallWidth, center.y+smallLength, center.z+smallHeight), smallWidth, smallLength, smallHeight)
        val tne = OcTree(tneSpace, capacity, this)
        val tswSpace = RectSpace(Vector3(center.x+smallWidth, center.y-smallLength, center.z+smallHeight), smallWidth, smallLength, smallHeight)
        val tsw = OcTree(tswSpace, capacity, this)
        val tseSpace = RectSpace(Vector3(center.x-smallWidth, center.y-smallLength, center.z+smallHeight), smallWidth, smallLength, smallHeight)
        val tse = OcTree(tseSpace, capacity, this)
        val bnwSpace = RectSpace(Vector3(center.x+smallWidth, center.y+smallLength, center.z-smallHeight), smallWidth, smallLength, smallHeight)
        val bnw = OcTree(bnwSpace, capacity, this)
        val bneSpace = RectSpace(Vector3(center.x-smallWidth, center.y+smallLength, center.z-smallHeight), smallWidth, smallLength, smallHeight)
        val bne = OcTree(bneSpace, capacity, this)
        val bswSpace = RectSpace(Vector3(center.x+smallWidth, center.y-smallLength, center.z-smallHeight), smallWidth, smallLength, smallHeight)
        val bsw = OcTree(bswSpace, capacity, this)
        val bseSpace = RectSpace(Vector3(center.x-smallWidth, center.y-smallLength, center.z-smallHeight), smallWidth, smallLength, smallHeight)
        val bse = OcTree(bseSpace, capacity, this)

        children = arrayOf(tnw, tne, tsw, tse, bnw, bne, bsw, bse)
        divided = true
    }

    fun insert(point: Point, bubble: Boolean = false) {
        if (!this.boundary.contains(point)) {
            if (bubble) parent?.insert(point, true) else return
        }

        if (points.size < capacity) {
            points.add(point)
        } else {
            if (!divided) subdivide()
            for (child in children) child!!.insert(point)
        }
    }

    fun update() {
        val pointIterator = points.iterator()
        while (pointIterator.hasNext()) {
            val point = pointIterator.next()
            if (!this.boundary.contains(point) && parent != null) {
                pointIterator.remove()
                parent.insert(point, true)
            }
        }
        if (divided) for (child in children) child!!.update()
    }

    fun query(space: RectSpace): MutableList<Point> {
        val list = mutableListOf<Point>()
        if (!boundary.intersects(space)) return list
        for (point in points) if (space.contains(point)) list.add(point)
        if (divided) for (child in children) list.addAll(child!!.query(space))
        return list
    }

    fun allPoints(): MutableList<Point> {
        val list = mutableListOf<Point>()
        list.addAll(points)
        if (divided) for (child in children) list.addAll(child!!.allPoints())
        return list
    }
}