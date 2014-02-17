package com.luxoft.dnepr.courses.regular.unit7;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;


public class _FileCrawlerTest {

    @Test
    public void testExecute() throws Exception {
        String rootDirPath = getClass().getResource("dir1").getFile();
        FileCrawler crawler = new FileCrawler(rootDirPath, 10);
        FileCrawlerResults results = crawler.execute();

        assertEquals(2, results.getProcessedFiles().size());

        Map<String, ? extends Number> statistics = results.getWordStatistics();
        assertEquals(19, results.getWordStatistics().size());
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

    @Test
    public void testExecuteBigData() throws Exception  {
        String rootDirPath = getClass().getResource("dir2").getFile();
        System.out.println(rootDirPath);
        FileCrawler crawler = new FileCrawler(rootDirPath, 10);
        long start = System.currentTimeMillis();
        FileCrawlerResults results = crawler.execute();
        System.out.println("*********************************");
        System.out.println("Execution time: " + (System.currentTimeMillis() - start) + " ms");
        System.out.println("*********************************");
        assertEquals(40, results.getProcessedFiles().size());
        assertEquals(148, results.getWordStatistics().size());
    }
}
