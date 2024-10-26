package io.joern.benchmarks.datasets.runner

import better.files.File
import org.slf4j.LoggerFactory

import java.net.{URI, URL}
import scala.util.{Failure, Success, Try}

class BugsInPyDownloader(datasetDir: File) extends DatasetDownloader(datasetDir) with MultiFileDownloader {

  private val logger = LoggerFactory.getLogger(getClass)

  override val benchmarkName = s"BugsInPy"

  private case class PackageDetails(name: String, version: String, urlString: String) {
    def url: URL = URI(urlString).toURL
  }

  private val packageDetails: Seq[PackageDetails] = Seq(
    PackageDetails(
      "ansible",
      "10.5.0",
      "https://files.pythonhosted.org/packages/d7/23/ae30b280ebad1f19fa012c0410aaf7d50cd741a5786bd60a2ecba42d2cd4/ansible-10.5.0.tar.gz"
    ),
    PackageDetails(
      "cookiecutter",
      "2.6.0",
      "https://files.pythonhosted.org/packages/52/17/9f2cd228eb949a91915acd38d3eecdc9d8893dde353b603f0db7e9f6be55/cookiecutter-2.6.0.tar.gz"
    ),
    PackageDetails(
      "PySnooper",
      "1.2.1",
      "https://files.pythonhosted.org/packages/64/2f/1cec82aaff770c953717cbbdad26d30504689f4d492332dd816296fd9029/PySnooper-1.2.1.tar.gz"
    ),
    PackageDetails(
      "spacy",
      "3.8.2",
      "https://files.pythonhosted.org/packages/07/53/536941af8fbb5cb10a778f0dbd2a17b0f13e7ebfc11e67b154be60508fdf/spacy-3.8.2.tar.gz"
    ),
    PackageDetails(
      "sanic",
      "24.6.0",
      "https://files.pythonhosted.org/packages/37/00/d252369495fe3bc876fb7c269ec204800b6f66fdd9083d4dd29ee7539130/sanic-24.6.0.tar.gz"
    ),
    PackageDetails(
      "httpie",
      "3.2.3",
      "https://files.pythonhosted.org/packages/7a/6a/20c0b92027f1e6cdb4068a662833d882f138ec781cbe26f4d6fc5f10313c/httpie-3.2.3.tar.gz"
    ),
    PackageDetails(
      "keras",
      "3.6.0",
      "https://files.pythonhosted.org/packages/47/22/448401abc7deaee90592c48c5dcc27ad93518b605655bef7ec5eb9544fe5/keras-3.6.0.tar.gz"
    ),
    PackageDetails(
      "matplotlib",
      "3.9.2",
      "https://files.pythonhosted.org/packages/9e/d8/3d7f706c69e024d4287c1110d74f7dabac91d9843b99eadc90de9efc8869/matplotlib-3.9.2.tar.gz"
    ),
    PackageDetails(
      "thefuck",
      "3.32",
      "https://files.pythonhosted.org/packages/ac/d0/0c256afd3ba1d05882154d16aa0685018f21c60a6769a496558da7d9d8f1/thefuck-3.32.tar.gz"
    ),
    PackageDetails(
      "pandas",
      "2.2.3",
      "https://files.pythonhosted.org/packages/9c/d6/9f8431bacc2e19dca897724cd097b1bb224a6ad5433784a44b587c7c13af/pandas-2.2.3.tar.gz"
    ),
    PackageDetails(
      "black",
      "24.10.0",
      "https://files.pythonhosted.org/packages/d8/0d/cc2fb42b8c50d80143221515dd7e4766995bd07c56c9a3ed30baf080b6dc/black-24.10.0.tar.gz"
    ),
    PackageDetails(
      "Scrapy",
      "2.11.2",
      "https://files.pythonhosted.org/packages/f2/1f/5524416a64c030fbe18caeba079e7176836b281bf9eb50b79efdf8015063/scrapy-2.11.2.tar.gz"
    ),
    PackageDetails(
      "luigi",
      "3.5.2",
      "https://files.pythonhosted.org/packages/47/bf/40a9ea8860a0d6387fe974d10d93b539fb5e477fc590e5d7673d2322b42d/luigi-3.5.2.tar.gz"
    ),
    PackageDetails(
      "fastapi",
      "0.115.3",
      "https://files.pythonhosted.org/packages/a9/ce/b64ce344d7b13c0768dc5b131a69d52f57202eb85839408a7637ca0dd7e2/fastapi-0.115.3.tar.gz"
    ),
    PackageDetails(
      "tornado",
      "6.4.1",
      "https://files.pythonhosted.org/packages/ee/66/398ac7167f1c7835406888a386f6d0d26ee5dbf197d8a571300be57662d3/tornado-6.4.1.tar.gz"
    ),
    PackageDetails(
      "tqdm",
      "4.66.5",
      "https://files.pythonhosted.org/packages/58/83/6ba9844a41128c62e810fddddd72473201f3eacde02046066142a2d96cc5/tqdm-4.66.5.tar.gz"
    ),
    PackageDetails(
      "youtube-dl",
      "2021.12.17",
      "https://files.pythonhosted.org/packages/01/4f/ab0d0806f4d818168d0ec833df14078c9d1ddddb5c42fa7bfb6f15ecbfa7/youtube_dl-2021.12.17.tar.gz"
    )
  )

  /** The URL to the archive.
    */
  override protected val benchmarkUrls: Map[String, URL] = packageDetails.map {
    case pack @ PackageDetails(name, _, _) =>
      name -> pack.url
  }.toMap

  /** The name of the benchmark directory to download all benchmark components to.
    */
  override protected val benchmarkDirName: String = "bugs_in_py"

  /** The name of the benchmark directory.
    */
  override protected val benchmarkBaseDir: File = datasetDir / benchmarkDirName

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
