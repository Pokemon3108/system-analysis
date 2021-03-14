import java.awt.Color
import java.awt.image.BufferedImage


fun BufferedImage.toGrayScale() {
    for (x in 0 until width) {
        for (y in 0 until height) {
            var argb = getRGB(x, y)
            val alpha = (argb shr 24) and 0xFF
            val red = (argb shr 16) and 0xFF
            val green = (argb shr 8) and 0xFF
            val blue = argb and 0xFF
            val lumin = (0.2126 * red + 0.7152 * green + 0.0722 * blue).toInt()
            argb = (alpha shl 24) or (lumin shl 16) or (lumin shl 8) or lumin
            setRGB(x, y, argb)
        }
    }
}

fun BufferedImage.histogram(): IntArray {
    val lumCount = IntArray(256)
    for (x in 0 until width) {
        for (y in 0 until height) {
            val argb = getRGB(x, y)
            val color = argb and 0xFF
            ++lumCount[color]
        }
    }
    return lumCount
}