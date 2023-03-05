fun main() {
    val a = Matrix(listOf(doubleArrayOf(1.0, 2.0, 3.0), doubleArrayOf(4.0, 5.0, 6.0)))
    val b = Matrix(listOf(doubleArrayOf(7.0, 8.0), doubleArrayOf(9.0, 10.0), doubleArrayOf(11.0, 12.0)))
    val c = a.multiply(b)
    println(c.toString()) // [58.0, 64.0, 0.0][139.0, 154.0, 0.0]
}