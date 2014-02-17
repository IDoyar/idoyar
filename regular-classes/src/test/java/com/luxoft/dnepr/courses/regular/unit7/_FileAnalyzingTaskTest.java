package com.luxoft.dnepr.courses.regular.unit7;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;

public class _FileAnalyzingTaskTest {

    private FileAnalyzingTask task;
    private BlockingQueue<File> queue;
    private WordStorage storage;

    @Before
    public void setUp() {
        queue = new ArrayBlockingQueue<>(1000);
        storage = new WordStorage();
        task = new FileAnalyzingTask(queue, storage);
    }

    @Test
    public void testAnalyze() throws Exception {
        File file = new File(getClass().getResource("dir1/subdir1/file11.txt").toURI());
        int wordsSum = task.analyze(file);
        assertEquals(13, wordsSum);
        Map<String, ? extends Number> statistics = storage.getWordStatistics();
        assertEquals(10, statistics.size());
        assertEquals(1, statistics.get("3333").intValue());
        assertEquals(2, statistics.get("три").intValue());
        assertEquals(1, statistics.get("тысячи").intValue());
        assertEquals(1, statistics.get("триста").intValue());
        assertEquals(1, statistics.get("тридцать").intValue());
        assertEquals(3, statistics.get("three").intValue());
        assertEquals(1, statistics.get("thousand").intValue());
        assertEquals(1, statistics.get("hundred").intValue());
        assertEquals(1, statistics.get("and").intValue());
        assertEquals(1, statistics.get("thirty").intValue());
    }

    @Test
    public void testCall() throws Exception {
        File file1 = new File(getClass().getResource("dir1/file1.txt").toURI());
        File file11 = new File(getClass().getResource("dir1/subdir1/file11.txt").toURI());
        queue.put(file1);
        queue.put(file11);
        task.forceShutdown();

        int totalWordsSum = task.call();
        assertEquals(40, totalWordsSum);
        Map<String, ? extends Number> statistics = storage.getWordStatistics();
        assertEquals(3, statistics.get("съешь").intValue());
        assertEquals(3, statistics.get("ещё").intValue());
        assertEquals(3, statistics.get("этих").intValue());
        assertEquals(3, statistics.get("мягких").intValue());
        assertEquals(3, statistics.get("французских").intValue());
        assertEquals(3, statistics.get("булок").intValue());
        assertEquals(3, statistics.get("да").intValue());
        assertEquals(3, statistics.get("выпей").intValue());
        assertEquals(3, statistics.get("чаю").intValue());
        assertEquals(1, statistics.get("3333").intValue());
        assertEquals(2, statistics.get("три").intValue());
        assertEquals(1, statistics.get("тысячи").intValue());
        assertEquals(1, statistics.get("триста").intValue());
        assertEquals(1, statistics.get("тридцать").intValue());
        assertEquals(3, statistics.get("three").intValue());
        assertEquals(1, statistics.get("thousand").intValue());
        assertEquals(1, statistics.get("hundred").intValue());
        assertEquals(1, statistics.get("and").intValue());
        assertEquals(1, statistics.get("thirty").intValue());
    }

    @After
    public void tearDown() {
        queue = null;
        storage = null;
        task = null;
    }
}
