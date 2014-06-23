java-microbenchmarks
====================

Microbenchmarks and playing around with JMH

Run this with:
```bash
mvn package
java -jar target/microbenchmarks.jar ".*ListBenchmarks.*(listAdd).*" -wi 2 -i 5 -f 1 -t 3
```
