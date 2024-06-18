package io.joern.benchmarks.datasets

import io.joern.benchmarks.datasets.BenchmarkDataset.benchmarkConstructors
import io.joern.benchmarks.datasets.AvailableBenchmarks
import io.joern.benchmarks.datasets.runner.{
  DatasetDownloader,
  IchnaeaDownloader,
  SecuribenchMicroDownloader,
  ThoratDownloader
}
import org.slf4j.LoggerFactory
import upickle.default.*

/** The main benchmarking process.
  */
class BenchmarkDataset(config: BenchmarkDatasetConfig) {
  private val logger = LoggerFactory.getLogger(getClass)

  def evaluate(): Unit = {
    logger.info("Beginning evaluation")

    def runBenchmark(benchmarkRunnerCreator: BenchmarkDatasetConfig => DatasetDownloader): Unit = {
      val benchmarkRunner = benchmarkRunnerCreator(config)
      val benchmarkName   = benchmarkRunner.benchmarkName
      logger.info(s"Running $benchmarkName")
      benchmarkRunner.run()
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
      AvailableBenchmarks.SECURIBENCH_MICRO_JAVASRC,
      x => new SecuribenchMicroDownloader(x.datasetDir, JavaCpgTypes.JAVASRC)
    ),
    (AvailableBenchmarks.SECURIBENCH_MICRO_JAVA, x => new SecuribenchMicroDownloader(x.datasetDir, JavaCpgTypes.JAVA)),
    (AvailableBenchmarks.ICHNAEA_JSSRC, x => new IchnaeaDownloader(x.datasetDir)),
    (
      AvailableBenchmarks.SECURIBENCH_MICRO_SEMGREP,
      x => new SecuribenchMicroDownloader(x.datasetDir, JavaCpgTypes.SEMGREP)
    ),
    (AvailableBenchmarks.ICHNAEA_SEMGREP, x => new IchnaeaDownloader(x.datasetDir)),
    (AvailableBenchmarks.THORAT_PYSRC, x => new ThoratDownloader(x.datasetDir)),
    (AvailableBenchmarks.THORAT_SEMGREP, x => new ThoratDownloader(x.datasetDir))
  )

}
