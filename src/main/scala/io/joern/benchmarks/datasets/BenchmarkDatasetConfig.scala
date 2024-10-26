package io.joern.benchmarks.datasets

import better.files.File

case class BenchmarkDatasetConfig(
  benchmark: AvailableBenchmarks.Value = AvailableBenchmarks.ALL,
  datasetDir: File = File("workspace")
)

object AvailableBenchmarks extends Enumeration {
  val ALL = Value

  // Joern
  val SECURIBENCH_MICRO_SRC  = Value
  val SECURIBENCH_MICRO_JAVA = Value
  val ICHNAEA_JSSRC          = Value
  val THORAT_PYSRC           = Value
  val BUGS_IN_PY             = Value
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
