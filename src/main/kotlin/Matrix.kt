class Matrix(arr: Collection<DoubleArray>): ArrayList<DoubleArray>(arr) {
    constructor(vararg lines: DoubleArray) : this(lines.map { it })

    init {
        /* TODO: check sizes */
    }

    /** Matrix x Matrix multiplication **/
    fun multiply(other: Matrix): Matrix {
        TODO()
    }
}