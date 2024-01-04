package org.pedrograciabernal.jsonata;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class Jsonata {
    public static void main(String[] args) throws RunnerException {
            Options options = new OptionsBuilder()
                    .include(JsonataBenchmark.class.getSimpleName())
                    .timeUnit(TimeUnit.MILLISECONDS)
                    .measurementIterations(3)
                    .mode(Mode.Throughput) //Calculate number of operations in a time unit.
                    .mode(Mode.AverageTime) //Calculate an average running time per operation
                    .warmupIterations(1)
                    .threads(Runtime.getRuntime().availableProcessors())
                    .forks(1)
                    .shouldFailOnError(true)
                    .shouldDoGC(true)
                    .build();

            new Runner(options).run();
        }
    }

