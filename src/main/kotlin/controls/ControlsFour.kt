package controls

import canvas.Canvas
import canvas.Poly
import matrix.Matrix
import matrix.row
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import kotlin.math.max
import kotlin.math.min

class ControlsFour(private val canvas: Canvas): ControlPanel(400, 200) {
    override var points = Matrix(
        row(-150, -150),
        row(-150, 150),
        row(150, 150),
        row(150, -150)
    )

    /*
    x0 -> x0, x1
    x1 -> x2, x3
    y0 -> y0, y3
    y1 -> y1, y2
     */

    private val rect
        get() = Matrix(
            row(points[0].x, points[0].y),
            row(points[2].x, points[2].y)
        )

    private val lines = listOf(
        Matrix(
            row(-200, -250),
            row(200, 250)
        ),
        Matrix(
            row(-100, 100),
            row(100, -100)
        ),
        Matrix(
            row(-250, -200),
            row(-200, 250)
        ),
    )

    init {
        add(JPanel().apply {
            layout = GridLayout(2, 3)
            val labels = listOf("Bottom Left", "Top Right")
            for (i in 0..1) {
                add(JLabel(labels[i]))
                for (j in 0..1) {
                    add(createCustomSpinner(rect[i][j]) {
                        points[i * 2][j] = it
                        points[((i - j) * 2 + 1).mod(4)][j] = it
                    })
                }
            }
        }, BorderLayout.WEST)
        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            add(JLabel("Lines"))
            add(JPanel().apply {
                layout = GridLayout(6, 2)
                for (n in 0..2) {
                    for (i in 0..1) {
                        for (j in 0..1) {
                            add(createCustomSpinner(lines[n][i][j]) {
                                lines[n][i][j] = it
                            })
                        }
                    }
                }
            })
        })

        pack()
        draw()
    }

    override fun draw() {
        canvas.draw {
            it.drawPoly(points, Poly.POLYGON)
            lines.forEach { matrix ->
                it.drawPoly(matrix, Poly.LINE, Color.GREEN) //
                val truncated = truncate(matrix)
                if (truncated != null) {
                    it.drawPoly(truncated, Poly.LINE)
                }
            }
        }
    }

    private fun isOutside(line: Matrix) =
        (line[0].x <= rect[0].x && line[1].x <= rect[0].x)
                || (line[0].x >= rect[1].x && line[1].x >= rect[1].x)
                || (line[0].y <= rect[0].y && line[1].y <= rect[0].y)
                || (line[0].y >= rect[1].y && line[1].y >= rect[1].y)

    private fun isInside(line: Matrix) =
        !(line[0].x < rect[0].x || line[0].x > rect[1].x)
                && !(line[1].x < rect[0].x || line[1].x > rect[1].x)
                && !(line[0].y < rect[0].y || line[0].y > rect[1].y)
                && !(line[1].y < rect[0].y || line[1].y > rect[1].y)

    private fun truncate(line: Matrix): Matrix? {
        //println("${lines.indexOf(line)}: ${isOutside(line)} ${isInside(line)}")
        if (isOutside(line)) return null
        if (isInside(line)) return line
        val deltaX = line[1].x - line[0].x
        val deltaY = line[1].y - line[0].y
        val t = listOf(
            (rect[0].x - line[0].x) / deltaX,
            (rect[1].x - line[0].x) / deltaX,
            (rect[0].y - line[0].y) / deltaY,
            (rect[1].y - line[0].y) / deltaY
        )
        val t1 = (
                if (t[0] > 0 && t[0] < 1 && t[2] > 0 && t[2] < 1) max(t[0], t[2])
                else if (t[0] <= 0 && t[2] <= 0) 0
                else if (t[0] >= 1 && t[2] >= 1) 1
                else if ((t[0] >= 1 || t[0] <= 0) && t[2] > 0 && t[2] < 1) t[2]
                else if ((t[2] >= 1 || t[2] <= 0) && t[0] > 0 && t[0] < 1) t[0]
                else if (line[0].x in rect[0].x..rect[1].x && line[0].y in rect[0].y..rect[1].y) 0
                else 1
                ).toDouble()
        val t2 = (
                if (t[1] > 0 && t[1] < 1 && t[3] > 0 && t[3] < 1) min(t[1], t[3])
                else if (t[1] <= 0 && t[3] <= 0) 0
                else if (t[1] >= 1 && t[3] >= 1) 1
                else if ((t[1] >= 1 || t[1] <= 0) && t[3] > 0 && t[3] < 1) t[3]
                else if ((t[3] >= 1 || t[3] <= 0) && t[1] > 0 && t[1] < 1) t[1]
                else if (line[0].x in rect[0].x..rect[1].x && line[0].y in rect[0].y..rect[1].y) 0
                else 1
                ).toDouble()
        //println("${lines.indexOf(line)}: $t t1: $t1 t2: $t2")
        val res = Matrix(
            row(line[0].x + (line[1].x - line[0].x) * t1, line[0].y + (line[1].y - line[0].y) * t1),
            row(line[0].x + (line[1].x - line[0].x) * t2, line[0].y + (line[1].y - line[0].y) * t2)
        )
        return if (!isOutside(res)) res
        else null
    }
}