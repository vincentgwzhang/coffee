package tina.coffee.dozer.convertor;

import java.util.Currency;

import org.dozer.DozerConverter;

public class CurrencyConverter extends DozerConverter<Currency, String> {

    private static final String RMB_CURRENCY_CODE = "RMB";
    private static final String DEFAULT_CURRENCY_CODE = "CNY";

    public CurrencyConverter() {
        super(Currency.class, String.class);
    }

    @Override
    public String convertTo(Currency currency, String currencyCode) {
        return currency == null ? null : currency.getCurrencyCode();
    }

    @Override
    public Currency convertFrom(String currencyCode, Currency currency) {
        try {
            // Fixes "wrong" currency codes.
            if (currencyCode == null || currencyCode.trim().isEmpty() || currencyCode.equals(RMB_CURRENCY_CODE))
                currencyCode = DEFAULT_CURRENCY_CODE;

            return Currency.getInstance(currencyCode);
        } catch (final IllegalArgumentException e) {
            return null;
        }
    }

}