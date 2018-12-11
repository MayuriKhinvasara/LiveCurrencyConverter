package mk.android.com.livecurrencyconvertor.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mk.android.com.livecurrencyconvertor.ui.main.MainActivity;
import mk.android.com.livecurrencyconvertor.ui.main.MainFragmentBindingModule;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
