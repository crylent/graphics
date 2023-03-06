import java.util.*
import javax.swing.DefaultBoundedRangeModel
import javax.swing.JLabel
import javax.swing.JSlider

class ScaleSlider: JSlider() {
    private val scales = mapOf(
        1 to 0.25,
        2 to 0.5,
        3 to 0.75,
        4 to 1.0,
        5 to 2.0,
        6 to 3.0,
        7 to 4.0
    )

    init {
        model = DefaultBoundedRangeModel().apply {
            minimum = 1
            maximum = 7
            value = 4
            valueIsAdjusting = true
        }
        labelTable = Hashtable(scales.map {
            it.key to JLabel("${(it.value * 100).toInt()}%")
        }.toMap())
        paintLabels = true
    }

    val scale
        get() = scales[value]
}