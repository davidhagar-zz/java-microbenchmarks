java-microbenchmarks
====================

Microbenchmarks and playing around with JMH

Run this with:
```bash
mvn package
java -jar target/microbenchmarks.jar ".*ListBenchmarks.*(listAdd).*" -wi 2 -i 5 -f 1 -t 3
```

Breaking that down: 

|                                  Fragment                            |                                      Meaning                                     |
|----------------------------------------------------------------------|----------------------------------------------------------------------------------|
|java -jar target/microbenchmarks.jar ".*ListBenchmarks.*(listAdd).*"  |run the benchmarks in ListBenchmarks that have method names beginning with listAdd|
|-wi 2                                                                 |means 2 warm up iterations to overcome jvm startup inertia                        |
|-i  5                                                                 |means run 5 actual iterations                                                     |
|-f  1                                                                 |means run 1 fork                                                                  | 
|-t  3                                                                 |means run the benchmarks in 3 threads                                             |
 
and get results like:

```bash
# Run progress: 75.00% complete, ETA 00:00:08
# Warmup: 2 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Threads: 3 threads, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.davidhagar.microbenchmarks.ListBenchmarks.listAddLinkedListWithPointer
# VM invoker: /Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home/jre/bin/java
# VM options: <none>
# Fork: 1 of 1
# Warmup Iteration   1: 371507.392 ns/op
# Warmup Iteration   2: 372918.653 ns/op
Iteration   1: 370407.611 ns/op
Iteration   2: 369963.860 ns/op
Iteration   3: 373438.190 ns/op
Iteration   4: 367462.735 ns/op
Iteration   5: 377556.485 ns/op

Result: 371765.776 ±(99.9%) 14905.642 ns/op [Average]
  Statistics: (min, avg, max) = (367462.735, 371765.776, 377556.485), stdev = 3870.948
  Confidence interval (99.9%): [356860.134, 386671.418]

Benchmark                                               Mode   Samples        Score  Score error    Units
c.d.c.Microbenchmarks.listAddArrayListWithIterator      avgt         5      956.445       44.848    ns/op
c.d.c.Microbenchmarks.listAddArrayListWithPointer       avgt         5      963.061       91.788    ns/op
c.d.c.Microbenchmarks.listAddLinkedListWithIterator     avgt         5     3122.704     1249.198    ns/op
c.d.c.Microbenchmarks.listAddLinkedListWithPointer      avgt         5   362507.678    22396.165    ns/op
```
