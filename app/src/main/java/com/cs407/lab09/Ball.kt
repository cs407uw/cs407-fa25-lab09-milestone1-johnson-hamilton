package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if (isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        if (dT <= 0f) {
            accX = xAcc
            accY = yAcc
            return
        }

        val a0x = accX
        val a0y = accY
        val v0x = velocityX
        val v0y = velocityY

        val a1x = xAcc
        val a1y = yAcc

        val v1x = v0x + 0.5f * (a1x + a0x) * dT
        val v1y = v0y + 0.5f * (a1y + a0y) * dT

        val dt2 = dT * dT
        val dx = v0x * dT + (1f / 6f) * dt2 * (3f * a0x + a1x)
        val dy = v0y * dT + (1f / 6f) * dt2 * (3f * a0y + a1y)

        posX += dx
        posY += dy

        velocityX = v1x
        velocityY = v1y
        accX = a1x
        accY = a1y
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // Horizontal boundaries: left (0) and right (backgroundWidth - ballSize)
        val maxX = backgroundWidth - ballSize
        if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        } else if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }

        // Vertical boundaries: top (0) and bottom (backgroundHeight - ballSize)
        val maxY = backgroundHeight - ballSize
        if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        } else if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }
}
