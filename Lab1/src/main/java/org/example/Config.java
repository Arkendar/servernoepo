package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private final Path outputDirectory;
    private final String prefix;
    private final boolean appendMode;
    private final boolean fullStatistics;
    private final List<String> inputFiles = new ArrayList<>();

    public Config(String[] args) {
        Path outputDir = Paths.get(".");
        String filePrefix = "";
        boolean append = false;
        boolean fullStats = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputDir = Paths.get(args[++i]);
                    } else {
                        throw new IllegalArgumentException("The -o option requires a path to be specified.");
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        filePrefix = args[++i];
                    } else {
                        throw new IllegalArgumentException("The -p option requires a prefix to be specified.");
                    }
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    fullStats = false;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    if (!args[i].startsWith("-")) {
                        inputFiles.add(args[i]); // Добавляем входные файлы
                    }
                    break;
            }
        }

        this.outputDirectory = outputDir;
        this.prefix = filePrefix;
        this.appendMode = append;
        this.fullStatistics = fullStats;
    }

    public Path getOutputDirectory() {
        return outputDirectory;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}