package io.joern.benchmarks.datasets.runner

import better.files.File
import io.joern.benchmarks.*
import io.joern.benchmarks.datasets.JavaCpgTypes
import org.slf4j.LoggerFactory

import java.net.{URI, URL}
import scala.util.{Failure, Success, Try}

class SecuribenchMicroJsDownloader(datasetDir: File) extends DatasetDownloader(datasetDir) with SingleFileDownloader {

  private val logger = LoggerFactory.getLogger(getClass)

  override val benchmarkName = s"securibench-micro.js v1.0.2"

  override protected val benchmarkUrl: URL = URI(
    "https://github.com/DavidBakerEffendi/securibench-micro.js/archive/refs/tags/v1.0.2.zip"
  ).toURL
  override protected val benchmarkFileName: String = "securibench-micro.js-1.0.2"
  override protected val benchmarkBaseDir: File    = datasetDir / benchmarkFileName

  override def initialize(): Try[File] = {
    val target = datasetDir / "securibench-micro-js.zip"
    if !target.exists then downloadFile(benchmarkUrl, datasetDir / "securibench-micro-js.zip")
    else Success(target)
  }

  override def run(): Unit = {
    initialize() match {
      case Failure(exception) =>
        logger.error(s"Unable to initialize benchmark '$getClass'", exception)
      case Success(_) =>
    }
  }
}
