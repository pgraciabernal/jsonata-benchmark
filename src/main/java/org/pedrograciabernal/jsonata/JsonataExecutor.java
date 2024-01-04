package org.pedrograciabernal.jsonata;

import com.api.jsonata4java.expressions.EvaluateException;
import com.api.jsonata4java.expressions.Expressions;
import com.api.jsonata4java.expressions.ParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.dashjoin.jsonata.Jsonata.jsonata;

public class JsonataExecutor {

    public static boolean Jsonata_execution(JsonNode jsonContent, String JsonataExpression) throws ParseException, IOException, EvaluateException {
        ObjectMapper mapper = new ObjectMapper();
        Expressions expr = Expressions.parse(JsonataExpression);
        return expr.evaluate(jsonContent).asBoolean();
    }

    public static boolean JsonataDashjoin_execution(Object jsonObject, String JsonataExpression) throws ParseException, IOException, EvaluateException {
        var expression = jsonata(JsonataExpression);
        return (boolean) expression.evaluate(jsonObject);
    }
}
