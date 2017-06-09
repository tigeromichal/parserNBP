package pl.parser.nbp;

public class ExchangeRate {
    
    public ExchangeRate() {
    }
    
    public ExchangeRate(final String currencyCode, final double buyingRate, final double sellingRate) {
        this.currencyCode = currencyCode;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }
    
    public String getCurrencyCode() {
        return currencyCode;
    }
    
    public double getBuyingRate() {
        return buyingRate;
    }
    
    public double getSellingRate() {
        return sellingRate;
    }
    
    private String currencyCode = null;
    private double buyingRate;
    private double sellingRate;
    
}
