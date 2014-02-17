package com.luxoft.dnepr.courses.toprank;

import java.util.*;

public class TopRankExecutor {

    private static final String HREF_PATTERN = "<a href=";
    private static final String HREF_PARENTHESIS = "\"";
    private double dampingFactor;
    private int numberOfLoopsInRankComputing;

    public TopRankExecutor(double dampingFactor, int numberOfLoopsInRankComputing) {
        this.dampingFactor = dampingFactor;
        this.numberOfLoopsInRankComputing = numberOfLoopsInRankComputing;
    }

    public TopRankResults execute(Map<String, String> urlContent) {
        TopRankResults results = new TopRankResults();
        for (Map.Entry<String, String> entry : urlContent.entrySet()) {
            addPageToIndex(results.getIndex(), entry.getKey(), entry.getValue());
            results.getReverseGraph().put(entry.getKey(), getAllLinksWithinPage(entry.getValue()));
        }
        computeReverseGraph(results.getGraph(), results.getReverseGraph());
        computeRanks(results.getReverseGraph(), results.getRanks());
        return results;
    }

    private void computeReverseGraph(Map<String, List<String>> graph, Map<String, List<String>> reverseGraph) {
        for (Map.Entry<String, List<String>> entry : reverseGraph.entrySet()) {
            String key = entry.getKey();
            List<String> value = new LinkedList();
            for (Map.Entry<String, List<String>> entry2 : reverseGraph.entrySet()) {
                if (!key.equals(entry2.getKey()) && entry2.getValue().contains(key)) {
                    value.add(entry2.getKey());
                }
            }
            graph.put(key, value);
        }
    }

    private void computeRanks(Map<String, List<String>> graph, Map<String, Double> ranks) {
        int numberOfPages = graph.size();
        for (String url : graph.keySet()) {
            ranks.put(url, 1d / numberOfPages);
        }
        for (int i = 0; i < numberOfLoopsInRankComputing; i++) {
            Map<String, Double> newRanks = new HashMap();
            for (String url : graph.keySet()) {
                double newRank = (1 - dampingFactor) / numberOfPages;
                List<String> inLinks = getInLinks(graph, url);
                for (String inLink : inLinks) {
                    newRank += dampingFactor * ranks.get(inLink) / graph.get(inLink).size();
                }
                newRanks.put(url, newRank);
            }
            ranks.putAll(newRanks);
        }
    }

    private List<String> getInLinks(Map<String, List<String>> graph, String url) {
        List<String> inLinks = new ArrayList();
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            if (entry.getValue().contains(url)) {
                inLinks.add(entry.getKey());
            }
        }
        return inLinks;
    }

    private void addPageToIndex(Map<String, List<String>> index, String url, String content) {
        String[] words = content.split("[\t\n\\s]");
        for (String word : words) {
            if (index.get(word) == null) {
                List<String> list = new ArrayList();
                list.add(url);
                index.put(word, list);
            } else {
                index.get(word).add(url);
            }
        }
    }

    private List<String> getAllLinksWithinPage(String content) {
        List<String> links = new ArrayList();
        int startIndex = 0;
        while (true) {
            startIndex = content.indexOf(HREF_PATTERN, startIndex);
            if (startIndex < 0) {
                break;
            }
            int linkStart = startIndex + HREF_PATTERN.length() + 1;
            int linkEnd = content.indexOf(HREF_PARENTHESIS, linkStart);
            links.add(new String(content.substring(linkStart, linkEnd)));
            startIndex = linkEnd;
        }
        return links;
    }
}
