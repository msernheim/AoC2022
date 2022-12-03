fun main() {
    fun part1(input: List<String>): Int {
        var cMax = 0;
        var current = 0;
        input.forEach { food ->
            if (food.equals("")) {
                if (cMax >= current) {
                    current = 0
                } else {
                    cMax = current
                    current = 0
                }
            } else {
                current += food.toInt()
            }
        }
        return cMax
    }

    fun placeInTopThree(current: Int, topThree: MutableList<Int>) {
        if (current > topThree[0]) {
            topThree[2] = topThree[1]
            topThree[1] = topThree[0]
            topThree[0] = current
        } else if(current > topThree[1]) {
            topThree[2] = topThree[1]
            topThree[1] = current
        } else if (current > topThree[2]) {
            topThree[2] = current
        }
    }

    fun part2(input: List<String>): Int {
        val topThree = mutableListOf(0, 0, 0);
        var current = 0;
        input.forEach { food ->
            if (food.equals("")) {
                placeInTopThree(current, topThree)
                current = 0
            } else {
                current += food.toInt()
            }
        }
        return topThree.sum()
    }


    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
