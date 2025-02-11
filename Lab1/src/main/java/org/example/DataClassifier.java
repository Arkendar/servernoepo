package org.example;

import java.util.function.Consumer;

public class DataClassifier {
    private final Consumer<Integer> integerConsumer;
    private final Consumer<Double> floatConsumer;
    private final Consumer<String> stringConsumer;

    public DataClassifier(Consumer<Integer> integerConsumer, Consumer<Double> floatConsumer, Consumer<String> stringConsumer) {
        this.integerConsumer = integerConsumer;
        this.floatConsumer = floatConsumer;
        this.stringConsumer = stringConsumer;
    }

    public void classify(String line) {
        if (isInteger(line)) {
            integerConsumer.accept(Integer.parseInt(line));
        } else if (isDouble(line)) {
            floatConsumer.accept(Double.parseDouble(line));
        } else {
            stringConsumer.accept(line);
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return str.contains(".") || str.contains("e") || str.contains("E");
        } catch (NumberFormatException e) {
            return false;
        }
    }
}