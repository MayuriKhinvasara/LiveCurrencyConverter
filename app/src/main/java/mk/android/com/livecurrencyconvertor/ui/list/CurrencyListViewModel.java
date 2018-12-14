package mk.android.com.livecurrencyconvertor.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subscribers.DisposableSubscriber;
import mk.android.com.livecurrencyconvertor.data.model.Currency;
import mk.android.com.livecurrencyconvertor.data.rest.CurrencyRepository;
import mk.android.com.livecurrencyconvertor.util.ConstantsKt;
import mk.android.com.livecurrencyconvertor.util.Mappers;


public class CurrencyListViewModel extends ViewModel {

    private static final long CURRENCY_REFRESH_INTERVAL_SECONDS = 1;
    private final CurrencyRepository currencyRepository;
    private DisposableSubscriber<List<Currency>> disposableSubscriber;

    private final MutableLiveData<List<Currency>> currencies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> currencyLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private String currentBase = ConstantsKt.getDEFAULT_CURRENCY_BASE();
    private double amount = ConstantsKt.getDEFAULT_CURRENCY_BASE_AMOUNT();

    @Inject
    public CurrencyListViewModel(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        disposableSubscriber = getSubscriber();
        fetchCurrencies();
    }

    LiveData<List<Currency>> getCurrencies() {
        return currencies;
    }
    LiveData<Boolean> getError() {
        return currencyLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }
    private  PublishSubject<Long> publishSubject = PublishSubject.create();

    private void fetchCurrencies() {
        loading.setValue(true);

        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        /* Use rx flowable interval function to do periodic updates */

        Flowable.interval(0, CURRENCY_REFRESH_INTERVAL_SECONDS, TimeUnit.SECONDS)
                    .subscribeOn(scheduler)
                    .mergeWith(publishSubject.toFlowable(BackpressureStrategy.DROP))
                    .onBackpressureDrop()
                    .concatMap(n -> {
                        return currencyRepository.getCurrency(currentBase)
                                .map(currencyBaseDTO -> Mappers.mapRemoteToLocal(currencyBaseDTO, currentBase))
                                .toFlowable();
                    })
                    .observeOn(Schedulers.io())
                    .map(list -> {
                        return ((amount != ConstantsKt.getDEFAULT_CURRENCY_BASE_AMOUNT()) ? convertCurrency(list, amount) : list);
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(disposableSubscriber);
        }

    @NonNull
    private List<Currency> convertCurrency(List<Currency> list, double amount) {
        //Sample code to modify as per amount
        List<Currency> modifiedList = new ArrayList<>();
        for (Currency c : list) {
            Currency currency = Currency.builder().setName(c.name()).setValue(c.value() * amount).build();
            modifiedList.add(currency);
        }
        return modifiedList;
    }

    private DisposableSubscriber<List<Currency>> getSubscriber() {
        /* Subscriber to update the data at regular intervals */
        return  disposableSubscriber = new DisposableSubscriber<List<Currency>>() {
           @Override
           public void onNext(List<Currency> value) {
               currencyLoadError.setValue(false);
               currencies.setValue(value);
               loading.setValue(false);
           }

           @Override
           public void onError(Throwable t) {
               currencyLoadError.setValue(true);
               loading.setValue(false);
           }

           @Override
           public void onComplete() {
           }
       };
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (disposableSubscriber != null) {
            disposableSubscriber.dispose();
        }
    }

    public void setSelectedCurrency(String base) {
        currentBase = base;
        amount = ConstantsKt.getDEFAULT_CURRENCY_BASE_AMOUNT();
    }

    public void setUpdatedAmount(Double newAmount) {
        amount = newAmount;
    }
}
