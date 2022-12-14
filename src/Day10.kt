import kotlin.math.sign

fun main() {

    fun evalSignalStrengthOrZero(clock: Int, x: Int): Int {

        if (clock % 40 == 20) {
            return x * clock
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        var clock = 1
        var signalStrength = 0
        var x = 1
        input.forEach { instruction ->
            if (instruction.startsWith("addx")) {
                clock++
                signalStrength += evalSignalStrengthOrZero(clock, x)
                x += instruction.split(" ").last().toInt()
            }
            clock++
            signalStrength += evalSignalStrengthOrZero(clock, x)
        }

        return signalStrength
    }

    fun crtPrint(pixelToWrite: Int, crt: List<MutableList<Char>>, spritePos: Int) {
        val crtX = pixelToWrite % 40
        if (spritePos - 1 <= crtX && crtX <= spritePos + 1) {
            val crtY = (pixelToWrite - (pixelToWrite % 40)) / 40
            crt[crtY][crtX] = '#'
        }
    }

    fun part2(input: List<String>): Int{
        val crt = List(6) { MutableList(40) {'.'} }
        var clock = 1
        var x = 1
        input.forEach { instruction ->
            crtPrint(clock - 1, crt, x)
            if (instruction.startsWith("addx")) {
                clock++
                crtPrint(clock - 1, crt, x)
                x += instruction.split(" ").last().toInt()
            }
            clock++
        }
        crt.forEach { row   -> println(row.joinToString("")) }
        return 0
    }

    val input = readInput("Day10")
    val part1 = part1(input)
    println("Result part1: $part1")
    println("Result part2:")
    part2(input)

}
