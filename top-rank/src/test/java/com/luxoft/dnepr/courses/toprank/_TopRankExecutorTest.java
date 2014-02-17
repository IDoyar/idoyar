package com.luxoft.dnepr.courses.toprank;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class _TopRankExecutorTest {

    private static final double ERROR = 0.0000000001;

    @Test
    public void testExecute() throws Exception {
        TopRankExecutor executor = new TopRankExecutor(0.8, 10);
        Map<String, String> urlContent = new HashMap();
        urlContent.put("http://udacity.com/cs101x/urank/index.html", "<html>\n" +
                "<body>\n" +
                "<h1>Dave's Cooking Algorithms</h1>\n" +
                "<p>\n" +
                "Here are my favorite recipies:\n" +
                "<ul>\n" +
                "<li> <a href=\"http://udacity.com/cs101x/urank/hummus.html\">Hummus Recipe</a>\n" +
                "<li> <a href=\"http://udacity.com/cs101x/urank/arsenic.html\">World's Best Hummus</a>\n" +
                "<li> <a href=\"http://udacity.com/cs101x/urank/kathleen.html\">Kathleen's Hummus Recipe</a>\n" +
                "</ul>\n" +
                "For more expert opinions, check out the\n" +
                "<a href=\"http://udacity.com/cs101x/urank/nickel.html\">Nickel Chef</a>\n" +
                "and <a href=\"http://udacity.com/cs101x/urank/zinc.html\">Zinc Chef</a>.\n" +
                "</body>\n" +
                "</html>");
        urlContent.put("http://udacity.com/cs101x/urank/zinc.html", "<html>\n" +
                "<body>\n" +
                "<h1>The Zinc Chef</h1>\n" +
                "<p>\n" +
                "I learned everything I know from\n" +
                "<a href=\"http://udacity.com/cs101x/urank/nickel.html\">the Nickel Chef</a>.\n" +
                "</p>\n" +
                "<p>\n" +
                "For great hummus, try\n" +
                "<a href=\"http://udacity.com/cs101x/urank/arsenic.html\">this recipe</a>.\n" +
                "</body>\n" +
                "</html>");
        urlContent.put("http://udacity.com/cs101x/urank/nickel.html", "<html>\n" +
                "<body>\n" +
                "<h1>The Nickel Chef</h1>\n" +
                "<p>\n" +
                "This is the\n" +
                "<a href=\"http://udacity.com/cs101x/urank/kathleen.html\">\n" +
                "best Hummus recipe!\n" +
                "</a>\n" +
                "</body>\n" +
                "</html>");
        urlContent.put("http://udacity.com/cs101x/urank/kathleen.html", "<html>\n" +
                "<body>\n" +
                "<h1>\n" +
                "Kathleen's Hummus Recipe\n" +
                "</h1>\n" +
                "<p>\n" +
                "<ol>\n" +
                "<li> Open a can of garbonzo beans.\n" +
                "<li> Crush them in a blender.\n" +
                "<li> Add 3 tablesppons of tahini sauce.\n" +
                "<li> Squeeze in one lemon.\n" +
                "<li> Add salt, pepper, and buttercream frosting to taste.\n" +
                "</ol>\n" +
                "</body>\n" +
                "</html>");
        urlContent.put("http://udacity.com/cs101x/urank/arsenic.html", "<html>\n" +
                "<body>\n" +
                "<h1>\n" +
                "The Arsenic Chef's World Famous Hummus Recipe\n" +
                "</h1>\n" +
                "<p>\n" +
                "<ol>\n" +
                "<li> Kidnap the <a href=\"http://udacity.com/cs101x/urank/nickel.html\">Nickel Chef</a>.\n" +
                "<li> Force her to make hummus for you.\n" +
                "</ol>\n" +
                "</body>\n" +
                "</html>");
        urlContent.put("http://udacity.com/cs101x/urank/hummus.html", "<html>\n" +
                "<body>\n" +
                "<h1>\n" +
                "Hummus Recipe\n" +
                "</h1>\n" +
                "<p>\n" +
                "\n" +
                "<ol>\n" +
                "<li> Go to the store and buy a container of hummus.\n" +
                "<li> Open it.\n" +
                "</ol>\n" +
                "</body>\n" +
                "</html>");
        TopRankResults results = executor.execute(urlContent);
        System.out.println(results);

        Assert.assertEquals(0, results.getReverseGraph().get("http://udacity.com/cs101x/urank/kathleen.html").size());
        Assert.assertEquals(5, results.getReverseGraph().get("http://udacity.com/cs101x/urank/index.html").size());
        Assert.assertEquals(2, results.getReverseGraph().get("http://udacity.com/cs101x/urank/zinc.html").size());
        Assert.assertEquals(0, results.getReverseGraph().get("http://udacity.com/cs101x/urank/hummus.html").size());
        Assert.assertEquals(1, results.getReverseGraph().get("http://udacity.com/cs101x/urank/arsenic.html").size());
        Assert.assertEquals(1, results.getReverseGraph().get("http://udacity.com/cs101x/urank/nickel.html").size());

        Assert.assertEquals(2, results.getGraph().get("http://udacity.com/cs101x/urank/kathleen.html").size());
        Assert.assertEquals(0, results.getGraph().get("http://udacity.com/cs101x/urank/index.html").size());
        Assert.assertEquals(1, results.getGraph().get("http://udacity.com/cs101x/urank/zinc.html").size());
        Assert.assertEquals(1, results.getGraph().get("http://udacity.com/cs101x/urank/hummus.html").size());
        Assert.assertEquals(2, results.getGraph().get("http://udacity.com/cs101x/urank/arsenic.html").size());
        Assert.assertEquals(3, results.getGraph().get("http://udacity.com/cs101x/urank/nickel.html").size());

        Assert.assertEquals(1, results.getIndex().get("buttercream").size());
        Assert.assertEquals(3, results.getIndex().get("of").size());
        Assert.assertEquals(5, results.getIndex().get("Hummus").size());

        Assert.assertEquals(0.033333333333333326d, results.getRanks().get("http://udacity.com/cs101x/urank/index.html"), ERROR);
        Assert.assertEquals(0.05413333333333332, results.getRanks().get("http://udacity.com/cs101x/urank/arsenic.html"), ERROR);
    }
}
