package com.pischyk.task3.builder;

import com.pischyk.task3.exception.GemException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.pischyk.task3.builder.GemBuilderFactory.createGemBuilder;

public class GemDomBuilderTest {
    private AbstractGemBuilder builder;

    @Test(expectedExceptions = GemException.class)
    public void testGemException() throws GemException {
        String type = "DOM";
        builder = createGemBuilder(type);
        builder.buildSetGems("");
    }


    @Test
    public void testParsingGems() throws GemException {
        String[] expectedNames = new String[]{"purple", "pink", "blue", "yellow", "pink", "red", "colorless", "purple", "brown", "green", "green", "light blue", "purple", "red", "blue", "pink"};
        String type = "DOM";
        builder = createGemBuilder(type);
        builder.buildSetGems("src\\main\\resources\\file\\gems.xml");
        Object[] actualColors = builder.getGems().stream().map(i -> i.getParameters().getColor()).toArray();
        Assert.assertEqualsNoOrder(expectedNames, actualColors);
    }
}