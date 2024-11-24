Joern Benchmarks
================

A repository for building snapshots of datasets used by `joern-benchmarks`.

## Usage

```bash
sbt stage
./joern-benchmarks-datasets --help
joern-benchmark v0.0.1
Usage: joern-benchmark-datasets [options] benchmark

A benchmark downloader tool for Joern benchmarks
  -h, --help
  --version                Prints the version
  benchmark                The benchmark to download. Available [ALL,SECURIBENCH_MICRO_SRC,SECURIBENCH_MICRO_JAVA,SECURIBENCH_MICRO_JS,ICHNAEA,THORAT,BUGS_IN_PY,DEFECTS4J]
  -d, --dataset-dir <value>
                           The dataset directory where benchmarks will be downloaded to. Default is `./workspace`.
```
