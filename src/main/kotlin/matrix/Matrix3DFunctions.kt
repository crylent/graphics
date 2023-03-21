package matrix

import matrix.Matrix3DFunctions.turn
import kotlin.math.cos
import kotlin.math.sin


object Matrix3DFunctions {

    /******************/
    /** 3D Functions **/
    /******************/

    private val projectionMatrix = Matrix(
        row(1, 0, 0, 0),
        row(0, 1, 0, 0),
        row(0, 0, 0, 0),
        row(0, 0, 0, 1)
    )

    fun orthoProjection(matrix: Matrix) = matrix.multiply(projectionMatrix)

    fun turn(matrix: Matrix, angle: Double, axis: Axis): Matrix {
        val (sin, cos) = sinAndCos(angle)
        val turnMatrix = when (axis) {
            Axis.X -> Matrix(
                row(1, 0, 0, 0),
                row(0, cos, sin, 0),
                row(0, -sin, cos, 0),
                row(0, 0, 0, 1)
            )
            Axis.Y -> Matrix(
                row(cos, 0, -sin, 0),
                row(0, 1, 0, 0),
                row(sin, 0, cos, 0),
                row(0, 0, 0, 1)
            )
            Axis.Z -> Matrix(
                row(cos, sin, 0, 0),
                row(-sin, cos, 0, 0),
                row(0, 0, 1, 0),
                row(0, 0, 0, 1)
            )
        }
        return matrix.multiply(turnMatrix)
    }

    private fun sinAndCos(angle: Double) = Pair(sin(angle), cos(angle))
}

fun Matrix.turn(angle: Double, axis: Axis) = turn(this, angle, axis)

fun Matrix.orthoProjection() = Matrix3DFunctions.orthoProjection(this)