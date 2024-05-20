package io.joern.benchmarks.datasets

import better.files.File
import org.slf4j.LoggerFactory
import scopt.OptionParser

import scala.util.{Failure, Success}

/** Example program that makes use of Joern as a library */
object Main {

  private val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    optionParser.parse(args, BenchmarkDatasetConfig()).map(BenchmarkDataset(_)).foreach(_.evaluate())
  }

  private val optionParser: OptionParser[BenchmarkDatasetConfig] = new OptionParser[BenchmarkDatasetConfig]("joern-benchmark") {

    implicit val availableBenchmarksRead: scopt.Read[AvailableBenchmarks.Value] =
      scopt.Read.reads(AvailableBenchmarks withName _)

    implicit val outputFormatRead: scopt.Read[OutputFormat.Value] =
      scopt.Read.reads(OutputFormat withName _)

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
      .text("The dataset directory where benchmarks will be initialized and executed. Default is `./workspace`.")
      .action { (x, c) =>
        x.createDirectoryIfNotExists(createParents = true)
        c.copy(datasetDir = x)
      }
  }

}
