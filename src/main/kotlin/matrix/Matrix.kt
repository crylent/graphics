package matrix

import java.awt.Point
import java.util.function.UnaryOperator
import kotlin.math.roundToInt


class Matrix(arr: Collection<DoubleArray>): ArrayList<DoubleArray>(arr) {
    constructor(vararg lines: DoubleArray) : this(lines.map { it })

    val columns = this[0].size //width
    val rows = this.size //height

    init {
        if (this.isEmpty()) error("It mustn't be empty")
        for (i in this.indices) {
            if (columns != this[i].size)
                throw IllegalArgumentException("Wrong size!")
        }
    }

    /** Matrix x Matrix multiplication **/
    fun multiply(b: Matrix): Matrix {
        val result = List(rows) { Array(b.columns) { .0 }.toDoubleArray() }
        for (i in 0 until rows) {
            for (j in 0 until b.columns) {
                for (k in 0 until columns) {
                    result[i][j] += this[i][k] * b[k][j]
                }
            }
        }
        return Matrix(result)
    }

    override fun toString(): String { //to string
        var result = ""
        var ans: String
        for (i in this.indices) {
            ans = this[i].contentToString()
            result += "$ans\n"
        }
        return result
    }

    fun toPoints(): List<Point> {
        if (columns != 2) throw UnsupportedOperationException()
        return map { Point(it[0].roundToInt(), it[1].roundToInt()) }
    }

    override fun add(element: DoubleArray): Boolean { throw UnsupportedOperationException() }
    override fun add(index: Int, element: DoubleArray) { throw UnsupportedOperationException() }
    override fun remove(element: DoubleArray): Boolean { throw UnsupportedOperationException() }
    override fun replaceAll(operator: UnaryOperator<DoubleArray>) { throw UnsupportedOperationException() }
    override fun sort(c: Comparator<in DoubleArray>) { throw UnsupportedOperationException() }
}

fun row(vararg e: Number) = e.map { it.toDouble() }.toDoubleArray()
fun row(vararg e: Double) = doubleArrayOf(*e)