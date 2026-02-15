package com.example.poolassist

import android.graphics.PointF
import android.util.Log

class TrajectoryCalculator(private val screenWidth: Int, private val screenHeight: Int) {

    // Table boundaries (approximate, should be dynamic based on screen size or detection)
    private val pocketOffset = 50f
    private val tableTop = pocketOffset
    private val tableBottom = screenHeight - pocketOffset
    private val tableLeft = pocketOffset
    private val tableRight = screenWidth - pocketOffset

    /**
     * Calculates the reflection points given a start position and a direction vector.
     * Implements incident_angle = reflection_angle
     */
    fun calculateReflections(start: PointF, direction: PointF, maxBounces: Int = 2): List<PointF> {
        val points = mutableListOf<PointF>()
        points.add(start)

        var currentStart = start
        var currentDir = normalize(direction)

        for (i in 0 until maxBounces) {
            val intersection = findNextWallIntersection(currentStart, currentDir) ?: break
            points.add(intersection)

            // Reflect the direction vector based on which wall was hit
            currentDir = reflect(currentDir, intersection)
            currentStart = intersection
            
            // Move slightly away from the wall to avoid immediate re-collision
            currentStart = PointF(currentStart.x + currentDir.x * 0.1f, currentStart.y + currentDir.y * 0.1f)
        }

        return points
    }

    private fun findNextWallIntersection(start: PointF, dir: PointF): PointF? {
        var minT = Float.MAX_VALUE
        var intersection: PointF? = null

        // Intersect with each wall
        // Wall equation: p = start + t * dir
        
        // Top wall: y = tableTop
        if (dir.y < 0) {
            val t = (tableTop - start.y) / dir.y
            if (t > 0 && t < minT) {
                minT = t
                intersection = PointF(start.x + t * dir.x, tableTop)
            }
        }
        // Bottom wall: y = tableBottom
        if (dir.y > 0) {
            val t = (tableBottom - start.y) / dir.y
            if (t > 0 && t < minT) {
                minT = t
                intersection = PointF(start.x + t * dir.x, tableBottom)
            }
        }
        // Left wall: x = tableLeft
        if (dir.x < 0) {
            val t = (tableLeft - start.x) / dir.x
            if (t > 0 && t < minT) {
                minT = t
                intersection = PointF(tableLeft, start.y + t * dir.y)
            }
        }
        // Right wall: x = tableRight
        if (dir.x > 0) {
            val t = (tableRight - start.x) / dir.x
            if (t > 0 && t < minT) {
                minT = t
                intersection = PointF(tableRight, start.y + t * dir.y)
            }
        }

        return intersection
    }

    private fun reflect(dir: PointF, intersection: PointF): PointF {
        val newDir = PointF(dir.x, dir.y)
        if (intersection.x <= tableLeft + 1 || intersection.x >= tableRight - 1) {
            newDir.x = -newDir.x
        }
        if (intersection.y <= tableTop + 1 || intersection.y >= tableBottom - 1) {
            newDir.y = -newDir.y
        }
        return newDir
    }

    private fun normalize(p: PointF): PointF {
        val length = Math.sqrt((p.x * p.x + p.y * p.y).toDouble()).toFloat()
        return if (length > 0) PointF(p.x / length, p.y / length) else p
    }
}
