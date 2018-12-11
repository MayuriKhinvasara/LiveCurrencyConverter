package mk.android.com.livecurrencyconvertor.di.module;

import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mk.android.com.livecurrencyconvertor.data.rest.CurrencyService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    public static final String BASE_URL = "https://revolut.duckdns.org/";

    @Singleton
    @Provides
    static Retrofit provideRetrofit() {

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                new GsonBuilder()
                        .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                        .create());


        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Singleton
    @Provides
    static CurrencyService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(CurrencyService.class);
    }
}
