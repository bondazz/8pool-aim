package com.example.poolassist

import android.content.Context
import android.graphics.*
import android.view.View

class OverlayView(context: Context) : View(context) {

    private val linePaint = Paint().apply {
        color = Color.parseColor("#8000E676") // Semi-transparent neon green
        style = Paint.Style.STROKE
        strokeWidth = 3f
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
        isAntiAlias = true
    }
    
    private val circlePaint = Paint().apply {
        color = Color.parseColor("#8000E676")
        style = Paint.Style.STROKE
        strokeWidth = 2f
        isAntiAlias = true
    }

    private var trajectoryPoints: List<PointF> = emptyList()
    private var ghostBallPos: PointF? = null
    private var ballRadius = 20f

    fun updateTrajectory(points: List<PointF>, ghostBall: PointF?) {
        this.trajectoryPoints = points
        this.ghostBallPos = ghostBall
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (trajectoryPoints.size < 2) return

        // Draw trajectory lines
        val path = Path()
        path.moveTo(trajectoryPoints[0].x, trajectoryPoints[0].y)
        for (i in 1 until trajectoryPoints.size) {
            path.lineTo(trajectoryPoints[i].x, trajectoryPoints[i].y)
        }
        canvas.drawPath(path, linePaint)

        // Draw ghost ball at impact point
        ghostBallPos?.let {
            canvas.drawCircle(it.x, it.y, ballRadius, circlePaint)
        }
    }
}
