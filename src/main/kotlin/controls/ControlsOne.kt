package controls

import canvas.Canvas
import canvas.Poly
import matrix.*
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.*

class ControlsOne(private val canvas: Canvas): ControlPanel() {
    override var points = Matrix(
        row(50, 0),
        row(0, 50),
        row(150, 150)
    )

    private val xReflectMatrix = Matrix(
        row(1, 0),
        row(0, -1)
    )
    private val yReflectMatrix = Matrix(
        row(-1, 0),
        row(0, 1)
    )

    private var xReflection = false
    private var yReflection = false

    private fun resMatrix(): Matrix {
        var res = points.clone() as Matrix
        if (xReflection) res = res.multiply(xReflectMatrix)
        if (yReflection) res = res.multiply(yReflectMatrix)
        return res
    }

    init {
        canvas.drawAxis = true
        add(ScaleSlider().apply {
            addChangeListener {
                canvas.scale = scale!!
                draw()
            }
        }, BorderLayout.WEST)
        add(JPanel().apply {
            add(JCheckBox("X-reflection").apply {
                addItemListener {
                    xReflection = !xReflection
                    draw()
                }
            })
            add(JCheckBox("Y-reflection").apply {
                addItemListener {
                    yReflection = !yReflection
                    draw()
                }
            })

            add(JPanel().apply {
                add(JPanel().apply {
                    layout = GridLayout(3, 2)
                    add(createSpinner(0,0))
                    add(createSpinner(0,1))
                    add(createSpinner(1,0))
                    add(createSpinner(1,1))
                    add(createSpinner(2,0))
                    add(createSpinner(2,1))
                })
            })
        })

        pack()
        draw()
    }

    override fun draw() {
        canvas.draw {
            it.drawPoly(points, Poly.POLYGON)
            it.drawPoly(resMatrix(), Poly.FILLED)
        }
    }

}