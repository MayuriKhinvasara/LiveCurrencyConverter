package mk.android.com.livecurrencyconvertor.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mk.android.com.livecurrencyconvertor.ui.list.ListFragment;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();

}
