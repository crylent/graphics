import java.awt.Point
import java.util.function.UnaryOperator


class Matrix(arr: Collection<IntArray>): ArrayList<IntArray>(arr) {
    constructor(vararg lines: IntArray) : this(lines.map { it })

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
        val result = List(rows) { Array(b.columns) { 0 }.toIntArray() }
        for (i in result.indices) {
            for (j in 0 until b.rows) {
                for (k in 0 until this.columns) {
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
        return map { Point(it[0], it[1]) }
    }

    override fun add(element: IntArray): Boolean { throw UnsupportedOperationException() }
    override fun add(index: Int, element: IntArray) { throw UnsupportedOperationException() }
    override fun remove(element: IntArray): Boolean { throw UnsupportedOperationException() }
    override fun replaceAll(operator: UnaryOperator<IntArray>) { throw UnsupportedOperationException() }
    override fun sort(c: Comparator<in IntArray>) { throw UnsupportedOperationException() }
}

fun row(vararg e: Int) = intArrayOf(*e)