package org.pedrograciabernal.jsonata;

import com.api.jsonata4java.expressions.EvaluateException;
import com.api.jsonata4java.expressions.ParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.api.jsonata4java.expressions.Expressions;

import static com.dashjoin.jsonata.Jsonata.jsonata;

public class JsonataTest {

    private static String fileString;

    @BeforeAll
    public static void init() throws IOException {
        ClassLoader classLoader = JsonataTest.class.getClassLoader();
        File file = new File(classLoader.getResource("file/jsonata_file.json").getFile());
        fileString = Files.readString(Paths.get(file.getAbsolutePath()));
    }

    @Test
    public void Jsonata4Java_exist() throws ParseException, IOException, EvaluateException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj = mapper.readTree(fileString);
        Expressions expr = Expressions.parse("\"Madrid\" in capital");
        Assertions.assertTrue(expr.evaluate(jsonObj).asBoolean());
    }

    @Test
    public void Jsonata4Java_notfound() throws ParseException, IOException, EvaluateException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj = mapper.readTree(fileString);
        Expressions expr = Expressions.parse("\"Foo\" in capital");
        Assertions.assertFalse(expr.evaluate(jsonObj).asBoolean());
    }

    @Test
    public void Jsonata4Java_greaterThan() throws ParseException, IOException, EvaluateException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj = mapper.readTree(fileString);
        Assertions.assertNotNull(Expressions.parse("$[population > 50000000]"));
    }

    @Test
    public void Jsonata4Java_booleanOperator() throws ParseException, IOException, EvaluateException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj = mapper.readTree(fileString);
        Expressions expr = Expressions.parse("$[population > 50000000] " +
                "and $[$contains(subregion, \"Southern Europe\")]");
        Assertions.assertTrue(expr.evaluate(jsonObj).asBoolean());
    }

    @Test
    public void JsonataDashjoin_exist() throws ParseException, IOException, EvaluateException {
        ObjectMapper mapper = new ObjectMapper();
        var data = new ObjectMapper().readValue(fileString, Object.class);
        var expression = jsonata("\"Republic of Finland\" in $.name.official[$match($, /Republic/)]");
        Assertions.assertTrue((boolean) expression.evaluate(data));
    }

    @Test
    public void JsonataDashjoin_notfound() throws ParseException, IOException, EvaluateException {
        ObjectMapper mapper = new ObjectMapper();
        var data = new ObjectMapper().readValue(fileString, Object.class);
        var expression = jsonata("\"Republic of Foo\" in $.name.official[$match($, /Republic/)]");
        Assertions.assertFalse((boolean) expression.evaluate(data));
    }

    @Test
    public void JsonataDashjoin_booleanOperator() throws ParseException, IOException, EvaluateException {
        ObjectMapper mapper = new ObjectMapper();
        var data = new ObjectMapper().readValue(fileString, Object.class);
        var expression = jsonata("$[population > 50000000] " +
                "and $[$contains(subregion, \"Southern Europe\")]");
        Assertions.assertTrue((boolean) expression.evaluate(data));
    }

    @Test
    public void JsonataDashjoin_greaterThan() throws ParseException, IOException, EvaluateException {
        ObjectMapper mapper = new ObjectMapper();
        var data = new ObjectMapper().readValue(fileString, Object.class);
        Assertions.assertNotNull(jsonata("$[population > 50000000]"));
    }
}
