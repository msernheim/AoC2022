fun main() {

    fun getPriority(item: Char): Int {
        return when {
            item.isLowerCase() -> item.code - 96
            item.isUpperCase() -> item.code - 38
            else -> 0
        }
    }

    fun getDuplicates(head: String, tail: String): String {
        return head.filter { item -> tail.contains(item) }
    }

    fun part1(input: List<String>): Int {
        val duplicates = mutableListOf<Char>()

        input.forEach { items ->
            val head = items.slice(IntRange(0, items.length / 2 - 1))
            val tail = items.drop(items.length / 2)

            duplicates.add(getDuplicates(head, tail).first());
        }

        return duplicates.map(::getPriority).sum()
    }

    fun getBadgeForGroup(group: List<String>): Char {
        val commons = getDuplicates(group[0], group[1])
        return getDuplicates(commons, group[2]).first()
    }

    fun part2(input: List<String>): Int {
        val badges = mutableListOf<Char>()
        for (i in input.indices) {
            if (i % 3 == 0) badges.add(getBadgeForGroup(input.slice(IntRange(i, i+2))))
        }
        return badges.map(::getPriority).sum()
    }

    val input = readInput("Day03")
    val part1 = part1(input)
    val part2 = part2(input)

    println("Result part1: $part1")
    println("Result part2: $part2")
}
