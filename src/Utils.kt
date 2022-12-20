import java.io.File
import java.math.BigInteger
import java.security.MessageDigest


/**
 * Reads lines from the given input txt file.
 */
fun readInputAsString(name: String) = File("src/input", "$name.txt")
    .readText()

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/input", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

class Coord(var X: Int, var Y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coord

        if (X != other.X) return false
        if (Y != other.Y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = X
        result = 31 * result + Y
        return result
    }

    override fun toString(): String {
        return "(X=$X, Y=$Y)"
    }

    fun toPair(): Pair<Int, Int> {
        return Pair(this.X, this.Y)
    }
}