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
public class Test10302_CompositeParser {
    static boolean success = false;
    static SyntaxTreeParser stp = new SyntaxTreeParser();
    static Tokenizer tokenizer = new Tokenizer();

    @Test
    @Order(1)
    void testCompositeParser() {
        provideCompositeParser().forEach(args -> testCompositeParser(
                (String) args.get()[0],
                (String) args.get()[1]));
        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testCompositeParseri2(boolean dummy) {
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

    void testCompositeParser(String expression, String target) {
        List<Token> tokens = tokenizer.evaluate(expression);
        Node root = stp.parse(tokens);

        assertEquals(target, root.toString());
    }

    static Stream<Arguments> provideCompositeParser() {
        return Stream.of(
                Arguments.of("1+-2", "(1 + -2)"),
                Arguments.of("1+[1,2]", "(1 + [1,2])"),
                Arguments.of("1+[2,3] / 3", "(1 + ([2,3] / 3))"),
                Arguments.of("([1,4]+(2-[1,3]))*4", "(([1,4] + (2 - [1,3])) * 4)"),
                Arguments.of("-3 * 2 + [-2, 5] * [2,3] - (7 / [4,5])",
                        "(((-3 * 2) + ([-2,5] * [2,3])) - (7 / [4,5]))"));
    }
}
