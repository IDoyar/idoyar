package com.luxoft.dnepr.courses.toprank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopRankSearcher {

    private static final double DEFAULT_DAMPING_FACTOR = 0.8;
    private static final int DEFAULT_NUMBER_OF_LOOPS_IN_RANK_COMPUTING = 10;

    public TopRankResults execute(List<String> urls) {
        return execute(urls, DEFAULT_DAMPING_FACTOR, DEFAULT_NUMBER_OF_LOOPS_IN_RANK_COMPUTING);
    }

    public TopRankResults execute(List<String> urls, double dampingFactor, int numberOfLoopsInRankComputing) {
        Map<String, String> urlContent = new HashMap();
        for (String url : urls) {
            try {
                urlContent.put(url, getContentByURL(url));
            } catch (IOException e) {
                throw new RuntimeException(String.format("Error on reading from '%s' address", url), e);
            }
        }
        return new TopRankExecutor(dampingFactor, numberOfLoopsInRankComputing).execute(urlContent);
    }

    private String getContentByURL(String url) throws IOException {
        URL oracle = new URL(url);
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return sb.toString();
    }
}
