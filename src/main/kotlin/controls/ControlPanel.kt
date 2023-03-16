package controls

import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel

const val CONTROLS_WIDTH = 200
const val CONTROLS_HEIGHT = 200

abstract class ControlPanel : JFrame() {
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        preferredSize = Dimension(CONTROLS_WIDTH, CONTROLS_HEIGHT)
        rootPane.contentPane = JPanel().apply {
            layout = BorderLayout()
        }
    }

    internal abstract fun draw()
}