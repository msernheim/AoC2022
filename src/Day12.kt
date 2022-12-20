fun main() {


    fun findFirstOrNull(input: List<String>, target: Char): Coord? {
        for (i in input.indices) {
            for (j in input[i].indices) {
                when (input[i][j]) {
                    target -> return Coord(j, i)
                }
            }
        }
        return null
    }

    fun withinReach(current: Char, next: Char): Boolean {
        return current.code >= next.code - 1
    }

    fun allowedMove(current: Char, next: Char): Boolean {
        return when (current) {
            'S' -> next == 'a' || next == 'b'
            'y' -> withinReach(current, next) || next == 'E'
            'z' -> withinReach(current, next) || next == 'E'
            else -> next != 'E' && withinReach(current, next)
        }
    }

    fun getNeighbours(path: List<Coord>, input: List<String>): List<Coord> {
        val current = path.last()
        val neighbors = listOf(
            Coord(current.X - 1, current.Y),
            Coord(current.X + 1, current.Y),
            Coord(current.X, current.Y - 1),
            Coord(current.X, current.Y + 1)
        )
        return neighbors.filter { coord ->
            !path.contains(coord) &&
                    coord.X >= 0 && coord.X < input[current.Y].length && //check X inside grid
                    coord.Y >= 0 && coord.Y < input.size && //check Y inside grid
                    allowedMove(input[current.Y][current.X], input[coord.Y][coord.X]) //check altitude
        }.toList()
    }

    fun anyPathReachedGoal(paths: MutableList<List<Coord>>, end: Coord): Boolean {
        paths.forEach { path -> if (path.last() == end) return true }
        return false
    }

    fun getShortestPath(start: Coord, end: Coord, input: List<String>): Int {
        var paths = mutableListOf<List<Coord>>()
        var toRemove = mutableListOf<List<Coord>>()
        var toAdd = mutableListOf<List<Coord>>()
        paths.add(mutableListOf(start))
        while (paths.isNotEmpty() && !anyPathReachedGoal(paths, end)) {
            paths.forEach { path ->
                val current = path.last()
                val neighbours = getNeighbours(
                    path,
                    input
                )
                if (neighbours.isNotEmpty()) {
                    neighbours.forEach { coord -> toAdd.add(path.plus(coord)) }
                }
                toRemove.add(path)
            }
            paths.removeAll(toRemove)
            paths.addAll(toAdd)
            paths = paths.distinctBy { coords -> coords.last() }.toMutableList()
            toRemove.clear()
            toAdd.clear()
        }
        return if (paths.isNotEmpty()) paths.filter { path -> path.last() == end }.sortedBy { path -> path.size }.first().size - 1 else -1
    }

    fun part1(input: List<String>): Int {
        val waypoints = Pair(findFirstOrNull(input, 'S'), findFirstOrNull(input, 'E'))
        return getShortestPath(waypoints.first!!, waypoints.second!!, input)

    }

    fun findAllStarts(input: List<String>): List<Coord> {
        val starts = mutableListOf<Coord>()
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == 'a' || c == 'S') starts.add(Coord(x, y))
            }
        }
        return starts
    }

    fun part2(input: List<String>): Int {
        val starts = findAllStarts(input)
        val end = findFirstOrNull(input, 'E')!!
        return starts.map { start -> getShortestPath(start, end, input) }.filter { i -> i > 0 }.minOf { it }
    }

    val input = readInput("Day12")
    val part1 = part1(input)
    val part2 = part2(input)
    println("Result part1: $part1")
    println("Result part2: $part2")
}

