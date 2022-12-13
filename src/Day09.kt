import kotlin.math.sign

fun main() {


    fun moveTail(head: Coord, tail: Coord): Coord {
        var tailX = tail.X
        var tailY = tail.Y
        val dx = (head.X - tail.X)
        val dy = (head.Y - tail.Y)
        if (dx * dx > 1 || dy * dy > 1) {
            tailX += dx.sign
            tailY += dy.sign
        }
        return Coord(tailX, tailY)
    }

    fun part1(input: List<String>): Int {
        val tailVisits = mutableListOf<Coord>()
        var currentHead = Coord(0, 0)
        var currentTail = Coord(0, 0)
        input.forEach { instruction ->
            val dir = instruction.split(" ").first()
            val length = instruction.split(" ").last().toInt()
            for (i in 0 until length) {
                when (dir) {
                    "R" -> currentHead.X++
                    "L" -> currentHead.X--
                    "U" -> currentHead.Y++
                    "D" -> currentHead.Y--
                    else -> break
                }
                currentTail = moveTail(currentHead, currentTail)
                tailVisits.add(currentTail)
            }
        }
        val coordSet = tailVisits.toSet()
        return coordSet.size
    }

    fun moveRope(rope: MutableList<Coord>) {
        for (i in 0 until rope.size - 1) {
            val dx = (rope[i].X - rope[i + 1].X)
            val dy = (rope[i].Y - rope[i + 1].Y)
            if (dx * dx > 1 || dy * dy > 1) {
                rope[i + 1].X += dx.sign
                rope[i + 1].Y += dy.sign
            }
        }
    }

    fun part2(input: List<String>): Int {
        val tailVisits = mutableSetOf<Pair<Int,Int>>()
        val rope = MutableList(10) { Coord(0, 0) }

        input.forEach { instruction ->
            val dir = instruction.split(" ").first()
            val length = instruction.split(" ").last().toInt()
            for (i in 0 until length) {
                when (dir) {
                    "R" -> rope[0].X++
                    "L" -> rope[0].X--
                    "U" -> rope[0].Y++
                    "D" -> rope[0].Y--
                    else -> break
                }
                moveRope(rope)
                tailVisits.add(rope[9].toPair())
            }
        }
        return tailVisits.size
    }

    val input = readInput("Day09")

    val part1 = part1(input)
    val part2 = part2(input)

    println("Result part1: $part1")
    println("Result part2: $part2")
}

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