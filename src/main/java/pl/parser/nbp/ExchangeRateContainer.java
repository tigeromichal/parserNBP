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

    public void add(final ExchangeRate obj) {
        exchangeRates.add(obj);
    }

    public int getSize() {
        return exchangeRates.size();
    }

    public void printAll() {
        for (int i = 0; i < getSize(); i++) {
            System.out.println(exchangeRates.get(i));
        }
    }

    private List<ExchangeRate> exchangeRates;

}
