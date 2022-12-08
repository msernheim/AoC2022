fun main() {

    fun findNextCommand(input: List<String>, cursor: Int): Int {
        return when (val indexOfNextCommand =
            input.subList(cursor, input.size - 1).indexOfFirst { line -> line.startsWith("$ ") }) {
            -1 -> input.size - 1
            else -> indexOfNextCommand + cursor
        }
    }

    fun increaseSizeBackToRoot(size: Int, currentDir: Item?) {
        var current = currentDir
        while (current!!.parent != null) {
            current.size += size
            current = current.parent
        }
        current.size += size
    }


    fun parseListDir(
        nextCommand: Int,
        i: Int,
        input: List<String>,
        currentDir: Item?
    ) {
        if (nextCommand > i) {
            for (j in i + 1 until nextCommand) {
                val words = input[j].split(" ")
                var item: Item
                if (words[0].toIntOrNull() != null) {
                    item = Item(words[1], Type.FILE, words[0].toInt(), mutableListOf(), currentDir)
                    increaseSizeBackToRoot(item.size, currentDir)
                } else {
                    item = Item(words[1], Type.DIRECTORY, 0, mutableListOf(), currentDir)
                }
                currentDir!!.contents.add(item)
            }
        }
    }

    fun sumSmallSizeDirs(item: Item?): Int {
        if (item == null) return 0
        return if (item.type == Type.DIRECTORY) {
            if (item.size <= 100000) {
                item.size + item.contents.sumOf { containingItem -> sumSmallSizeDirs(containingItem) }
            } else {
                item.contents.sumOf { containingItem -> sumSmallSizeDirs(containingItem) }
            }
        } else {
            0
        }
    }

    fun getRoot(item: Item?): Item? {
        var current = item
        while (current!!.parent != null) {
            current = current!!.parent
        }
        return current
    }

    fun isTargetInContents(contents: List<Item>, target: String) =
        contents.filter { item -> item.name == target }.isNotEmpty()

    fun parseCommands(input: List<String>): Item? {
        var current: Item? = null
        for (i in input.indices) {
            val line = input[i]
            when {
                line.contains("$ cd") -> {
                    val target = line.removePrefix("$ cd ")
                    when (target) {
                        ".." -> {
                            current = current!!.parent
                        }

                        else -> {
                            if (current != null && isTargetInContents(current!!.contents, target)) {
                                current = current!!.contents.first { item -> item.name == target }
                            } else if (current == null) {
                                val newDir = Item(target, Type.DIRECTORY, 0, mutableListOf(), current)
                                current = newDir
                            } else println("Could not execute: $line")
                        }
                    }
                }
                line.contains("$ ls") -> {
                    val nextCommand = findNextCommand(input, i + 1)
                    parseListDir(nextCommand, i, input, current)
                }
            }
        }

        return current
    }

    fun part1(input: List<String>): String {
        var currentDir: Item? = parseCommands(input)
        currentDir = getRoot(currentDir)
        return sumSmallSizeDirs(currentDir).toString()
    }

    fun findBestCleaningSize(root: Item, amountToFree: Int): Int {
        var currentBest = root.size
        var currentDir = root
        while (currentDir.contents.isNotEmpty()) {
            val bestChild =
                currentDir.contents.filter { child -> IntRange(amountToFree, currentBest).contains(child.size) }
                    .sortedByDescending { item -> item.size }.lastOrNull() ?: return currentBest
            currentBest = bestChild.size
            currentDir = bestChild
        }
        return currentBest
    }

    fun part2(input: List<String>): String {
        var currentDir: Item? = parseCommands(input)
        val root = getRoot(currentDir)
        val amountToFree = 30000000 - (70000000 - root!!.size)
        val sizeToFree = findBestCleaningSize(root, amountToFree)

        return sizeToFree.toString()
    }

    val input = readInput("Day07")

    val part1 = part1(input)
    val part2 = part2(input)

    println("Result part1: $part1")
    println("Result part2: $part2")
}

class Item(
    val name: String,
    val type: Type,
    var size: Int,
    val contents: MutableList<Item> = mutableListOf(),
    val parent: Item?
) {
    override fun toString(): String {
        return "Item( $name, size: $size contents: ${contents.size})"
    }
}

enum class Type {
    FILE, DIRECTORY
}
