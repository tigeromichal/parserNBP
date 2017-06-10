package pl.parser.nbp;

import org.junit.Assert;
import org.junit.Test;

public class ExchangeRateContainerTest {

    @Test
    public void testAdd() {
        ExchangeRateContainer container = new ExchangeRateContainer();
        Assert.assertEquals(container.getSize(), 0);
        ExchangeRate rate = new ExchangeRate("EUR", 5.67, 3.4);
        container.add(rate);
        Assert.assertEquals(container.getSize(), 1);
    }

    @Test
    public void testGetBuyingRateAverage() {
        double delta = 0.00001;
        ExchangeRateContainer container = new ExchangeRateContainer();
        ExchangeRate rate1 = new ExchangeRate("EUR", 5, 3);
        ExchangeRate rate2 = new ExchangeRate("EUR", 2, 1);
        container.add(rate1);
        container.add(rate2);
        Assert.assertEquals(container.getBuyingRateAverage(), 3.5, delta);
    }

    @Test
    public void testGetSellingRateStandardDeviation() {
        double delta = 0.00001;
        ExchangeRateContainer container = new ExchangeRateContainer();
        ExchangeRate rate1 = new ExchangeRate("EUR", 5, 3);
        ExchangeRate rate2 = new ExchangeRate("EUR", 2, 1);
        container.add(rate1);
        container.add(rate2);
        Assert.assertEquals(container.getSellingRateStandardDeviation(), 1, delta);
    }

}
