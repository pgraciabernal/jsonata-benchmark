package org.pedrograciabernal.jsonata;

import com.api.jsonata4java.expressions.EvaluateException;
import com.api.jsonata4java.expressions.ParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonataBenchmark {

    private static final String FOUND_CAPITAL = "\"Madrid\" in capital";
    private static final String FOUND_CONDITIONAL = "$[population > 50000000] " +
            "and $[$contains(subregion, \"Southern Europe\")]";

    @Benchmark
    public boolean Jsonata4Java_simpleBenchmark() throws EvaluateException, ParseException, IOException {
        return JsonataExecutor.Jsonata_execution(getJsonContent(), FOUND_CAPITAL);
    }

    @Benchmark
    public boolean JsonataDashjoin_simpleBenchmark() throws EvaluateException, ParseException, IOException {
        return JsonataExecutor.JsonataDashjoin_execution(getJsonObject(), FOUND_CAPITAL);
    }

    @Benchmark
    public boolean Jsonata4Java_conditionalBenchmark() throws EvaluateException, ParseException, IOException {
        return JsonataExecutor.Jsonata_execution(getJsonContent(), FOUND_CONDITIONAL);
    }

    @Benchmark
    public boolean JsonataDashjoin_conditionalBenchmark() throws EvaluateException, ParseException, IOException {
        return JsonataExecutor.JsonataDashjoin_execution(getJsonObject(), FOUND_CONDITIONAL);
    }

    private static JsonNode getJsonContent() throws IOException {
        ClassLoader classLoader = JsonataExecutor.class.getClassLoader();
        File file = new File(classLoader.getResource("file/jsonata_file.json").getFile());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(Files.readString(Paths.get(file.getAbsolutePath())));
    }

    private static Object getJsonObject() throws IOException {
        ClassLoader classLoader = JsonataExecutor.class.getClassLoader();
        File file = new File(classLoader.getResource("file/jsonata_file.json").getFile());
        ObjectMapper mapper = new ObjectMapper();
        return new ObjectMapper().readValue(Files.readString(Paths.get(file.getAbsolutePath())), Object.class);
    }
}
