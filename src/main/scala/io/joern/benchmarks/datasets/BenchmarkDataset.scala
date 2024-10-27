package io.joern.benchmarks.datasets

import io.joern.benchmarks.datasets.BenchmarkDataset.benchmarkConstructors
import io.joern.benchmarks.datasets.AvailableBenchmarks
import io.joern.benchmarks.datasets.runner.{
  BugsInPyDownloader,
  DatasetDownloader,
  Defects4jDownloader,
  IchnaeaDownloader,
  SecuribenchMicroDownloader,
  ThoratDownloader
}
import org.slf4j.LoggerFactory
import upickle.default.*

class BenchmarkDataset(config: BenchmarkDatasetConfig) {
  private val logger = LoggerFactory.getLogger(getClass)

  def evaluate(): Unit = {
    logger.info("Beginning downloads")

    def runBenchmark(benchmarkRunnerCreator: BenchmarkDatasetConfig => DatasetDownloader): Unit = {
      benchmarkRunnerCreator(config).run()
    }

    if (config.benchmark == AvailableBenchmarks.ALL) {
      benchmarkConstructors.values.foreach(runBenchmark)
    } else {
      benchmarkConstructors.get(config.benchmark).foreach(runBenchmark)
    }
  }
}

object BenchmarkDataset {
  val benchmarkConstructors: Map[AvailableBenchmarks.Value, BenchmarkDatasetConfig => DatasetDownloader] = Map(
    (
      AvailableBenchmarks.SECURIBENCH_MICRO_SRC,
      x => new SecuribenchMicroDownloader(x.datasetDir, JavaCpgTypes.JAVASRC)
    ),
    (AvailableBenchmarks.SECURIBENCH_MICRO_JAVA, x => new SecuribenchMicroDownloader(x.datasetDir, JavaCpgTypes.JAVA)),
    (AvailableBenchmarks.ICHNAEA, x => new IchnaeaDownloader(x.datasetDir)),
    (AvailableBenchmarks.THORAT, x => new ThoratDownloader(x.datasetDir)),
    (AvailableBenchmarks.BUGS_IN_PY, x => new BugsInPyDownloader(x.datasetDir)),
    (AvailableBenchmarks.DEFECTS4J, x => new Defects4jDownloader(x.datasetDir))
  )

}
