import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.*


class ControlsOne(private val canvas: Canvas): ControlPanel() {
    private var matrix = Matrix(
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

    init {
        add(ScaleSlider().apply {
            addChangeListener {
                canvas.scale = scale!!
                draw()
            }
        }, BorderLayout.WEST)
        add(JPanel().apply {
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

    private fun createSpinner(a: Int, b: Int) = JSpinner().apply {
        model = SpinnerNumberModel(matrix[a][b],-600,600,10)
        editor = JSpinner.NumberEditor(this).apply {
            textField.columns = 3
        }
        addChangeListener {
            matrix[a][b]= value as Int //??
            draw()
        }
    }

    private fun draw() {
        canvas.draw {
            it.drawPoly(matrix, true)
        }
    }

   /* private fun setTriangle():Matrix{ // input from console
        println("Enter coordinates")
        val a = row(readln().toInt(),readln().toInt())
        val b = row(readln().toInt(),readln().toInt())
        val c = row(readln().toInt(),readln().toInt())
        return Matrix(a,b,c)
    }

    private fun setQuadrilateral():Matrix{ // from console
        println("Enter coordinates")
        val a = row(readln().toInt(),readln().toInt())
        val b = row(readln().toInt(),readln().toInt())
        val c = row(readln().toInt(),readln().toInt())
        val d = row(readln().toInt(),readln().toInt())
        return Matrix(a,b,c,d)
    }*/

}