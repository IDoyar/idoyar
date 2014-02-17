package com.luxoft.dnepr.courses.regular.unit7;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Task which scans given directory and puts all found text files into the queue for processing by {@link FileAnalyzingTask}.
 */
public class FileEnumerationTask implements Callable<List<File>> {
    public static File DUMMY_FILE = new File("DUMMY");

    private File directory;
    private BlockingQueue<File> filesQueue;
    private FilenameFilter textFileFilter = new TextFileFilter();
    private FileFilter directoryFilter = new DirectoryFilter();
    private List<File> processedFiles = new ArrayList<>();

    public FileEnumerationTask(File directory, BlockingQueue<File> filesQueue) {
        this.directory = directory;
        this.filesQueue = filesQueue;
    }

    @Override
    public List<File> call() {
        try {
            enumerate(directory);
            filesQueue.put(DUMMY_FILE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Enumeration task is finished");
        return processedFiles;
    }

    protected void enumerate(File directory) throws InterruptedException {
        System.out.println("Directory: " + directory);
        File[] textFiles = directory.listFiles(textFileFilter);
        if (textFiles != null) {
            for (File textFile : textFiles) {
                filesQueue.put(textFile);
                System.out.println("Found text file: " + textFile);
            }
            processedFiles.addAll(Arrays.asList(textFiles));
        }
        File[] nestedDirectories = directory.listFiles(directoryFilter);
        if (nestedDirectories != null) {
            for (File nestedDirectory : nestedDirectories) {
                enumerate(nestedDirectory);
            }
        }
    }

    private static class TextFileFilter implements FilenameFilter {
        public static final String FILENAME_SUFFIX = ".txt";

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(FILENAME_SUFFIX);
        }
    }

    private static class DirectoryFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    }
}
