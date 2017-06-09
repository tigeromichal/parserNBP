package pl.parser.nbp;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Michał Piasecki
 */
public class ExchangeRateContainer {

    public ExchangeRateContainer() {
        exchangeRates = new ArrayList<ExchangeRate>();
    }

    public void addExchangeRate(final ExchangeRate obj) {
        exchangeRates.add(obj);
    }

    private List<ExchangeRate> exchangeRates;

}
