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
public class Test10402_CompositeCalculator {
    static boolean success1 = false;
    static boolean success2 = false;
    static Calculator calculator = new Calculator();

    @Test
    @Order(1)
    void testSimpleCompositeExpression() {
        provideSimpleCompositeExpression().forEach(args -> testSimpleCompositeExpression(
                (String) args.get()[0],
                (String) args.get()[1]));
        success1 = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult1")
    @Order(2)
    void testSimpleCompositeExpression2(boolean dummy) {
        assertTrue(success1);
    }

    static Stream<Arguments> provideResult1() {
        return Stream.of(
                Arguments.of(success1),
                Arguments.of(success1),
                Arguments.of(success1),
                Arguments.of(success1));
    }

    void testSimpleCompositeExpression(String expression, String target) {
        assertDoesNotThrow(() -> assertEquals(target, calculator.evaluate(expression).toString()));
    }

    static Stream<Arguments> provideSimpleCompositeExpression() {
        return Stream.of(
                Arguments.of("2*2+2*[1,2]", "5"),
                Arguments.of("2*2-3*[1,2]", "[5,2]"),
                Arguments.of("1*2/2+[1,2]", "[3,2]"));
    }

    @Test
    @Order(3)
    void testCompositeExpression() {
        provideCompositeExpression().forEach(args -> testCompositeExpression(
                (String) args.get()[0],
                (String) args.get()[1]));
        success2 = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult2")
    @Order(4)
    void testCompositeExpression2(boolean dummy) {
        assertTrue(success2);
    }

    static Stream<Arguments> provideResult2() {
        return Stream.of(
                Arguments.of(success2),
                Arguments.of(success2),
                Arguments.of(success2),
                Arguments.of(success2));
    }

    void testCompositeExpression(String expression, String target) {
        assertDoesNotThrow(() -> assertEquals(target, calculator.evaluate(expression).toString()));
    }

    static Stream<Arguments> provideCompositeExpression() {
        return Stream.of(
                Arguments.of("-3 --2 + ([-2, 5] * [2,-3] - (7 / [ 4, 5]))", "[-569,60]"),
                Arguments.of("-3 * 2 + [23, 5] * [2,3] - (2 / [11,3])", "[-574,165]"));
    }
}
