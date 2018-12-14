package mk.android.com.livecurrencyconvertor.ui.list;


import mk.android.com.livecurrencyconvertor.data.model.Currency;

public interface CurrencyUpdatedListener {

    void onCurrencySelected(Currency currency);
    void onAmountUpdated(Double currency);
}
