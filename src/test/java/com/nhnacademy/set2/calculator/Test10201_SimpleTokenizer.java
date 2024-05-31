package com.nhnacademy.set2.calculator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.nhnacademy.calculator.Tokenizer;

@TestMethodOrder(OrderAnnotation.class)
public class Test10201_SimpleTokenizer {
    static boolean success = false;
    static Tokenizer tokenizer = new Tokenizer();

    @Test
    @Order(1)
    void testSimpleTokenzier() {
        provideSimpleTokenizer().forEach(args -> testSimpleTokenizer(
                (String) args.get()[0],
                (String) args.get()[1]));
        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testSimpleTokenizer(boolean dummy) {
        assertTrue(success);
    }

    static Stream<Arguments> provideResult() {
        return Stream.of(
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success));
    }

    void testSimpleTokenizer(String expression, String target) {
        assertDoesNotThrow(() -> tokenizer.evaluate(expression));
        assertEquals(target, tokenizer.toString());
    }

    static Stream<Arguments> provideSimpleTokenizer() {
        return Stream.of(
                Arguments.of("1", "[1]"),
                Arguments.of("1+2", "[1, +, 2]"),
                Arguments.of("1-2*3", "[1, -, 2, *, 3]"),
                Arguments.of("1-[1,2]*3", "[1, -, [1,2], *, 3]"));
    }
}
