fun main() {

    fun findDividingRow(input: List<String>): Int {
        return input.indexOf("")
    }

    fun getCargoMap(cargoMap: List<String>): MutableMap<Int, String> {
        val stacks = mutableMapOf<Int,String>()
        val last = cargoMap.last().trimEnd().last().digitToInt()
        for (i in 1..last) {
            val stackIndex = cargoMap.last().indexOf(i.digitToChar())
            var stack = ""
            for (level in (cargoMap.size - 1).downTo(0)) {
                if (level < cargoMap.size - 1) {
                    val item = cargoMap[level][stackIndex]
                    when {
                        item.isLetter() -> stack += item
                        else -> break
                    }
                }
            }
            stacks[i] = stack.trimEnd()
        }
        return stacks
    }

    fun getMoves(moves: List<String>): List<Move> {
        val result = mutableListOf<Move>()
        moves.forEach { move ->
            val from = move.substringAfter("from ").substringBefore(" to").toInt()
            val to = move.substringAfter("to ").toInt()
            val N = move.substringAfter("move ").substringBefore(" from").toInt()
            result.add(Move(from, to, N))
        }
        return result
    }

    fun executeMoves(cargoMap: MutableMap<Int, String>, moves: List<Move>, reverse: Boolean): Map<Int, String> {
        moves.forEach { move ->
            val srcStack = cargoMap[move.from].orEmpty()
            val itemsToMove = srcStack.substring(srcStack.length - move.N)
            cargoMap[move.to] += if (reverse) itemsToMove.reversed() else itemsToMove
            cargoMap[move.from] = srcStack.removeSuffix(itemsToMove)
        }
        return cargoMap
    }

    fun part1(input: List<String>, reverse: Boolean=true): String {
        val index = findDividingRow(input)
        val cargoMap = getCargoMap(input.slice(IntRange(0, index - 1)))
        val moves = getMoves(input.drop(index + 1))
        executeMoves(cargoMap, moves, reverse)
        return cargoMap.values.map{ stack -> stack[stack.length - 1]}.toString()
    }


    fun part2(input: List<String>): String {
        return part1(input, false)
    }

    val input = readInput("Day05")

    val part1 = part1(input)
    val part2 = part2(input)

    println("Result part1: $part1")
    println("Result part2: $part2")
}

class Move(val from: Int, val to: Int, val N: Int)
