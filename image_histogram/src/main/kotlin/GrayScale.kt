import java.io.File
import javax.imageio.ImageIO

fun main() {
    val image = ImageIO.read(File("image1.jpg")) // using BBC BASIC image
    image.toGrayScale()
    val arr=image.histogram()
    println(arr)
//    val grayFile = File("grayImage1.jpg")
//    ImageIO.write(image, "jpg", grayFile)
}



