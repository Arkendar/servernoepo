package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Конфигурация программы
            Config config = new Config(args);

            // Инициализация объектов для классификации и записи данных
            StatisticsCollector statisticsCollector = new StatisticsCollector();
            CustomFileWriter fileWriter = new CustomFileWriter(config);

            // Создаем DataClassifier, который передаст данные в нужные файлы
            DataClassifier dataClassifier = new DataClassifier(
                    value -> {
                        fileWriter.writeToFile("integers", String.valueOf(value));
                        statisticsCollector.collectStatistics(value);
                    },
                    value -> {
                        fileWriter.writeToFile("floats", String.valueOf(value));
                        statisticsCollector.collectStatistics(value);
                    },
                    value -> {
                        fileWriter.writeToFile("strings", value);
                        statisticsCollector.collectStatistics(value);
                    });

            // Обработка файлов
            FileProcessor fileProcessor = new FileProcessor(config.getInputFiles());
            fileProcessor.processFiles(line -> dataClassifier.classify(line));

            // Вывод статистики
            if (config.isFullStatistics()) {
                statisticsCollector.printFullStatistics();
            } else {
                statisticsCollector.printShortStatistics();
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
