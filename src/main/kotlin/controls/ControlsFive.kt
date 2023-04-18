package controls

import canvas.Canvas
import canvas.Poly
import matrix.Axis
import matrix.Matrix
import matrix.row
import matrix.turn
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JPanel

class ControlsFive(private val canvas: Canvas): ControlPanel(300, 250) {
    override var points = Matrix(
        row(0, 0, 0, 1),
        row(100, 100, -50, 1),
        row(200, 100, -50, 1),
        row(100, 0, 0, 1)
    )

    private fun resMatrix() = points.turn(xTurn, Axis.X).turn(yTurn, Axis.Y)

    private var xTurn = .0
    private var yTurn = .0

    init {
        add(JPanel().apply {
            add(TurnSlider {
                xTurn = Math.toRadians(it)
                draw()
            })
            add(TurnSlider {
                yTurn = Math.toRadians(it)
                draw()
            })
        }, BorderLayout.WEST)
        add(JPanel().apply {
            layout = GridLayout(4, 3)
            for (i in 0..3) {
                for (j in 0..2) {
                    add(createSpinner(i, j))
                }
            }
        })

        pack()
        draw()
    }

    override fun draw() {
        canvas.draw {
            val res = resMatrix()
            it.drawPoly3D(res, Poly.POLYGON)
            val location = computeLinesLocation(points[0], points[1], points[2], points[3])
            val (a, b) = when (location) {
                LinesLocation.FIRST_FRONT -> Pair(res[0], res[1])
                LinesLocation.SECOND_FRONT -> Pair(res[2], res[3])
                else -> Pair(null, null)
            }
            for (i in 0..100) {
                val u = i / 100.0
                for (j in 0..100) {
                    val v = j / 100.0
                    val p = Matrix(res[0]) * (1 - u) * (1 - v) +
                            Matrix(res[1]) * (1 - u) * v +
                            Matrix(res[3]) * (1 - v) * u +
                            Matrix(res[2]) * u * v
                    var color = Color.GRAY
                    if (a != null && b != null) {
                        val pointLocation = (b.x - a.x) * (p[0].y - a.y) - (b.y - a.y) * (p[0].x - a.x)
                        if (pointLocation >= 0) {
                            color = Color.BLUE
                        }
                    }
                    it.drawPoint3D(p, color)
                }
            }
        }
    }

    enum class LinesLocation {
        NOT_INTERSECTING, FIRST_FRONT, SECOND_FRONT
    }

    private fun computeLinesLocation(
        p1: DoubleArray, p2: DoubleArray, p3: DoubleArray, p4: DoubleArray
    ): LinesLocation {
        val deltaX1 = p2.x - p1.x
        val deltaY1 = p2.y - p1.y
        val deltaX2 = p4.x - p3.x
        val deltaY2 = p4.y - p3.y
        val den = deltaX2 * deltaY1 - deltaX1 * deltaY2
        val num1 = -deltaX2 * p1.y + deltaX2 * p3.y + deltaY2 * p1.x - deltaY2 * p3.x
        val num2 = -deltaX1 * p1.y + deltaX1 * p3.y + deltaY1 * p1.x - deltaY1 * p3.x
        val tA = num1 / den
        val tB = num2 / den

        if (tA !in .0..1.0 || tB !in .0..1.0) return LinesLocation.NOT_INTERSECTING

        val z1 = p1.z + p2.z * tA
        val z2 = p3.z + p4.z * tB

        return if (z1 < z2) LinesLocation.FIRST_FRONT
        else LinesLocation.SECOND_FRONT
    }
}
