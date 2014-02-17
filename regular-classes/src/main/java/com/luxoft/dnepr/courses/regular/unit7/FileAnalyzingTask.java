package com.luxoft.dnepr.courses.regular.unit7;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Task which takes files from queue, splits text into words and saves them into {@WordStorage}
 */
public class FileAnalyzingTask implements Callable<Integer> {
    private BlockingQueue<File> filesQueue;
    private WordStorage storage;
    private static Pattern pattern = Pattern.compile("\\b\\w+\\b", Pattern.UNICODE_CHARACTER_CLASS);

    public FileAnalyzingTask(BlockingQueue<File> filesQueue, WordStorage storage) {
        this.filesQueue = filesQueue;
        this.storage = storage;
    }

    /**
     * Takes files from queue, splits text into words and saves them into {@WordStorage}
     * @return total sum of processed words (including duplicates)
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        try {
            boolean done = false;
            while (!done) {
                File textFile = filesQueue.take();
                if (textFile == FileEnumerationTask.DUMMY_FILE) {
                    filesQueue.put(FileEnumerationTask.DUMMY_FILE);
                    done = true;
                } else {
                    sum += analyze(textFile);
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Analyzing task is finished");
        return sum;
    }

    public int analyze(File textFile) throws FileNotFoundException {
        System.out.println("Analyzing " + textFile);
        int wordsNumber = 0;
        try (Scanner scanner = new Scanner(new FileInputStream(textFile), "UTF-8")) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                Matcher m = pattern.matcher(nextLine);
                while (m.find()) {
                    String nextWord = m.group();
                    storage.save(nextWord);
                    wordsNumber++;
                }
            }
        }
        return wordsNumber;
    }

    public void forceShutdown() throws InterruptedException {
        filesQueue.put(FileEnumerationTask.DUMMY_FILE);
    }
}
