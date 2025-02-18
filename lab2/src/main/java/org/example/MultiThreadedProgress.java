package org.example;

import static java.lang.Thread.sleep;

class ProgressTask implements Runnable {
    private final int taskNumber; // Номер потока
    private final int taskLength; // Длина задачи
    private final String taskType; // Тип задачи

    public ProgressTask(int taskNumber, int taskLength, String taskType) {
        this.taskNumber = taskNumber;
        this.taskLength = taskLength;
        this.taskType = taskType;
    }

    public static void progressPercentage(int remain, int total) {
        if (remain > total) {
            throw new IllegalArgumentException();
        }
        int maxBareSize = 10; // 10unit for 100%
        int remainProcent = ((100 * remain) / total) / maxBareSize;
        char defaultChar = '-';
        String icon = ">";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append("[");
        for (int i = 0; i < remainProcent; i++) {
            bareDone.append(icon);
        }        String bareRemain = bare.substring(remainProcent, bare.length());
        System.out.print("\r" + bareDone + bareRemain + " " + remainProcent * 10 + "%");
        if (remain == total) {
            System.out.print("\n");
        }
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis(); // Время начала
        StringBuilder progressBar = new StringBuilder();

        for (int i = 0; i < taskLength; i++) {
            progressPercentage(i, taskLength);
            System.out.printf(" Thread %d (ID: %d) [%s]\n",  // Поток %d (ID: %d) [%s]
                    taskNumber, Thread.currentThread().getId(), progressBar);
            if(taskType=="A"){
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        long endTime = System.currentTimeMillis(); // Время окончания
        System.out.printf("Thread %d (ID: %d) completed in %d ms\n",  // Поток %d (ID: %d) завершён за %d мс
                taskNumber, Thread.currentThread().getId(), (endTime - startTime));
    }
}

public class MultiThreadedProgress {
    public static void main(String[] args) {
        // количество символов в прогресс-баре
        int taskLength = 10;

        new Thread(new ProgressTask(1, taskLength, "A")).start();
        new Thread(new ProgressTask(2, taskLength, "B")).start();
    }
}
