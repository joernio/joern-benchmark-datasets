package io.joern.benchmarks.datasets

import better.files.File
import org.slf4j.LoggerFactory
import scopt.OptionParser

import scala.util.{Failure, Success}

object Main {

  def main(args: Array[String]): Unit = {
    optionParser.parse(args, BenchmarkDatasetConfig()).map(BenchmarkDataset(_)).foreach(_.evaluate())
  }

  private val optionParser: OptionParser[BenchmarkDatasetConfig] =
    new OptionParser[BenchmarkDatasetConfig]("joern-benchmark-datasets") {

      implicit val availableBenchmarksRead: scopt.Read[AvailableBenchmarks.Value] =
        scopt.Read.reads(AvailableBenchmarks withName _)

      implicit val betterFilesRead: scopt.Read[File] =
        scopt.Read.reads(File.apply(_))

      head("joern-benchmarks-datasets", ManifestVersionProvider().getVersion)

      note("A benchmark downloader tool for Joern benchmarks")
      help('h', "help")
      version("version").text("Prints the version")

      arg[AvailableBenchmarks.Value]("benchmark")
        .text(s"The benchmark to download. Available [${AvailableBenchmarks.values.mkString(",")}]")
        .required()
        .action((x, c) => c.copy(benchmark = x))
      opt[File]('d', "dataset-dir")
        .text("The dataset directory where benchmarks will be downloaded to. Default is `./workspace`.")
        .action { (x, c) =>
          x.createDirectoryIfNotExists(createParents = true)
          c.copy(datasetDir = x)
        }
    }

}
