package com.luxoft.dnepr.courses.regular.unit7;

import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class _FileEnumerationTaskTest {

    @Test
    public void testRun() throws Exception {
        assertNotNull(getClass().getResource("dir1"));
        File dir1 = new File(getClass().getResource("dir1").toURI());
        assertTrue(dir1.exists());

        BlockingQueue<File> queue = new ArrayBlockingQueue<>(100);
        FileEnumerationTask task = new FileEnumerationTask(dir1, queue);
        List<File> processedFiles = task.call();

        assertTrue(queue.containsAll(processedFiles));
    }
}
