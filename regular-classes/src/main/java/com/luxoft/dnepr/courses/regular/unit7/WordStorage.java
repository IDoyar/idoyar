package com.luxoft.dnepr.courses.regular.unit7;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents word statistics storage.
 */
public class WordStorage {
    private ConcurrentMap<String, AtomicInteger> wordMap = new ConcurrentHashMap<>();

    /**
     * Saves given word and increments count of occurrences.
     * @param word
     */
    public void save(String word) {
        AtomicInteger storedValue = wordMap.putIfAbsent(word, new AtomicInteger(1));
        if (storedValue != null) {
            storedValue.incrementAndGet();
        }
    }

    /**
     * @return unmodifiable map containing words and corresponding counts of occurrences.
     */
    public Map<String, ? extends Number> getWordStatistics() {
        return Collections.unmodifiableMap(wordMap);
    }
}
