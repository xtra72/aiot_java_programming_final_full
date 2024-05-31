package com.nhnacademy.set2.calculator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.nhnacademy.calculator.Node;
import com.nhnacademy.calculator.SyntaxTreeParser;
import com.nhnacademy.calculator.Token;
import com.nhnacademy.calculator.Tokenizer;

@TestMethodOrder(OrderAnnotation.class)
public class Test10301_SimpleParser {
    static boolean success = false;
    static SyntaxTreeParser stp = new SyntaxTreeParser();
    static Tokenizer tokenizer = new Tokenizer();

    @Test
    @Order(1)
    void testSimpleParser() {
        provideSimpleParser().forEach(args -> testSimpleParser(
                (String) args.get()[0],
                (String) args.get()[1]));
        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testSimpleParseri2(boolean dummy) {
        assertTrue(success);
    }

    static Stream<Arguments> provideResult() {
        return Stream.of(
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success));
    }

    void testSimpleParser(String expression, String target) {
        List<Token> tokens = tokenizer.evaluate(expression);
        Node root = stp.parse(tokens);

        assertEquals(target, root.toString());
    }

    static Stream<Arguments> provideSimpleParser() {
        return Stream.of(
                Arguments.of("1", "1"),
                Arguments.of("1+2", "(1 + 2)"),
                Arguments.of("1+2-3", "((1 + 2) - 3)"),
                Arguments.of("1+2-3*4", "((1 + 2) - (3 * 4))"));
    }
}
