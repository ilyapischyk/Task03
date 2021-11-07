package com.pischyk.task3.builder;

import com.pischyk.task3.exception.GemException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.pischyk.task3.builder.GemBuilderFactory.createGemBuilder;

public class GemSaxBuilderTest {
    private AbstractGemBuilder builder;

    @Test(expectedExceptions = GemException.class)
    public void testFundException() throws GemException {
        String type = "SAX";
        builder = createGemBuilder(type);
        builder.buildSetGems("");
    }

    @Test
    public void testBuildSetGems() throws GemException {
        String[] expectedNames = new String[]{"2017-05-02", "2014-06-10", "2021-01-10", "2020-04-03", "2019-03-26", "2019-05-04", "2018-02-04", "2015-01-15", "2019-09-10", "2016-07-24", "2021-02-12", "2017-10-23", "2020-04-03", "2016-02-29", "2020-11-11", "2011-02-05"};
        String type = "SAX";
        builder = createGemBuilder(type);
        builder.buildSetGems("src\\main\\resources\\file\\gems.xml");
        Object[] actualColors = builder.getGems().stream().map(i -> i.getDate().toString()).toArray();
        Assert.assertEqualsNoOrder(expectedNames, actualColors);
    }
}