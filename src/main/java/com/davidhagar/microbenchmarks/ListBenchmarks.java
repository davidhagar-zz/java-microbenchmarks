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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ListBenchmarks {
  int intsSize = 1025;
  Integer[] ints = new Integer[intsSize];
  LinkedList<Integer> myIntsLinkedList = new LinkedList<>();
  ArrayList<Integer> myIntsArrayList = new ArrayList<>();
  Random random = new Random();

  @Setup
  public void setupInts() {
    for (int i = 0; i < intsSize; i++) {
      ints[i] = random.nextInt();
    }
  }

  @Setup
  public void setupLists() {
    for (int i = 0; i < intsSize; i++) {
      int toAdd = random.nextInt();
      myIntsArrayList.add(toAdd);
      myIntsLinkedList.add(toAdd);
    }
  }

  @Benchmark
  public List<Integer> listInsertAtEndLinkedList() {
    LinkedList<Integer> linkedList = new LinkedList<>();
    for (Integer myInt : ints) {
      linkedList.add(myInt);
    }
    return linkedList;
  }

  @Benchmark
  public List<Integer> listInsertAtEndArrayList() {
    ArrayList<Integer> arrayList = new ArrayList<>();
    for (Integer myInt : ints) {
      arrayList.add(myInt);
    }
    return arrayList;
  }

  @Benchmark
  public List<Integer> listInsertAtEndPresizedArrayList() {
    ArrayList<Integer> arrayList = new ArrayList<>(ints.length);
    for (Integer myInt : ints) {
      arrayList.add(myInt);
    }
    return arrayList;
  }
  @Benchmark
  public List<Integer> listInsertAtFrontLinkedList() {
    LinkedList<Integer> linkedList = new LinkedList<>();
    for (Integer myInt : ints) {
      linkedList.add(0, myInt);
    }
    return linkedList;
  }

  @Benchmark
  public List<Integer> listInsertAtFrontArrayList() {
    ArrayList<Integer> arrayList = new ArrayList<>();
    for (Integer myInt : ints) {
      arrayList.add(0, myInt);
    }
    return arrayList;
  }

  @Benchmark
  public List<Integer> listInsertAtFrontPresizedArrayList() {
    ArrayList<Integer> arrayList = new ArrayList<>(ints.length);
    for (Integer myInt : ints) {
      arrayList.add(0, myInt);
    }
    return arrayList;
  }

  @Benchmark
  public List<Integer> listInsertAtMiddleLinkedList() {
    LinkedList<Integer> linkedList = new LinkedList<>();
    for (Integer myInt : ints) {
      linkedList.add(linkedList.size() / 2, myInt);
    }
    return linkedList;
  }

  @Benchmark
  public List<Integer> listInsertAtMiddleArrayList() {
    ArrayList<Integer> arrayList = new ArrayList<>();
    for (Integer myInt : ints) {
      arrayList.add(arrayList.size() / 2, myInt);
    }
    return arrayList;
  }

  @Benchmark
  public List<Integer> listInsertAtMiddlePresizedArrayList() {
    ArrayList<Integer> arrayList = new ArrayList<>(ints.length);
    for (Integer myInt : ints) {
      arrayList.add(arrayList.size() / 2, myInt);
    }
    return arrayList;
  }

  @Benchmark
  public long listAddLinkedListWithIterator() {
    long result = 0;
    for (Integer myInt : myIntsLinkedList) {
      result += myInt;
    }
    return result;
  }

  @Benchmark
  public long listAddArrayListWithIterator() {
    long result = 0;
    for (Integer myInt : myIntsArrayList) {
      result += myInt;
    }
    return result;
  }

  @Benchmark
  public long listAddLinkedListWithPointer() {
    long result = 0;
    int sizeOfList = myIntsLinkedList.size();
    for (int i = 0; i < sizeOfList; i++) {
      result += myIntsLinkedList.get(i);
    }
    return result;
  }

  @Benchmark
  public long listAddArrayListWithPointer() {
    long result = 0;
    int sizeOfList = myIntsArrayList.size();
    for (int i = 0; i < sizeOfList; i++) {
      result += myIntsArrayList.get(i);
    }
    return result;
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(".*" + ListBenchmarks.class.getSimpleName() + ".*")
        .warmupIterations(5)
        .measurementIterations(5)
        .forks(1)
        .build();

    new Runner(opt).run();
  }
}
