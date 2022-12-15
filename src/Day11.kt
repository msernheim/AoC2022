import kotlin.math.floor

fun main() {

    fun getOperation(operation: String): (ULong) -> ULong {
        val parts = operation.split(" ")
        if (parts.size == 3) {
            if (parts[2] == "old") {
                return when (parts[1]) {
                    "*" -> { x: ULong -> x * x }
                    "+" -> { x: ULong -> x + x }
                    else -> { x: ULong -> x }
                }
            }
            return when (parts[1]) {
                "*" -> { x: ULong -> x * parts[2].toULong() }
                "+" -> { x: ULong -> x + parts[2].toULong() }
                else -> { x: ULong -> x }
            }
        }
        return { x: ULong -> x }
    }

    fun parseMonkeys(input: String): Map<Int, Monkey> {
        val monkeys = mutableMapOf<Int, Monkey>()
        input.split("Monkey ").forEach { stringMonkey ->
            var items = mutableListOf<ULong>()
            var operation = { x: ULong -> x }
            var test = 1
            var targetSuccess = 0
            var targetFail = 1
            var key = -1
            stringMonkey.split("\n").forEach { row ->
                when {
                    row.trim().replace(":", "").toIntOrNull() != null -> {
                        key = row.trim().replace(":", "").toInt()
                    }

                    row.trimStart().startsWith("Starting items: ") -> {
                        items = row.trimStart().removePrefix("Starting items: ")
                            .split(", ").map { item -> item.toULong() }.toMutableList()
                    }

                    row.trimStart().startsWith("Operation: ") -> {
                        operation = getOperation(row.trimStart().removePrefix("Operation: new = "))
                    }

                    row.trimStart().startsWith("Test: divisible by ") -> {
                        test = row.trimStart().removePrefix("Test: divisible by ").toInt()
                    }

                    row.trimStart().startsWith("If true: ") -> {
                        targetSuccess = row.trimStart().removePrefix("If true: throw to monkey ").toInt()
                    }

                    row.trimStart().startsWith("If false: ") -> {
                        targetFail = row.trimStart().removePrefix("If false: throw to monkey ").toInt()
                    }
                }
            }
            if (key != -1) monkeys[key] = Monkey(items, operation, test.toULong(), targetSuccess, targetFail)
        }
        return monkeys
    }


    fun part1(input: String): Long {
        val monkeys = parseMonkeys(input)
        for (i in 1..20) {
            for (key in 0 until monkeys.size) {
                val currentMonkey = monkeys[key]
                currentMonkey!!.items.forEach { item: ULong ->
                    val newWorry = floor(currentMonkey.inspect(item).toDouble() / 3.0).toULong()
                    when {
                        newWorry.mod(currentMonkey.testDivisor)
                            .toInt() == 0 -> monkeys[currentMonkey.targetSuccess]!!.items.add(newWorry)

                        else -> monkeys[currentMonkey.targetFail]!!.items.add(newWorry)
                    }
                    monkeys[key]!!.nInspections++
                }
                monkeys[key]!!.items.clear()
            }
        }
        val highest = monkeys.values.maxOfOrNull { monkey -> monkey.nInspections }
        val secondHighest = monkeys.values.map { monkey -> monkey.nInspections }.sortedDescending()[1]
        return highest!! * secondHighest

    }

    fun part2(input: String, rounds: Int): Long {
        val monkeys = parseMonkeys(input)
        val commonD = monkeys.values.map { monk -> monk.testDivisor }.reduce { acc, next -> acc * next }
        for (i in 1..rounds) {
            for (key in 0 until monkeys.size) {
                val currentMonkey = monkeys[key]
                currentMonkey!!.items.forEach { item: ULong ->
                    val newWorry = currentMonkey.inspect(item) % commonD
                    when {
                        newWorry.mod(currentMonkey.testDivisor)
                            .toInt() == 0 -> monkeys[currentMonkey.targetSuccess]!!.items.add(newWorry)

                        else -> monkeys[currentMonkey.targetFail]!!.items.add(newWorry)
                    }
                    monkeys[key]!!.nInspections++
                }
                monkeys[key]!!.items.clear()
            }
        }
        val highest = monkeys.values.maxOfOrNull { monkey -> monkey.nInspections }
        val secondHighest = monkeys.values.map { monkey -> monkey.nInspections }.sortedDescending()[1]
        return highest!! * secondHighest
    }

    val input = readInputAsString("Day11")
    val part1 = part1(input)
    val part2 = part2(input, 10000)
    println("Result part1: $part1")
    println("Result part2: $part2")

}

class Monkey(
    var items: MutableList<ULong>,
    val inspect: (worry: ULong) -> ULong,
    val testDivisor: ULong,
    val targetSuccess: Int,
    val targetFail: Int,
    var nInspections: Long = 0L
) {
    override fun toString(): String {
        return "Monkey(items=$items, inspections=$nInspections)"
    }
}

