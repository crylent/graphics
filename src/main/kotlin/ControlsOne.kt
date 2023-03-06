import javax.swing.JCheckBox

class ControlsOne(private val canvas: Canvas): ControlPanel() {
    private var matrix = Matrix(
        row(100, 100),
        row(200, 200),
        row(100, 200)
    )

    private val xReflectMatrix = Matrix(
        row(1, 0),
        row(0, -1)
    )
    private val yReflectMatrix = Matrix(
        row(-1, 0),
        row(0, 1)
    )

    init {
        add(JCheckBox("X-reflection").apply {
            addItemListener {
                matrix = matrix.multiply(xReflectMatrix)
                draw()
            }
        })
        add(JCheckBox("Y-reflection").apply {
            addItemListener {
                matrix = matrix.multiply(yReflectMatrix)
                draw()
            }
        })
        add(ScaleSlider().apply {
            addChangeListener {
                canvas.scale = scale!!
                draw()
            }
        })
        pack()
        draw()
    }

    private fun draw() {
        canvas.draw {
            it.drawPoly(matrix, true)
        }
    }
}