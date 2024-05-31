package com.nhnacademy.set2.calculator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.nhnacademy.calculator.Calculator;
import com.nhnacademy.calculator.DivideNode;
import com.nhnacademy.calculator.Node;

@TestMethodOrder(OrderAnnotation.class)
public class Test10403_ExpandableCalculator {
    static boolean success = false;
    static Calculator calculator = new Calculator();

    @Test
    @Order(1)
    @SuppressWarnings("unchecked")
    void testAdditionalOperator() {
        provideAdditionalOperator().forEach(args -> testAdditionalOperator(
                (String) args.get()[0],
                (Integer) args.get()[1],
                (BinaryOperator<Node>) args.get()[2],
                (String) args.get()[3],
                (String) args.get()[4]));
        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testSimpleCompositeExpression2(boolean dummy) {
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

    void testAdditionalOperator(String operator, int priority,
            BinaryOperator<Node> operation, String expression, String target) {
        calculator.addBinaryOperator(operator, operation, priority);
        assertDoesNotThrow(() -> assertEquals(target, calculator.evaluate(expression).toString()));
    }

    static Stream<Arguments> provideAdditionalOperator() {
        return Stream.of(
                Arguments.of("%", 1, new BinaryOperator<Node>() {
                    public Node apply(Node l, Node r) {
                        return new DivideNode(l, r);
                    }
                }, "1+[1,1]%2", "[3,2]"),
                Arguments.of("%", 2, new BinaryOperator<Node>() {
                    public Node apply(Node t, Node u) {
                        return new DivideNode(t, u);
                    }
                }, "1+1%2", "1"));
    }
}
