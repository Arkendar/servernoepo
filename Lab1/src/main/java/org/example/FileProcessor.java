package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class FileProcessor {
    private final List<String> inputFiles;

    public FileProcessor(List<String> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public void processFiles(Consumer<String> lineConsumer) {
        for (String filePath : inputFiles) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("Error: file " + filePath + " not found.");
                continue;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lineConsumer.accept(line);
                }
            } catch (IOException e) {
                System.err.println("Error reading file " + filePath + ": " + e.getMessage());
            }
        }
    }
}