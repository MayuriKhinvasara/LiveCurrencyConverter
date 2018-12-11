package mk.android.com.livecurrencyconvertor.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import mk.android.com.livecurrencyconvertor.di.util.ViewModelKey;
import mk.android.com.livecurrencyconvertor.ui.detail.DetailsViewModel;
import mk.android.com.livecurrencyconvertor.ui.list.ListViewModel;
import mk.android.com.livecurrencyconvertor.util.ViewModelFactory;

@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel.class)
    abstract ViewModel bindDetailsViewModel(DetailsViewModel detailsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
