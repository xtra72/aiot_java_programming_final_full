package com.nhnacademy.calculator;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SyntaxTreeParser {

    private Map<String, BinaryOperator<Node>> operators = Stream.of(
            new AbstractMap.SimpleEntry<String, BinaryOperator<Node>>("+", PlusNode::new),
            new AbstractMap.SimpleEntry<String, BinaryOperator<Node>>("-", MinusNode::new),
            new AbstractMap.SimpleEntry<String, BinaryOperator<Node>>("*", MultiplyNode::new),
            new AbstractMap.SimpleEntry<String, BinaryOperator<Node>>("/", DivideNode::new))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public void addOperator(String operator, BinaryOperator<Node> nodeCreator) {
        operators.put(operator, nodeCreator);
    }

    public Node parse(List<Token> tokens) {
        return parseExpression(tokens);
    }

    private Node parseExpression(List<Token> tokens) {
        Node node = parseTerm(tokens);
        while (!tokens.isEmpty()
                && (tokens.get(0) instanceof OperatorToken)
                && (tokens.get(0).getPriority() == 2)) {
            OperatorToken operator = (OperatorToken) tokens.remove(0);
            Node right = parseTerm(tokens);
            node = operators.get(operator.toString()).apply(node, right);
        }
        return node;
    }

    private Node parseTerm(List<Token> tokens) {
        Node node = parseFactor(tokens);
        while (!tokens.isEmpty()
                && (tokens.get(0) instanceof OperatorToken)
                && (tokens.get(0).getPriority() == 1)) {
            OperatorToken operator = (OperatorToken) tokens.remove(0);
            Node right = parseFactor(tokens);
            node = operators.get(operator.toString()).apply(node, right);
        }
        return node;
    }

    private Node parseFactor(List<Token> tokens) {
        Token token = tokens.remove(0);
        if (token instanceof NumberToken) {
            return new NumberNode(((NumberToken) token).toNumber());
        } else if ((token instanceof OperatorToken) && token.equals("(")) {
            Node node = parseExpression(tokens);
            tokens.remove(0);
            return node;
        }
        throw new IllegalArgumentException("Unexpected token: " + token);
    }
}
