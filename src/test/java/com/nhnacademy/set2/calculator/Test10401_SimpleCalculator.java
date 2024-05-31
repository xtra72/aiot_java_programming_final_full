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

import com.nhnacademy.calculator.Calculator;

@TestMethodOrder(OrderAnnotation.class)
public class Test10401_SimpleCalculator {
    static boolean success1 = false;
    static boolean success2 = false;
    static Calculator calculator = new Calculator();

    @Test
    @Order(1)
    void testSimpleIntegerExpression() {
        provideSimpleIntegerExpression().forEach(args -> testSimpleIntegerExpression(
                (String) args.get()[0],
                (String) args.get()[1]));
        success1 = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult1")
    @Order(2)
    void testSimpleIntegerExpression2(boolean dummy) {
        assertTrue(success1);
    }

    static Stream<Arguments> provideResult1() {
        return Stream.of(
                Arguments.of(success1),
                Arguments.of(success1),
                Arguments.of(success1),
                Arguments.of(success1));
    }

    void testSimpleIntegerExpression(String expression, String target) {
        assertDoesNotThrow(() -> assertEquals(target, calculator.evaluate(expression).toString()));
    }

    static Stream<Arguments> provideSimpleIntegerExpression() {
        return Stream.of(
                Arguments.of("3", "3"),
                Arguments.of("1+2", "3"),
                Arguments.of("1-2", "-1"),
                Arguments.of("1*2", "2"),
                Arguments.of("256/16", "16"));
    }

    @Test
    @Order(3)
    void testSimpleExpression() {
        provideSimpleExpression().forEach(args -> testSimpleExpression(
                (String) args.get()[0],
                (String) args.get()[1]));
        success2 = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult2")
    @Order(4)
    void testSimpleExpression2(boolean dummy) {
        assertTrue(success2);
    }

    static Stream<Arguments> provideResult2() {
        return Stream.of(
                Arguments.of(success2),
                Arguments.of(success2),
                Arguments.of(success2),
                Arguments.of(success2));
    }

    void testSimpleExpression(String expression, String target) {
        assertDoesNotThrow(() -> assertEquals(target, calculator.evaluate(expression).toString()));
    }

    static Stream<Arguments> provideSimpleExpression() {
        return Stream.of(
                Arguments.of("3+[1,2]", "[7,2]"),
                Arguments.of("[1,2]+[5,2]", "3"),
                Arguments.of("1-[1,3]", "[2,3]"),
                Arguments.of("[1,2]-[1,3]", "[1,6]"),
                Arguments.of("2*[1,3]", "[2,3]"),
                Arguments.of("[1,2]*[1,3]", "[1,6]"),
                Arguments.of("[1,2]*[1,-3]", "[-1,6]"),
                Arguments.of("1/[1,3]", "3"),
                Arguments.of("[1,2]/[1,3]", "[3,2]"),
                Arguments.of("[1,-2]/[-1,3]", "[3,2]"));
    }
}
