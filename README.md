Joern Benchmarks
================

A repository for running Joern against known benchmarks.

## Usage

```bash
sbt stage
./joern-benchmarks-datasets --help
joern-benchmark v0.0.1
Usage: joern-benchmark [options] benchmark

A benchmarking suite for Joern
  -h, --help
  --version                Prints the version
  benchmark                The benchmark to run. Available [ALL,OWASP_JAVASRC,OWASP_JAVA,SECURIBENCH_MICRO_JAVASRC,SECURIBENCH_MICRO_JAVA]
  -d, --dataset-dir <value>
                           The dataset directory where benchmarks will be downloaded to. Default is `./workspace`.
```

## Benchmarks

The benchmark naming convention of `<BENCHMARK>_<FRONTEND>`, e.g. `OWASP_JAVA` runs `OWASP` using the `jimple2cpg`
frontend (JVM bytecode).

| Benchmark                                                             | Status | Enabled Frontends |
|-----------------------------------------------------------------------|--------|-------------------|
| [`OWASP`](https://owasp.org/www-project-benchmark/)                   | WIP    | `JAVASRC`         |
| [`SECURIBENCH_MICRO`](https://github.com/too4words/securibench-micro) | WIP    | `JAVASRC` `JAVA`  |
| [`ICHNAEA`](https://www.franktip.org/pubs/tse2020.pdf)                | WIP    | `JSSRC`           |
