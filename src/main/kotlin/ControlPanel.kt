import java.awt.Dimension
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JPanel

const val CONTROLS_WIDTH = 300
const val CONTROLS_HEIGHT = 400

open class ControlPanel : JFrame() {
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        preferredSize = Dimension(CONTROLS_WIDTH, CONTROLS_HEIGHT)
        rootPane.contentPane = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
        }
    }
}