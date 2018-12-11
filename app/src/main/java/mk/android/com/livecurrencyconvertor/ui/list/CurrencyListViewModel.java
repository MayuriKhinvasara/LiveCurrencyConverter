package mk.android.com.livecurrencyconvertor.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import mk.android.com.livecurrencyconvertor.data.model.Currency;
import mk.android.com.livecurrencyconvertor.data.rest.CurrencyRepository;
import mk.android.com.livecurrencyconvertor.util.Mappers;

public class CurrencyListViewModel extends ViewModel {

    private final CurrencyRepository currencyRepository;
    private CompositeDisposable disposable;

    private final MutableLiveData<List<Currency>> repos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public CurrencyListViewModel(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        disposable = new CompositeDisposable();
        fetchRepos();
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

    private void fetchRepos() {
        loading.setValue(true);
        disposable.add(
            currencyRepository.getCurrency()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Mappers::mapRemoteToLocal)
                .subscribeWith(new DisposableSingleObserver<List<Currency>>() {
                    @Override
                    public void onSuccess(List<Currency> value) {
                        repoLoadError.setValue(false);
                        repos.setValue(value);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
