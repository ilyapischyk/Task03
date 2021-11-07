package com.pischyk.task3.validator;

import com.pischyk.task3.validator.XmlValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class XmlValidatorTest {

    @Test
    public void testValidate() {
        XmlValidator validator = new XmlValidator();
        boolean isRead = validator.validate("src\\main\\resources\\file\\gems.xml");
        Assert.assertTrue(isRead);
    }
}