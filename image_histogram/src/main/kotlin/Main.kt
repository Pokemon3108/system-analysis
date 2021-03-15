import java.io.File
import javax.imageio.ImageIO

fun main() {
    val imageKesha = ImageIO.read(File("kesha.jpg")) // using BBC BASIC image
    imageKesha.toGrayScale()
    var grayFile = File("grayImageKesha.jpg")
    ImageIO.write(imageKesha, "jpg", grayFile)
    val histArr1=imageKesha.histogram()

    val imageCockatoo = ImageIO.read(File("cockatoo.jpg")) // using BBC BASIC image
    imageCockatoo.toGrayScale()
    grayFile = File("grayImageCockatoo.jpg")
    ImageIO.write(imageCockatoo, "jpg", grayFile)
    val histArr2=imageCockatoo.histogram()

    drawHistogram(imageCockatoo.ahistogram())
    drawHistogram(imageKesha.ahistogram())

    val averageKesha=Statistic.average(histArr1)
    val squareAverageKesha=Statistic.squareAverage(histArr1)
    val medianKesha=Statistic.median(histArr1)
    val modeKesha=Statistic.mode(histArr1)
    val hypotKesha=Statistic.checkHypothesisNormalDistribution(histArr1)


    val averageCockatoo=Statistic.average(histArr1)
    val squareAverageCockatoo=Statistic.squareAverage(histArr1)
    val medianCockatoo=Statistic.median(histArr1)
    val modeCockatoo=Statistic.mode(histArr1)
    val hypotCockatoo=Statistic.checkHypothesisNormalDistribution(histArr1)

    val coefCorrelation=Statistic.correlationCoef(histArr1, histArr2)


}





