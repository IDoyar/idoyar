package com.luxoft.dnepr.courses.regular.unit7;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Represents results of FileCrawler's execution.
 */
public class FileCrawlerResults {
    private List<File> processedFiles;
    private Map<String, ? extends Number> wordStatistics;

    public FileCrawlerResults(List<File> processedFiles, Map<String, ? extends Number> wordStatistics) {
        this.processedFiles = processedFiles;
        this.wordStatistics = wordStatistics;
    }

    public List<File> getProcessedFiles() {
        return processedFiles;
    }

    public Map<String, ? extends Number> getWordStatistics() {
        return wordStatistics;
    }

    public void print() {
        for (Map.Entry<String, ? extends Number> wordPair : getWordStatistics().entrySet()) {
            System.out.println(wordPair.getKey() + " : " + wordPair.getValue().intValue());
        }
        System.out.println("==============\nTOTAL unique words: " + getWordStatistics().size());
        System.out.println("TOTAL files processed: " + processedFiles.size());
    }
}
