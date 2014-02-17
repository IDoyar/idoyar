package com.luxoft.dnepr.courses.toprank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Anton
 * Date: 5/25/13
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class _TopRankSearcherITTest {

    private static final double ERROR = 0.0000000001;
    private static final String DOMAIN = "http://dnepr-luxoft-courses.appspot.com/";

    private static final String[] URLS = new String[] {
            DOMAIN + "index.html"
        , DOMAIN + "hummus.html"
        , DOMAIN + "arsenic.html"
        , DOMAIN + "kathleen.html"
        , DOMAIN + "nickel.html"
        , DOMAIN + "zinc.html"
    };
    private TopRankSearcher searcher;

    @Before
    public void init() {
        searcher = new TopRankSearcher();
    }

    @Test
    public void testExecute() throws Exception {
        List<String> urls = new ArrayList(Arrays.asList(URLS));
        TopRankResults results = searcher.execute(urls);

        Assert.assertEquals(0, results.getReverseGraph().get(DOMAIN + "kathleen.html").size());
        Assert.assertEquals(5, results.getReverseGraph().get(DOMAIN + "index.html").size());
        Assert.assertEquals(2, results.getReverseGraph().get(DOMAIN + "zinc.html").size());
        Assert.assertEquals(0, results.getReverseGraph().get(DOMAIN + "hummus.html").size());
        Assert.assertEquals(1, results.getReverseGraph().get(DOMAIN + "arsenic.html").size());
        Assert.assertEquals(1, results.getReverseGraph().get(DOMAIN + "nickel.html").size());


        Assert.assertEquals(2, results.getGraph().get(DOMAIN + "kathleen.html").size());
        Assert.assertEquals(0, results.getGraph().get(DOMAIN + "index.html").size());
        Assert.assertEquals(1, results.getGraph().get(DOMAIN + "zinc.html").size());
        Assert.assertEquals(1, results.getGraph().get(DOMAIN + "hummus.html").size());
        Assert.assertEquals(2, results.getGraph().get(DOMAIN + "arsenic.html").size());
        Assert.assertEquals(3, results.getGraph().get(DOMAIN + "nickel.html").size());

        Assert.assertEquals(1, results.getIndex().get("buttercream").size());
        Assert.assertEquals(3, results.getIndex().get("of").size());
        Assert.assertEquals(5, results.getIndex().get("Hummus").size());

        Assert.assertEquals(0.033333333333333326d, results.getRanks().get(DOMAIN + "index.html"), ERROR);
        Assert.assertEquals(0.05413333333333332, results.getRanks().get(DOMAIN + "arsenic.html"), ERROR);
    }
}
