package io.joern.benchmarks.datasets.runner

import better.files.File
import io.joern.benchmarks.*
import org.slf4j.LoggerFactory
import upickle.default.*

import java.net.{URI, URL}
import scala.util.{Failure, Success, Try}

class ThoratDownloader(datasetDir: File) extends DatasetDownloader(datasetDir) with SingleFileDownloader {

  private val logger = LoggerFactory.getLogger(getClass)

  private val version        = "1.0.1"
  override val benchmarkName = s"Thorat Python v$version"

  override protected val benchmarkUrl: URL = URI(
    s"https://github.com/DavidBakerEffendi/thorat/archive/refs/tags/v$version.zip"
  ).toURL
  override protected val benchmarkFileName: String = s"thorat-$version"
  override protected val benchmarkBaseDir: File    = datasetDir / benchmarkFileName

  override def initialize(): Try[File] = Try {
    val outputFile = File(s"${datasetDir.pathAsString}/thorat.zip")

    if !outputFile.exists then
      downloadBenchmarkAndUnarchive(CompressionTypes.ZIP)
      compressBenchmark(benchmarkBaseDir, Option(File(s"${datasetDir.pathAsString}/thorat.zip")))
    else outputFile
  }

  override def run(): Unit = Try {
    initialize() match {
      case Failure(exception) =>
        logger.error(s"Unable to initialize benchmark '$getClass'", exception)
      case Success(benchmarkDir) =>
    }
  }
}
