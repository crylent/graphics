import javax.swing.*


class ControlsOne(private val canvas: Canvas): ControlPanel() {
    private var matrix = Matrix(
        row(0, 0),
        row(0, 0),
        row(0, 0)
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

        add(createSpinner(0,0))
        add(createSpinner(0,1))
        add(createSpinner(1,1))
        add(createSpinner(2,0))
        add(createSpinner(2,1))
        add(createSpinner(2,2))

        pack()
        draw()
    }

    private fun createSpinner(a:Int,b:Int) = JSpinner().apply {
        model = SpinnerNumberModel(0,0,600,1)
        editor = JSpinner.NumberEditor(this).apply {
            textField.columns = 3
        }
        addChangeListener {
            matrix[a][b]= value as Int //??
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