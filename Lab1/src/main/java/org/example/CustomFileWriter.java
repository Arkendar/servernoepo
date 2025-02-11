package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CustomFileWriter {
    private final Config config;

    public CustomFileWriter(Config config) {
        this.config = config;
    }

    public void writeToFile(String type, String content) {
        String fileName = getFileName(type);
        Path filePath = Paths.get(fileName);

        try {
            // Создание директории, если её нет
            Path parentDir = filePath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            // Запись в файл
            StandardOpenOption option = config.isAppendMode() ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING;
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, option)) {
                writer.write(content);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file " + fileName + ": " + e.getMessage());
        }
    }

    private String getFileName(String type) {
        String fileName = config.getPrefix() + "result_" + type + ".txt";
        return Paths.get(config.getOutputDirectory().toString(), fileName).toString();
    }
}