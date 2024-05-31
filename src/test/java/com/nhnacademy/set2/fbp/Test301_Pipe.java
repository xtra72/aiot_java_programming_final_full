package com.nhnacademy.set2.fbp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.fbp.Pipe;

@TestMethodOrder(OrderAnnotation.class)
class Test301_Pipe {
    static boolean success = false;
    static ObjectMapper mapper;

    @BeforeAll
    static void init() {
        mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testPipe() {

        Pipe pipe = new Pipe();

        assertTrue(pipe.isEmpty());

        assertDoesNotThrow(() -> {
            JsonNode node = mapper.readTree("{}");
            assertDoesNotThrow(() -> pipe.push(node));
            assertTrue(!pipe.isEmpty() && (pipe.getCount() == 1));
            pipe.poll();
        });

        assertTrue(pipe.isEmpty());

        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testPipe2(boolean dummy) {
        assertTrue(success);
    }

    static Stream<Arguments> provideResult() {
        return Stream.of(
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success));
    }
}