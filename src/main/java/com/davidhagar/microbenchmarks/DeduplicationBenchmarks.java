package com.davidhagar.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DeduplicationBenchmarks {

  @State(Scope.Thread)
  public static class StringLists {

    private final Random random = new Random();
    private int largeListSize = 1000;
    private int smallListSize = 10;
    private int byteSize = 100;
    private List<String> largeStringList;
    private List<String> smallStringList;

    public StringLists() {
      this.largeStringList = new ArrayList<>(largeListSize);
      this.smallStringList = new ArrayList<>(largeListSize);
      byte[] bytes = new byte[byteSize];
      for (int i = 0; i < largeListSize; i++) {
        random.nextBytes(bytes);
        largeStringList.add(new String(bytes, StandardCharsets.UTF_8));
      }
      for (int i = 0; i< smallListSize; i++) {
        random.nextBytes(bytes);
        smallStringList.add(new String(bytes, StandardCharsets.UTF_8));
      }
    }
  }

  @Benchmark
  @BenchmarkMode({Mode.Throughput, Mode.SampleTime})
  @OperationsPerInvocation(100000)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public boolean useHashSetToDeduplicateLargeListThatHasDuplicates(StringLists state) {
    Set<String> possibleDuplicates = new HashSet<>(state.largeStringList);
    return state.largeStringList.size() == possibleDuplicates.size();
  }

  @Benchmark
  @BenchmarkMode({Mode.Throughput, Mode.SampleTime})
  @OperationsPerInvocation(100000)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public boolean useHashSetToDeduplicateSmallListThatHasDuplicates(StringLists state) {
    Set<String> possibleDuplicates = new HashSet<>(state.smallStringList);
    return state.smallStringList.size() == possibleDuplicates.size();
  }

  @Benchmark
  @BenchmarkMode({Mode.Throughput, Mode.SampleTime})
  @OperationsPerInvocation(100000)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public boolean useSortingToDeduplicateLargeListThatHasDuplicates(StringLists state) {
    Collections.sort(state.largeStringList);
    for (int i = 0; i < state.largeStringList.size() - 1; i++) {
      if (state.largeStringList.get(i).equals(state.largeStringList.get(i+1))) {
        return true;
      }
    }
    return false;
  }

  @Benchmark
  @BenchmarkMode({Mode.Throughput, Mode.SampleTime})
  @OperationsPerInvocation(100000)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public boolean useSortingToDeduplicateSmallListThatHasDuplicates(StringLists state) {
    Collections.sort(state.smallStringList);
    for (int i = 0; i < state.smallStringList.size() - 1; i++) {
      if (state.smallStringList.get(i).equals(state.smallStringList.get(i+1))) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(".*" + DeduplicationBenchmarks.class.getSimpleName() + ".*")
        .warmupIterations(5)
        .measurementIterations(5)
        .forks(1)
        .build();

    new Runner(opt).run();
  }
}
