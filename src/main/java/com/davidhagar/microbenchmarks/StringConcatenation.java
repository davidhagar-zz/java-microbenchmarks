package com.davidhagar.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class StringConcatenation {
  String[] strings;
  int stringsSize = 1024;
  Random random = new Random();

  @Setup
  public void setupStrings() {
    strings = new String[stringsSize];
    for (int i = 0; i < stringsSize; i++) {
      strings[i] = UUID.randomUUID().toString();
    }
  }

  @Benchmark
  public String formatStringUsingStringFormat() {
    return (String.format("foo %s %s %s %s %s %s %s %s", strings[random.nextInt(1024)], strings[random.nextInt(1024)], strings[random.nextInt(1024)], strings[random.nextInt(1024)], strings[random.nextInt(1024)], strings[random.nextInt(1024)], strings[random.nextInt(1024)], strings[random.nextInt(1024)]));
  }

  @Benchmark
  public String formatStringUsingStringConcatenation() {
    return ("foo "
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]
        + strings[random.nextInt(1024)]);
  }

  @Benchmark
  public String formatStringUsingStringBuilder() {
    StringBuilder builder = new StringBuilder(400);
    builder.append("foo");
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    builder.append(strings[random.nextInt(1024)]);
    return builder.toString();
  }



  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(".*" + StringConcatenation.class.getSimpleName() + ".*")
        .warmupIterations(5)
        .measurementIterations(5)
        .forks(1)
        .build();

    new Runner(opt).run();
  }
}
