class Matrix(arr: Collection<DoubleArray>) : ArrayList<DoubleArray>(arr) {
    // constructor(vararg lines: DoubleArray) : this(lines.map { it })

    private val columns = this[0].size //width
    private val rows = this.size //height

    init {
        if (this.isEmpty()) error("It mustn't be empty")
        for (i in this.indices) {
            if (columns != this[i].size)
                throw IllegalArgumentException("Wrong size!")
        }
    }

    /** Matrix x Matrix multiplication **/
    fun multiply(b: Matrix): Matrix {
        val result = List(rows) { Array(b[0].size) { .0 }.toDoubleArray() }
        for (i in result.indices) {
            for (j in 0 until this.rows) {
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
            result += ans
        }
        return result
    }

}