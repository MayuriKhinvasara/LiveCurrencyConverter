package mk.android.com.livecurrencyconvertor.data.rest;

import io.reactivex.Single;
import mk.android.com.livecurrencyconvertor.data.dto.CurrencyBaseDTO;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyService {

    @GET("latest")
    Single<CurrencyBaseDTO> getRepositories(@Query("base") String base
    );
}
