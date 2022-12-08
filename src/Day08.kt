fun main() {
    fun getWest(row: String, until: Int): List<Int> {
        if (until > 0) {
            return row.substring(0, until).map { c -> c.digitToInt() }
        }
        return emptyList()
    }

    fun getEast(row: String, start: Int): List<Int> {
        if (start < row.length) {
            return row.substring(start).map { c -> c.digitToInt() }
        }
        return emptyList()
    }

    fun getNorth(grid: List<String>, max: Int, j: Int): List<Int> {
        if (max > 0) {
            val north = mutableListOf<Int>()
            for (k in 0 until max) {
                val vertDirTree = grid[k][j].digitToInt()
                north.add(vertDirTree)
            }
            return north
        }
        return emptyList()
    }

    fun getSouth(grid: List<String>, min: Int, j: Int): List<Int> {
        if (min < grid.size) {
            val south = mutableListOf<Int>()
            for (k in min until grid.size) {
                val vertDirTree = grid[k][j].digitToInt()
                south.add(vertDirTree)
            }
            return south
        }
        return emptyList()
    }

    fun isVisibleHorizontally(row: String, j: Int): Boolean {
        val tree = row[j].digitToInt()
        return tree > getWest(row, j).max() || tree > getEast(row, j + 1).max()
    }


    fun isVisibleVertically(grid: List<String>, i: Int, j: Int): Boolean {
        val tree = grid[i][j].digitToInt()
        return tree > getNorth(grid, i, j).max() || tree > getSouth(grid, i + 1, j).max()
    }

    fun part1(grid: List<String>): Int {
        val edge: Int = grid.size - 1
        var visibles = 0
        for (i in grid.indices) {
            when (i) {
                0 -> visibles += grid[i].length
                edge -> visibles += grid[i].length
                else -> {
                    for (j in grid[i].indices) {
                        when (j) {
                            0 -> visibles += 1
                            grid[i].length - 1 -> visibles += 1
                            else -> {
                                if (isVisibleHorizontally(grid[i], j) || isVisibleVertically(grid, i, j)) {
                                    visibles += 1
                                }
                            }
                        }
                    }
                }
            }
        }
        return visibles
    }


    fun countTrees(treeHeight: Int, treeLine: List<Int>): Int {
        return when (treeLine.size) {
            0 -> 0
            1 -> 1
            else -> {
                for (i in treeLine.indices) {
                    if (treeHeight <= treeLine[i]) return i + 1
                }
                return treeLine.size
            }
        }
    }

    fun getScore(treeHeight: Int, west: List<Int>, east: List<Int>, north: List<Int>, south: List<Int>): Int {
        var score = 1
        score *= countTrees(treeHeight, west.reversed())
        score *= countTrees(treeHeight, east)
        score *= countTrees(treeHeight, north.reversed())
        score *= countTrees(treeHeight, south)

        return score
    }

    fun part2(grid: List<String>): Int {
        var highscore = 0
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                val score = getScore(
                    grid[i][j].digitToInt(),
                    getWest(grid[i], j),
                    getEast(grid[i], j + 1),
                    getNorth(grid, i, j),
                    getSouth(grid, i + 1, j)
                )
                highscore = if (score > highscore) score else highscore
            }
        }
        return highscore
    }

    val input = readInput("Day08")

    val part1 = part1(input)
    val part2 = part2(input)

    println("Result part1: $part1")
    println("Result part2: $part2")
}