package io.joern.benchmarks.datasets.runner

import better.files.File

import org.slf4j.{Logger, LoggerFactory}
import scala.util.Try

/** A process that downloads a benchmark.
  */
trait DatasetDownloader(protected val datasetDir: File) {

  protected val logger: Logger = LoggerFactory.getLogger(getClass)

  val benchmarkName: String

  protected def initialize(): Try[File]

  def run(): Unit

}
