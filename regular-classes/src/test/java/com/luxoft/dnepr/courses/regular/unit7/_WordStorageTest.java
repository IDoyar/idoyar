package com.luxoft.dnepr.courses.regular.unit7;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class _WordStorageTest {
    private WordStorage wordStorage;

    @Before
    public void setUp() {
        wordStorage = new WordStorage();
    }

    @After
    public void tearDown() {
        wordStorage = null;
    }

    @Test
    public void testSave() throws Exception {
        int numberOfThreads = 200;
        Runnable testRunnable = new Runnable() {
            @Override
            public void run() {
                wordStorage.save("abracadabra");
                for (int i = 0; i < 10; i++) {
                    wordStorage.save("test1");
                    wordStorage.save("test2");
                    wordStorage.save("test3");
                    wordStorage.save("test4");
                    wordStorage.save("test5");
                    wordStorage.save("test6");
                    wordStorage.save("test7");
                    wordStorage.save("test8");
                    wordStorage.save("test9");
                    wordStorage.save("test10");
                    wordStorage.save("test11");
                    wordStorage.save("test12");
                    wordStorage.save("test13");
                    wordStorage.save("test14");
                    wordStorage.save("test15");
                    wordStorage.save("test16");
                }
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(testRunnable);
        }
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        Map<String, ? extends Number> wordStatistics = wordStorage.getWordStatistics();

        assertEquals(numberOfThreads*10, wordStatistics.get("test1").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test2").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test3").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test4").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test5").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test6").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test7").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test8").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test9").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test10").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test11").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test12").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test13").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test14").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test15").intValue());
        assertEquals(numberOfThreads*10, wordStatistics.get("test16").intValue());
        assertEquals(numberOfThreads, wordStatistics.get("abracadabra").intValue());
    }

    @Test
    public void testGetWordStatistics() {
        assertEquals(0, wordStorage.getWordStatistics().size());
        wordStorage.save("test");
        assertEquals(1, wordStorage.getWordStatistics().size());
        wordStorage.save("test");
        assertEquals(1, wordStorage.getWordStatistics().size());

        try {
            wordStorage.getWordStatistics().remove("test");
            fail("wordStatistics is unmodifiable");
        } catch (UnsupportedOperationException e) {}
    }

}
