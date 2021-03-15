import jetbrains.datalore.plot.base.stat.math3.Percentile
import jetbrains.datalore.plot.config.Option
import jetbrains.letsPlot.Stat
import kscience.kmath.operations.exp
import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation
import org.apache.commons.math3.stat.inference.ChiSquareTest
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

object Statistic {

    fun average(frequencies: IntArray): Double {
        return frequencies.average()
    }

    fun squareAverage(frequencies: IntArray): Double {
        val doubleFreq = frequencies.map { it.toDouble() }.toDoubleArray()
        val standardDeviation = StandardDeviation()
        return standardDeviation.evaluate(doubleFreq, average(frequencies))
    }

    fun median(arr: IntArray): Double {
        val doubleArray = arr.map { it.toDouble() }.toDoubleArray()
        return Percentile.evaluate(doubleArray, 50.0)
    }

    fun correlationCoef(arr1: IntArray, arr2: IntArray): Double {
        val doubleArr1 = arr1.map { it.toDouble() }.toDoubleArray()
        val doubleArr2 = arr2.map { it.toDouble() }.toDoubleArray()
        val correlCount = PearsonsCorrelation()
        return correlCount.correlation(doubleArr1, doubleArr2)
    }

    fun mode(arr: IntArray): IntArray {
        val doubleArray = arr.map { it.toDouble() }.toDoubleArray()
        return StatUtils.mode(doubleArray).map { it.toInt() }.toIntArray()
    }

    fun checkHypothesisNormalDistribution(realFrequencies: IntArray): Boolean {
        val average = average((realFrequencies.indices).map { it*realFrequencies[it] }.toIntArray())
        val squareAverage = squareAverage(realFrequencies)
        val n = realFrequencies.size
        val theoreticalFrequencies = (realFrequencies.indices)
            .map {
                (exp((-(realFrequencies[it] - average) / (2 * squareAverage * squareAverage)).pow(2)) * n)
                    .div(sqrt(2 * PI) * squareAverage)
            }.toDoubleArray()
        val chiSquareTest = ChiSquareTest()
        val alpha = 0.05
        val longFreq = realFrequencies.map { it.toLong() }.toLongArray()
        return chiSquareTest.chiSquareTest(theoreticalFrequencies, longFreq, alpha)
    }

}