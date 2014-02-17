package com.luxoft.dnepr.courses.toprank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopRankResults {

    private Map<String, List<String>> graph = new HashMap();
    private Map<String, List<String>> reverseGraph = new HashMap();
    private Map<String, List<String>> index = new HashMap();
    private Map<String, Double> ranks = new HashMap();

    public Map<String, List<String>> getGraph() {
        return graph;
    }

    public Map<String, List<String>> getIndex() {
        return index;
    }

    public Map<String, Double> getRanks() {
        return ranks;
    }

    public Map<String, List<String>> getReverseGraph() {
        return reverseGraph;
    }

}
