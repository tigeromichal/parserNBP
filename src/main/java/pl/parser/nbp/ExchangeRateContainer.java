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
    
    public double getBuyingRateAverage() {
        double sum = 0.0;
        for (int i = 0; i < getSize(); i++) {
            sum += exchangeRates.get(i).getBuyingRate();
        }
        return sum / getSize();
    }
    
    private double getSellingRateAverage() {
        double sum = 0.0;
        for (int i = 0; i < getSize(); i++) {
            sum += exchangeRates.get(i).getSellingRate();
        }
        return sum / getSize();
    }
    
    public double getSellingRateStandardDeviation() {
        double average = getSellingRateAverage();
        double sum = 0.0;
        for (int i = 0; i < getSize(); i++) {
            sum += Math.pow(exchangeRates.get(i).getSellingRate() - average, 2);
        }
        return Math.pow(sum / getSize(), 0.5);
    }

    private List<ExchangeRate> exchangeRates;

}
