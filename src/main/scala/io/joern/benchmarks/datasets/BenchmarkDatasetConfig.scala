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
  val ICHNAEA                = Value
  val THORAT                 = Value
  val BUGS_IN_PY             = Value
  val DEFECTS4J              = Value
}

object JavaCpgTypes extends Enumeration {
  val JAVASRC = Value
  val JAVA    = Value
}
