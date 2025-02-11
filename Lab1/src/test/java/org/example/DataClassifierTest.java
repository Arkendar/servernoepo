package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataClassifierTest {

    @Test
    void testClassifyInteger() {
        List<Integer> result = new ArrayList<>();
        Consumer<Integer> integerConsumer = result::add;

        DataClassifier dataClassifier = new DataClassifier(integerConsumer, value -> {}, value -> {});
        dataClassifier.classify("123");

        // Проверяем, что результат содержит правильное значение
        assertEquals(1, result.size());
        assertEquals(123, result.get(0));
    }
}
