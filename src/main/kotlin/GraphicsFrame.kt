import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel

class GraphicsFrame: JFrame("Graphics") {
    val canvas = Canvas()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        preferredSize = Dimension(CANVAS_WIDTH, CANVAS_HEIGHT)
        rootPane.contentPane = JPanel(BorderLayout()).apply {
            add(canvas)
        }
        pack()
    }
}