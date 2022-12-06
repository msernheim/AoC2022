fun main() {

    fun getFirstMarker(input: String, n: Int): Int {
        if (input.length < n) return -1
        for (i in 0..input.length - n) {
            if (input.substring(i, i + n).toSet().size == n) {
                return i + n
            }
        }
        return 0
    }

    fun part1(input: String): Int {
        return getFirstMarker(input, 4)
    }

    fun part2(input: String): Int {
        return getFirstMarker(input, 14)
    }

    val input = readInputAsString("Day06")
    val part1 = part1(input)
    val part2 = part2(input)

    println("Result part1: $part1")
    println("Result part2: $part2")
}