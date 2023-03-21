package controls

import canvas.Canvas
import canvas.Poly
import matrix.Axis
import matrix.Matrix
import matrix.row
import matrix.turn
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.JPanel

class ControlsThree(private val canvas: Canvas): ControlPanel(300, 250) {
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
            it.drawPoly3D(resMatrix(), Poly.POLYGON)
        }
    }

}