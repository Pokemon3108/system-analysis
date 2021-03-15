

import jetbrains.datalore.plot.base.stat.math3.Percentile
import jetbrains.datalore.plot.config.Option
import jetbrains.letsPlot.Stat
import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation
import kotlin.math.sqrt

class Statistic {

    fun average(arr:IntArray): Double {
        return arr.average()
    }

    fun squareAverage(arr:IntArray): Double {
        val squareAverage=arr.map { it*it }.average()
        return sqrt(squareAverage)
    }

    fun mediana(arr:IntArray): Double{
        val doubleArray=arr.map { it.toDouble() }.toDoubleArray()
        return Percentile.evaluate(doubleArray, 50.0)
    }

    fun correlationCoef(arr1:IntArray, arr2:IntArray) : Double {
        val doubleArr1=arr1.map { it.toDouble() }.toDoubleArray()
        val doubleArr2=arr2.map { it.toDouble() }.toDoubleArray()
        val correlCount=PearsonsCorrelation()
        return correlCount.correlation(doubleArr1, doubleArr2)
    }

    fun mode(arr:IntArray) : IntArray {
        val doubleArray=arr.map { it.toDouble() }.toDoubleArray()
        return StatUtils.mode(doubleArray).map { it.toInt() }.toIntArray()
    }



}