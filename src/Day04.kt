fun main() {

    fun sectionsToInterval(sections: String): IntRange {
        val split = sections.split("-")

        return when {
            split.size > 1 -> IntRange(split[0].toInt(), split[1].toInt())
            else -> IntRange(sections.toInt(), sections.toInt())
        }
    }

    fun isOverlapping(rangeA: IntRange, rangeB: IntRange): Boolean {
        return when {
            rangeA.first < rangeB.first -> rangeA.last >= rangeB.last
            rangeB.first < rangeA.first -> rangeB.last >= rangeA.last
            else -> true
        }
    }

    fun part1(input: List<String>): Int {
        var count = 0
        input.forEach { pair ->
            val intervals = pair.split(",").map { elf -> sectionsToInterval(elf) }
            if (isOverlapping(intervals[0], intervals[1])) count++
        }
        return count
    }


    fun hasAnyOverlap(rangeA: IntRange, rangeB: IntRange): Boolean {
        return when {
            rangeA.first < rangeB.first -> rangeB.contains(rangeA.last) || rangeA.last > rangeB.last
            rangeB.first < rangeA.first -> rangeA.contains(rangeB.last) || rangeB.last > rangeA.last
            else -> true
        }
    }

    fun part2(input: List<String>): Int {
        var count = 0
        input.forEach { pair ->
            val intervals = pair.split(",").map { elf -> sectionsToInterval(elf) }
            if (hasAnyOverlap(intervals[0], intervals[1])) count++
        }
        return count
    }

    val input = readInput("Day04")

    val part1 = part1(input)
    val part2 = part2(input)

    println("Result part1: $part1")
    println("Result part2: $part2")
}
