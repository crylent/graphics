import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        GraphicsFrame().apply {
            isVisible = true
            ControlsOne(canvas).isVisible = true
        }
    }
}