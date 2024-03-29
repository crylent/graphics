package launch

import controls.*
import canvas.GraphicsFrame
import javax.swing.SwingUtilities

fun launch(lab: Int) {
    SwingUtilities.invokeLater {
        GraphicsFrame().apply {
            isVisible = true
            when (lab) {
                1 -> ControlsOne(canvas).isVisible = true
                2 -> ControlsTwo(canvas).isVisible = true
                3 -> ControlsThree(canvas).isVisible = true
                4 -> ControlsFour(canvas).isVisible = true
                5 -> ControlsFive(canvas).isVisible = true
            }
        }
    }
}
