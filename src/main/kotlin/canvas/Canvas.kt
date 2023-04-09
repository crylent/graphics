package canvas

import matrix.Matrix
import matrix.orthoProjection
import matrix.row
import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.Polygon
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import javax.swing.JPanel

const val CANVAS_WIDTH = 600
const val CANVAS_HEIGHT = 600

class Canvas: JPanel(true) {
    private lateinit var image: BufferedImage
    private lateinit var gfx: Graphics

    var scale = 1.0

    var drawAxis = false

    init {
        draw {}
    }

    fun draw(lambda: (Canvas) -> Unit) {
        image = BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, TYPE_INT_RGB)
        gfx = image.createGraphics()
        if (drawAxis) {
            drawPoly(Matrix(row(Int.MIN_VALUE, 0), row(Int.MAX_VALUE, 0))) // draw X-axis
            drawPoly(Matrix(row(0, Int.MIN_VALUE + 1), row(0, Int.MAX_VALUE))) // draw Y-axis
        }
        lambda(this)
        if (graphics != null) paintComponent(graphics)
    }

    private fun convertPoint(p: Point) = Point(
        (p.x * scale + CANVAS_WIDTH / 2).toInt(),
        (-p.y * scale + CANVAS_HEIGHT / 2).toInt()
    )

    override fun paintComponent(g: Graphics) {
        g.drawImage(image, 0, 0, width, height, null)
    }

    fun drawPoly(pointsMatrix: Matrix, type: Poly = Poly.LINE, color: Color = Color.WHITE) {
        gfx.color = color
        val poly = Polygon()
        pointsMatrix.toPoints().forEach {
            val p = convertPoint(it)
            poly.addPoint(p.x, p.y)
        }
        when (type) {
            Poly.FILLED -> gfx.fillPolygon(poly)
            Poly.POLYGON -> gfx.drawPolygon(poly)
            Poly.LINE -> gfx.drawPolyline(poly.xpoints, poly.ypoints, poly.npoints)
        }
    }

    fun drawPoly3D(pointsMatrix: Matrix, type: Poly = Poly.LINE) {
        drawPoly(
            Matrix(pointsMatrix.orthoProjection().map {
                row(it[0] / it[3], it[1] / it[3])
            }),
            type
        )
    }
}

enum class Poly {
    FILLED, POLYGON, LINE
}