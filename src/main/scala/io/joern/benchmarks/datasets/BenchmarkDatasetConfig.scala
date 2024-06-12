package io.joern.benchmarks.datasets

import better.files.File

case class BenchmarkDatasetConfig(
  benchmark: AvailableBenchmarks.Value = AvailableBenchmarks.ALL,
  datasetDir: File = File("workspace")
)

object AvailableBenchmarks extends Enumeration {
  val ALL                       = Value
  val OWASP_JAVASRC             = Value
  val OWASP_JAVA                = Value
  val SECURIBENCH_MICRO_JAVASRC = Value
  val SECURIBENCH_MICRO_JAVA    = Value
  val ICHNAEA_JSSRC             = Value
}

object JavaCpgTypes extends Enumeration {
  val JAVASRC = Value
  val JAVA    = Value
}

object OutputFormat extends Enumeration {
  val JSON = Value
  val CSV  = Value
  val MD   = Value
}
