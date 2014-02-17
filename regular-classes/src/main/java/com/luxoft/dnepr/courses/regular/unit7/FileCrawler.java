package com.luxoft.dnepr.courses.regular.unit7;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * Crawls files and directories, searches for text files and counts word occurrences.
 */
public class FileCrawler {
    private WordStorage wordStorage = new WordStorage();
    private File root;
    private int maxNumberOfThreads;

    public FileCrawler(String rootFolder, int maxNumberOfThreads) {
        root = new File(rootFolder);
        this.maxNumberOfThreads = maxNumberOfThreads;
    }

    /**
     * Performs crawling.
     * This method should wait until all parallel tasks are finished.
     * @return FileCrawlerResults
     */
    public FileCrawlerResults execute() {
        ExecutorService executorService = Executors.newFixedThreadPool(maxNumberOfThreads);
        BlockingQueue<File> queue = new ArrayBlockingQueue<>((maxNumberOfThreads - 1) * 2);
        //submit 1 "producer" task
        Future<List<File>> enumerationTaskFuture = executorService.submit(new FileEnumerationTask(root, queue));
        //submit multiple "consumer" tasks
        List<Future<Integer>> analyzingTaskFutures = new ArrayList<>();
        for (int i = 0; i < maxNumberOfThreads - 1; i++) {
            analyzingTaskFutures.add(executorService.submit(new FileAnalyzingTask(queue, wordStorage)));
        }
        executorService.shutdown();
        List<File> processedFiles = null;
        try {
            processedFiles = enumerationTaskFuture.get();
            for (Future<Integer> f : analyzingTaskFutures) {
                f.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        return new FileCrawlerResults(processedFiles, wordStorage.getWordStatistics());
    }

    public static void main(String args[]) {
        String rootDir = args.length > 0 ? args[0] : System.getProperty("user.home");
        FileCrawler crawler = new FileCrawler(rootDir, 10);
        crawler.execute().print();
    }

}
