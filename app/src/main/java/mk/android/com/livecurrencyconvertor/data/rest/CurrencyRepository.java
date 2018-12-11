package mk.android.com.livecurrencyconvertor.data.rest;

import javax.inject.Inject;

import io.reactivex.Single;
import mk.android.com.livecurrencyconvertor.data.dto.CurrencyBaseDTO;

public class CurrencyRepository {

    private final CurrencyService currencyService;

    @Inject
    public CurrencyRepository(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public Single<CurrencyBaseDTO> getCurrency() {
        return currencyService.getRepositories("EUR");
    }

}
