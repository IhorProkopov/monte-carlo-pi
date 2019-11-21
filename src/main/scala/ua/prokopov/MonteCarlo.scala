package ua.prokopov

import java.util.concurrent.ForkJoinPool

import org.scalameter.{Key, Warmer, config}

import scala.util.Random


object MonteCarlo {

  val forkJoinPool = new ForkJoinPool

  def calculate(radius: Double, numberOfRepetion: Long): Double = {
    var pointsInCircle = 0
    for (x <- 0L to numberOfRepetion) {
      val x = Random.between(radius * -1, radius)
      val y = Random.between(radius * -1, radius)
      if (inCircle(x, y, radius)) pointsInCircle += 1
    }
    4 * pointsInCircle.toDouble / numberOfRepetion.toDouble
  }

  private def inCircle(x: Double, y: Double, radius: Double) = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= radius

}

object PiCalculation {

  def main(args: Array[String]): Unit = {

    val standardConfig = config(
      Key.exec.minWarmupRuns -> 5,
      Key.exec.maxWarmupRuns -> 10,
      Key.exec.benchRuns -> 10,
      Key.verbose -> true
    ) withWarmer (new Warmer.Default)


    val mc = MonteCarlo

    //    val seqtime = standardConfig measure {
    //      val res = mc.calculate(900_000_000, 900_000_000)
    //      println(res)
    //    }
    //    println(s"sequential pi time: $seqtime")
    val res = mc.calculate(900_000_000, 900_000_000)
    println(res)

  }

}
