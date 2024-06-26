package io.joern.benchmarks.datasets.runner

import better.files.File
import com.github.sh4869.semver_parser.{Range, SemVer}
import io.joern.benchmarks.*
import org.slf4j.LoggerFactory
import upickle.default.*

import java.net.{URI, URL}
import scala.util.{Failure, Success, Try, Using}

class IchnaeaDownloader(datasetDir: File) extends DatasetDownloader(datasetDir) with MultiFileDownloader {

  private val logger = LoggerFactory.getLogger(getClass)

  override val benchmarkName = s"Ichnaea"

  private val packageNameAndVersion: Map[String, String] = Map(
    "chook-growl-reporter" -> "0.0.1",
    "cocos-utils"          -> "1.0.0",
    "gm"                   -> "1.20.0",
    "fish"                 -> "0.0.0",
    "git2json"             -> "0.0.1",
    "growl"                -> "1.9.2",
    "libnotify"            -> "1.0.3",
    "m-log"                -> "0.0.1",
    "mixin-pro"            -> "0.6.6",
    "modulify"             -> "0.1.0-1",
    "mongo-parse"          -> "1.0.5",
    "mongoosemask"         -> "0.0.6",
    "mongoosify"           -> "0.0.3",
    "node-os-utils"        -> "1.0.7",
    "node-wos"             -> "0.2.3",
    "office-converter"     -> "1.0.2",
    "os-uptime"            -> "2.0.1",
    "osenv"                -> "0.1.5",
    "pidusage"             -> "1.1.4",
    "pomelo-monitor"       -> "0.3.7",
    "system-locale"        -> "0.1.0",
    "systeminformation"    -> "3.42.2"
  )

  override protected val benchmarkUrls: Map[String, URL] = packageNameAndVersion.flatMap {
    case (packageName, version) =>
      parsePackageArtifactUrl(createNpmJsLookup(packageName, version)) match {
        case Success(distUrl) => Option(packageName -> distUrl)
        case Failure(exception) =>
          logger.error(s"Unable to determine module artifact for $packageName@$version", exception)
          None
      }
  }
  override protected val benchmarkDirName: String = "ichnaea"
  override protected val benchmarkBaseDir: File   = datasetDir / benchmarkDirName

  private def createNpmJsLookup(packageName: String, version: String): URL = URI(
    s"https://registry.npmjs.com/$packageName/$version"
  ).toURL

  private def parsePackageArtifactUrl(registryUrl: URL): Try[URL] = Try {
    Using.resource(registryUrl.openStream()) { is =>
      read[NPMRegistryResponse](ujson.Readable.fromByteArray(is.readAllBytes())).dist.tarball
    }
  }

  override def initialize(): Try[File] = Try {
    val downloadedDir = downloadBenchmarkAndUnarchive(CompressionTypes.TGZ) match {
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

implicit val urlRw: ReadWriter[URL] = readwriter[ujson.Value]
  .bimap[URL](
    x => ujson.Str(x.toString),
    {
      case json @ (j: ujson.Str) => URI(json.str).toURL
      case x                     => throw RuntimeException(s"Unexpected value type for URL strings: ${x.getClass}")
    }
  )

case class NPMRegistryResponse(dist: NPMDistBody) derives ReadWriter

case class NPMDistBody(tarball: URL) derives ReadWriter
