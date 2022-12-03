fun main() {
    fun getMoveScore(s: String): Int {
        return when (s) {
            "X" -> 1
            "Y" -> 2
            "Z" -> 3
            else -> 0
        }
    }

    fun getMatchScore(moveA: String, moveB: String): Int {
        return when (moveA) {
            "A" -> {
                when (moveB) {
                    "X" -> 3
                    "Y" -> 6
                    "Z" -> 0
                    else -> 0
                }
            }

            "B" -> {
                when (moveB) {
                    "X" -> 0
                    "Y" -> 3
                    "Z" -> 6
                    else -> 0
                }
            }

            "C" -> {
                when (moveB) {
                    "X" -> 6
                    "Y" -> 0
                    "Z" -> 3
                    else -> 0
                }
            }

            else -> 0
        }
    }

    fun part1(input: List<String>): Int {
        var score = 0
        input.forEach { row ->

            val moves = row.split(" ")
            val moveScore = getMoveScore(moves[1])
            val matchScore = getMatchScore(moves[0], moves[1])
            score += moveScore
            score += matchScore
        }
        return score
    }

    fun figureOutMovePart2(moves: List<String>): String {
        return when (moves[1]) {
            "X" -> {   //LOSE
                when (moves[0]) {
                    "A" -> "Z"
                    "B" -> "X"
                    "C" -> "Y"
                    else -> ""
                }
            }

            "Y" -> (moves[0].toCharArray()[0].code + 23).toChar().toString()   //DRAW
            "Z" -> {//WIN
                when (moves[0]) {
                    "A" -> "Y"
                    "B" -> "Z"
                    "C" -> "X"
                    else -> ""
                }
            }

            else -> ""
        }
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach { row ->
            val moves = row.split(" ")
            val moveToMake = figureOutMovePart2(moves)
            score += getMoveScore(moveToMake) + getMatchScore(moves[0], moveToMake)
        }
        return score
    }


    val input = readInput("Day02")
    val part1 = part1(input)
    val part2 = part2(input)

    println("Result part1: $part1")
    println("Result part2: $part2")
}
