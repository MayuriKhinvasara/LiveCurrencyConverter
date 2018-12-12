package mk.android.com.livecurrencyconvertor.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

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
import mk.android.com.livecurrencyconvertor.util.Mappers;

public class CurrencyListViewModel extends ViewModel {

    private final CurrencyRepository currencyRepository;
    DisposableSubscriber<List<Currency>> disposableSubscriber;

    private final MutableLiveData<List<Currency>> repos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public CurrencyListViewModel(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        fetchCurrencies();
    }

    LiveData<List<Currency>> getRepos() {
        return repos;
    }
    LiveData<Boolean> getError() {
        return repoLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }
    private  PublishSubject<Long> publishSubject = PublishSubject.create();

    private void fetchCurrencies() {
        loading.setValue(true);
            Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
         disposableSubscriber = new DisposableSubscriber<List<Currency>>() {
            @Override
            public void onNext(List<Currency> value) {
                repoLoadError.setValue(false);
                repos.setValue(value);
                loading.setValue(false);
            }

            @Override
            public void onError(Throwable t) {
                repoLoadError.setValue(true);
                loading.setValue(false);
            }

            @Override
            public void onComplete() {
            }
        };
        Flowable.interval(1, TimeUnit.SECONDS)
                    .subscribeOn(scheduler)
                    .mergeWith(publishSubject.toFlowable(BackpressureStrategy.DROP))
                    .onBackpressureDrop()
                    .concatMap(n -> {
                           return currencyRepository.getCurrency()
                                    .map(Mappers::mapRemoteToLocal)
                                    .toFlowable();
                                   })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(disposableSubscriber);
        }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (disposableSubscriber != null) {
            disposableSubscriber.dispose();
        }
    }
}
