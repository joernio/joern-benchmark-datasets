package io.joern.benchmarks.datasets.runner

import better.files.File
import io.joern.benchmarks.*
import io.joern.benchmarks.datasets.JavaCpgTypes
import org.slf4j.LoggerFactory

import java.net.{URI, URL}
import scala.util.{Failure, Success, Try}

class OWASPJavaDownloader(datasetDir: File, cpgCreatorType: JavaCpgTypes.Value)
    extends DatasetDownloader(datasetDir)
    with SingleFileDownloader {

  private val logger = LoggerFactory.getLogger(getClass)

  override val benchmarkName = s"OWASP Java v1.2"

  override protected val benchmarkUrl: URL = URI(
    "https://github.com/OWASP-Benchmark/BenchmarkJava/archive/refs/tags/1.2beta.zip"
  ).toURL
  override protected val benchmarkFileName: String = "BenchmarkJava-1.2beta"
  override protected val benchmarkBaseDir: File    = datasetDir / benchmarkFileName

  private val apacheJdo = URI("https://repo1.maven.org/maven2/javax/jdo/jdo-api/3.1/jdo-api-3.1.jar").toURL

  override def initialize(): Try[File] = Try {
    downloadBenchmarkAndUnarchive(CompressionTypes.ZIP)
    compressBenchmark(
      benchmarkBaseDir,
      Option(File(s"${datasetDir.pathAsString}/OWASP-BenchmarkJava-$cpgCreatorType.zip"))
    )
  }

  override def run(): Unit = {
    initialize() match {
      case Failure(exception) =>
        logger.error(s"Unable to initialize benchmark '$getClass'", exception)
      case Success(benchmarkDir) =>
    }
  }
}
