package org.example;

import java.util.List;

public class StatisticsCollector {
    private int integerCount;
    private int floatCount;
    private int stringCount;

    private int minInteger = Integer.MAX_VALUE;
    private int maxInteger = Integer.MIN_VALUE;
    private long sumInteger = 0;

    private double minFloat = Double.MAX_VALUE;
    private double maxFloat = Double.MIN_VALUE;
    private double sumFloat = 0;

    private int minStringLength = Integer.MAX_VALUE;
    private int maxStringLength = 0;

    public void collectStatistics(Object value) {
        if (value instanceof Integer) {
            integerCount++;
            int intValue = (Integer) value;
            minInteger = Math.min(minInteger, intValue);
            maxInteger = Math.max(maxInteger, intValue);
            sumInteger += intValue;
        } else if (value instanceof Double) {
            floatCount++;
            double floatValue = (Double) value;
            minFloat = Math.min(minFloat, floatValue);
            maxFloat = Math.max(maxFloat, floatValue);
            sumFloat += floatValue;
        } else if (value instanceof String) {
            stringCount++;
            String strValue = (String) value;
            int length = strValue.length();
            minStringLength = Math.min(minStringLength, length);
            maxStringLength = Math.max(maxStringLength, length);
        }
    }

    public void printShortStatistics() {
        System.out.println("Short statistics:");
        System.out.println("Integers: " + integerCount);
        System.out.println("Floating point numbers: " + floatCount);
        System.out.println("Strings: " + stringCount);
    }

    public void printFullStatistics() {
        System.out.println("Full statistics:");

        if (integerCount > 0) {
            System.out.println("Integers: " + integerCount);
            System.out.println("Min: " + minInteger);
            System.out.println("Max: " + maxInteger);
            System.out.println("Sum: " + sumInteger);
            System.out.println("Average: " + (sumInteger / (double) integerCount));
        }

        if (floatCount > 0) {
            System.out.println("Floating point numbers: " + floatCount);
            System.out.println("Min: " + minFloat);
            System.out.println("Max: " + maxFloat);
            System.out.println("Sum: " + sumFloat);
            System.out.println("Average: " + (sumFloat / floatCount));
        }

        if (stringCount > 0) {
            System.out.println("Strings: " + stringCount);
            System.out.println("Min length: " + minStringLength);
            System.out.println("Max length: " + maxStringLength);
        }
    }
}
