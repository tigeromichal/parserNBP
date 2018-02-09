package pl.parser.nbp;

import org.junit.Assert;
import org.junit.Test;

public class ExchangeRateTest {

    private double delta = 0.00001;

    @Test
    public void testConstructor() {
        ExchangeRate rate = new ExchangeRate("EUR", 5.67, 3.4);
        Assert.assertEquals("EUR", rate.getCurrencyCode());
        Assert.assertEquals(5.67, rate.getBuyingRate(), delta);
        Assert.assertEquals(3.4, rate.getSellingRate(), delta);
    }

}
