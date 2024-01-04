# jsonata-benchmark

This is a simple project to represent a benchmark between two ported libraries for [JSONata](https://jsonata.org/) implementation.

Those libraries are:
- IBM - JSON4Java: https://github.com/IBM/JSONata4Java
- Dashjoin - jsonata-java: https://github.com/dashjoin/jsonata-java

Uses public REST example from REST countries page filter for a few attributes: https://restcountries.com/v3.1/region/europe?fields=name,capital,population,subregion

Execution of benchmarks has been done using [JMH from OpenJDK](https://github.com/openjdk/jmh). This is a code tool used to benchmark small parts of code.