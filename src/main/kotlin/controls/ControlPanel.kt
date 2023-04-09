package controls

import matrix.Matrix
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel

const val CONTROLS_WIDTH = 200
const val CONTROLS_HEIGHT = 200

abstract class ControlPanel(width: Int = CONTROLS_WIDTH, height: Int = CONTROLS_HEIGHT): JFrame() {
    internal abstract var points: Matrix

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        preferredSize = Dimension(width, height)
        rootPane.contentPane = JPanel().apply {
            layout = BorderLayout()
        }
    }

    internal abstract fun draw()

    internal fun createSpinner(a: Int, b: Int) = JSpinner().apply {
        model = SpinnerNumberModel(points[a][b].toInt(), -600,600,10)
        editor = JSpinner.NumberEditor(this).apply {
            textField.columns = 3
        }
        addChangeListener {
            points[a][b] = (value as Int).toDouble()
            draw()
        }
    }

    internal fun createCustomSpinner(initValue: Double, listener: ((Double) -> Unit)) = JSpinner().apply {
        model = SpinnerNumberModel(initValue.toInt(), -600,600,10)
        editor = JSpinner.NumberEditor(this).apply {
            textField.columns = 3
        }
        addChangeListener {
            listener((value as Int).toDouble())
            draw()
        }
    }
}