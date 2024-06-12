package io.joern.benchmarks.datasets.runner

import better.files.File
import io.joern.benchmarks.*
import io.joern.benchmarks.datasets.JavaCpgTypes
import io.joern.x2cpg.utils.ExternalCommand
import org.slf4j.LoggerFactory

import java.net.{URI, URL}
import scala.util.{Failure, Success, Try}

class SecuribenchMicroDownloader(datasetDir: File, cpgCreatorType: JavaCpgTypes.Value)
    extends DatasetDownloader(datasetDir)
    with SingleFileDownloader {

  private val logger = LoggerFactory.getLogger(getClass)

  override val benchmarkName = s"Securibench Micro v1.08"

  override protected val benchmarkUrl: URL = URI(
    "https://github.com/too4words/securibench-micro/archive/6a5a724.zip"
  ).toURL
  override protected val benchmarkFileName: String = "securibench-micro-6a5a72488ea830d99f9464fc1f0562c4f864214b"
  override protected val benchmarkBaseDir: File    = datasetDir / benchmarkFileName

  private val apacheJdo = URI("https://repo1.maven.org/maven2/javax/jdo/jdo-api/3.1/jdo-api-3.1.jar").toURL

  override def initialize(): Try[File] = Try {
    downloadBenchmarkAndUnarchive(CompressionTypes.ZIP)
    downloadFile(apacheJdo, benchmarkBaseDir / "lib" / "jdo-api-3.1.jar")
    if (
      cpgCreatorType == JavaCpgTypes.JAVA && (benchmarkBaseDir / "classes")
        .walk()
        .count(_.`extension`.contains(".class")) < 1
    ) {
      val sourceFiles = (benchmarkBaseDir / "src")
        .walk()
        .filter(f => f.isRegularFile && f.`extension`.contains(".java"))
        .map(f => f.pathAsString.stripPrefix(s"${benchmarkBaseDir.pathAsString}${java.io.File.separator}"))
        .mkString(" ")
      val command =
        Seq(
          "javac",
          "-cp",
          "'.:lib/cos.jar:lib/j2ee.jar:lib/java2html.jar:lib/jdo-api-3.1.jar;'",
          "-d",
          "classes",
          sourceFiles
        ).mkString(" ")
      ExternalCommand.run(command, benchmarkBaseDir.pathAsString) match {
        case Failure(exception) =>
          logger.error(s"Exception encountered while compiling source code with: '$command'")
          throw exception
        case Success(_) => logger.info(s"Successfully compiled $benchmarkName")
      }
    }

    compressBenchmark(
      benchmarkBaseDir,
      Option(File(s"${datasetDir.pathAsString}/securibench-micro-${cpgCreatorType.toString}.zip"))
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
