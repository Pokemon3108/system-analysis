import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.plot.MonolithicAwt
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.swing.BatikMapperComponent
import jetbrains.datalore.vis.swing.BatikMessageCallback
import jetbrains.letsPlot.geom.geom_histogram
import jetbrains.letsPlot.geom.geom_vline
import jetbrains.letsPlot.ggplot
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.intern.toSpec
import jetbrains.letsPlot.label.ggtitle
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

private val SVG_COMPONENT_FACTORY_BATIK =
    { svg: SvgSvgElement -> BatikMapperComponent(svg, BATIK_MESSAGE_CALLBACK) }

private val BATIK_MESSAGE_CALLBACK = object : BatikMessageCallback {
    override fun handleMessage(message: String) {
        println(message)
    }

    override fun handleException(e: Exception) {
        if (e is RuntimeException) {
            throw e
        }
        throw RuntimeException(e)
    }
}

private val AWT_EDT_EXECUTOR = { runnable: () -> Unit ->
    runnable.invoke()
}

fun drawHistogram(values: List<Int>) {

    val mapped = values.map { it / 10 }

    SwingUtilities.invokeLater {

        val data = mapOf<String, Any>(
            "x" to mapped,
            "c" to List(values.size) { "A" }
        )

        val geom = geom_histogram(alpha = 0.3, size = 0.0, bins = 25, color = "black") {
            x = "x"; fill = "c"
        }
        val p = ggplot(data) + ggsize(1920, 1080) +
                geom+
                geom_vline(xintercept = mapped.average(), color = "red", linetype = "dashed", size = 1.0) +
                ggtitle("Gray scaled image distribution")

        val plotSpec = p.toSpec()
        val plotSize = DoubleVector(900.0, 500.0)

        val component =
            MonolithicAwt.buildPlotFromRawSpecs(
                plotSpec,
                plotSize,
                plotMaxWidth = null,
                SVG_COMPONENT_FACTORY_BATIK, AWT_EDT_EXECUTOR
            ) {
                for (message in it) {
                    println("PLOT MESSAGE: $message")
                }
            }

        val frame = JFrame("Image histogram")
        frame.contentPane.add(component)
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.pack()
        frame.isVisible = true
    }
}