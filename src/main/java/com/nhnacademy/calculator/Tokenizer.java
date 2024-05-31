package com.nhnacademy.calculator;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nhnacademy.number.XInteger;
import com.nhnacademy.number.XRational;

public class Tokenizer {

    private List<Token> result = null;
    private Map<String, Integer> operators = Stream.of(
            new AbstractMap.SimpleEntry<>("+", 2),
            new AbstractMap.SimpleEntry<>("-", 2),
            new AbstractMap.SimpleEntry<>("*", 1),
            new AbstractMap.SimpleEntry<>("/", 1),
            new AbstractMap.SimpleEntry<>("(", 0),
            new AbstractMap.SimpleEntry<>(")", 0))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private String operatorPattern;
    private String tokenPattern;

    public Tokenizer() {
        updatePattern();
    }

    public void addOperator(String operator, int priority) {
        operators.put(operator, priority);

        updatePattern();
    }

    private void updatePattern() {
        StringBuilder builder = new StringBuilder();

        operators.forEach((o, p) -> {
            if (builder.length() != 0) {
                builder.append("|");
            }

            builder.append("\\" + o);
        });

        operatorPattern = builder.toString();
        tokenPattern = operatorPattern + "|" + XInteger.PATTERN + "|" + XRational.PATTERN;
    }

    public List<Token> getResult() {
        return result;
    }

    public List<Token> evaluate(String expression) throws IllegalArgumentException {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile(tokenPattern).matcher(expression);

        while (matcher.find()) {
            String token = matcher.group().trim().replaceAll("\\s+", "");
            if (token.isEmpty()) {
                continue;
            }

            if (token.matches("[-+]")) {
                if (tokens.isEmpty()) {
                    matcher.find();
                    String nextToken = matcher.group().trim().replaceAll("\\s+", "");
                    if (nextToken.matches(XInteger.PATTERN)) {
                        token = token + nextToken;
                        tokens.add(new IntegerToken(token));
                    } else {
                        throw new IllegalArgumentException("Invalid expression: consecutive '-' detected.");
                    }
                } else {
                    Token prevToken = tokens.get(tokens.size() - 1);
                    if ((prevToken instanceof NumberToken) || ")".equals(prevToken.toString())) {
                        tokens.add(new OperatorToken(token, 2));
                    } else {
                        matcher.find();
                        String nextToken = matcher.group().trim().replaceAll("\\s+", "");
                        if (nextToken.matches(XInteger.PATTERN)) {
                            token = "-" + nextToken;
                            tokens.add(new IntegerToken(token));
                        } else {
                            throw new IllegalArgumentException("Invalid expression: consecutive '-' detected.");
                        }
                    }
                }
            } else {
                if (token.matches(XInteger.PATTERN)) {
                    tokens.add(new IntegerToken(token));
                } else if (token.matches(XRational.PATTERN)) {
                    tokens.add(new RationalToken(token));
                } else if (token.matches(operatorPattern)) {
                    tokens.add(new OperatorToken(token, operators.get(token)));
                } else {
                    throw new IllegalArgumentException("Invalid expression: not supported token detected.");
                }
            }
        }

        result = tokens;

        return tokens;
    }

    @Override
    public String toString() {
        if (result != null) {
            return Arrays.toString(result.toArray());
        } else {
            return "";
        }
    }
}
