package controls

import canvas.Canvas
import matrix.*
import java.awt.GridLayout
import javax.swing.*
import kotlin.math.pow


class ControlsTwo(private val canvas: Canvas): ControlPanel() {
    private var points = Matrix(
        row(50, 0),
        row(0, 50),
        row(150, 150),
        row(200, 150)
    )

    private val bezierMatrix = Matrix(
        row(-1, 3, -3, 1),
        row(3, -6, 3, 0),
        row(-3, 3, 0, 0),
        row(1, 0, 0, 0)
    )

    private fun calcPoint(t: Double) = Matrix(
        row(t.pow(3), t.pow(2), t, 1.0)
    ).multiply(bezierMatrix).multiply(points)

    init {
        add(JPanel().apply {
            layout = GridLayout(3, 2)
            add(createSpinner(0,0))
            add(createSpinner(0,1))
            add(createSpinner(1,0))
            add(createSpinner(1,1))
            add(createSpinner(2,0))
            add(createSpinner(2,1))
        })

        pack()
        draw()
    }

    private fun createSpinner(a: Int, b: Int) = JSpinner().apply {
        model = SpinnerNumberModel(points[a][b].toInt(),-600,600,10)
        editor = JSpinner.NumberEditor(this).apply {
            textField.columns = 3
        }
        addChangeListener {
            points[a][b]= (value as Int).toDouble() //??
            draw()
        }
    }

    override fun draw() {
        canvas.draw {
            it.drawPoly(points)
            it.drawPoly(Matrix(DoubleArray(1000) { n -> n / 1000.0 }.map { t ->
                val p = calcPoint(t)
                doubleArrayOf(p[0][0], p[0][1])
            }))
        }
    }

}