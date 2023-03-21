package controls

import java.util.*
import javax.swing.JLabel
import javax.swing.JSlider

class TurnSlider(onChange: ((Double) -> Unit)? = null): JSlider(-180, 180) {
    private val labels = intArrayOf(-180, -90, 0, 90, 180)

    init {
        labelTable = Hashtable(labels.associateWith {
            JLabel(it.toString())
        })
        paintLabels = true
        orientation = VERTICAL
        addChangeListener {
            onChange?.invoke(value.toDouble())
        }
    }
}