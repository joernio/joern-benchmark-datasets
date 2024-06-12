package io.joern.benchmarks.datasets

import better.files.File

case class BenchmarkDatasetConfig(
  benchmark: AvailableBenchmarks.Value = AvailableBenchmarks.ALL,
  datasetDir: File = File("workspace")
)

object AvailableBenchmarks extends Enumeration {
  val ALL = Value

  // Joern
  val OWASP_JAVASRC             = Value
  val OWASP_JAVA                = Value
  val SECURIBENCH_MICRO_JAVASRC = Value
  val SECURIBENCH_MICRO_JAVA    = Value
  val ICHNAEA_JSSRC             = Value
  val THORAT_PYSRC              = Value

  // Semgrep
  val OWASP_SEMGREP             = Value
  val SECURIBENCH_MICRO_SEMGREP = Value
  val THORAT_SEMGREP            = Value
  val ICHNAEA_SEMGREP           = Value
}

object JavaCpgTypes extends Enumeration {
  val JAVASRC = Value
  val JAVA    = Value
  val SEMGREP = Value
}

object OutputFormat extends Enumeration {
  val JSON = Value
  val CSV  = Value
  val MD   = Value
}
