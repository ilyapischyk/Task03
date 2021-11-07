package com.pischyk.task3.builder;

import com.pischyk.task3.exception.GemException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.pischyk.task3.builder.GemBuilderFactory.createGemBuilder;

public class GemStaxBuilderTest {
    private AbstractGemBuilder builder;

    @Test(expectedExceptions = GemException.class)
    public void testFundException() throws GemException {
        String type = "STAX";
        builder = createGemBuilder(type);
        builder.buildSetGems("");
    }


    @Test
    public void testParsingGems() throws GemException {
        String[] expectedNames = new String[]{"Russia", "China", "Brazil", "USA", "Australia", "Brazil", "Russia", "Germany", "USA", "Russia", "Colombia", "USA", "Brazil", "Thailand", "India", "China"};
        String type = "STAX";
        builder = createGemBuilder(type);
        builder.buildSetGems("src\\main\\resources\\file\\gems.xml");
        Object[] actualColors = builder.getGems().stream().map(i -> i.getOrigin()).toArray();
        Assert.assertEqualsNoOrder(expectedNames, actualColors);
    }
}