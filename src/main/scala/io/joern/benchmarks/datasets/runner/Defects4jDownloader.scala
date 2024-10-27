package io.joern.benchmarks.datasets.runner

import better.files.File
import org.slf4j.LoggerFactory

import java.io.IOException
import java.net.{URI, URL}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.*
import scala.jdk.CollectionConverters.IteratorHasAsScala
import scala.util.{Failure, Success, Try}

class Defects4jDownloader(datasetDir: File) extends DatasetDownloader(datasetDir) with MultiFileDownloader {

  private val logger = LoggerFactory.getLogger(getClass)

  override val benchmarkName = s"Defects4j"

  private val packageDetails: Seq[(String, String)] = Seq(
    "Chart" -> "https://repo1.maven.org/maven2/org/jfree/jfreechart/1.5.5/jfreechart-1.5.5.jar",
    "Cli"   -> "https://repo1.maven.org/maven2/commons-cli/commons-cli/1.8.0/commons-cli-1.8.0.jar",
    "Closure" -> "https://repo1.maven.org/maven2/com/google/javascript/closure-compiler/v20240317/closure-compiler-v20240317.jar",
    "Codec" -> "https://repo1.maven.org/maven2/commons-codec/commons-codec/1.17.0/commons-codec-1.17.0.jar",
    "Collections" -> "https://repo1.maven.org/maven2/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4.jar",
    "Compress" -> "https://repo1.maven.org/maven2/org/apache/commons/commons-compress/1.26.2/commons-compress-1.26.2.jar",
    "Csv"  -> "https://repo1.maven.org/maven2/org/apache/commons/commons-csv/1.11.0/commons-csv-1.11.0.jar",
    "Gson" -> "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.11.0/gson-2.11.0.jar",
    "JacksonCore" -> "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.17.2/jackson-core-2.17.2.jar",
    "JacksonDatabind" -> "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.17.2/jackson-databind-2.17.2.jar",
    "JacksonXml" -> "https://repo1.maven.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformat-xml/2.17.2/jackson-dataformat-xml-2.17.2.jar",
    "Jsoup"   -> "https://repo1.maven.org/maven2/org/jsoup/jsoup/1.18.1/jsoup-1.18.1.jar",
    "JxPath"  -> "https://repo1.maven.org/maven2/commons-jxpath/commons-jxpath/1.3/commons-jxpath-1.3.jar",
    "Lang"    -> "https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.14.0/commons-lang3-3.14.0.jar",
    "Math"    -> "https://repo1.maven.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar",
    "Mockito" -> "https://repo1.maven.org/maven2/org/mockito/mockito-core/5.12.0/mockito-core-5.12.0.jar",
    "Time"    -> "https://repo1.maven.org/maven2/joda-time/joda-time/2.12.7/joda-time-2.12.7.jar"
  )

  /** The URL to the archive.
    */
  override protected val benchmarkUrls: Map[String, URL] = packageDetails.map { case (name, urlString) =>
    name -> URI(urlString).toURL
  }.toMap

  /** The name of the benchmark directory to download all benchmark components to.
    */
  override protected val benchmarkDirName: String = "defects4j"

  /** The name of the benchmark directory.
    */
  override protected val benchmarkBaseDir: File = datasetDir / benchmarkDirName

  override def initialize(): Try[File] = Try {
    val downloadedDir = downloadBenchmarkAndUnarchive(CompressionTypes.ZIP) match {
      case Success(dir) =>
        dir
      case Failure(e) => throw e
    }

    compressBenchmark(downloadedDir)
  }

  override def run(): Unit = {
    initialize() match {
      case Failure(exception) =>
        logger.error(s"Unable to initialize benchmark '$getClass'", exception)
      case Success(benchmarkDir) =>
        logger.info(s"Finished downloading benchmark `$getClass``")
    }
  }

}
