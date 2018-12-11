package mk.android.com.livecurrencyconvertor.base;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import mk.android.com.livecurrencyconvertor.di.component.ApplicationComponent;
import mk.android.com.livecurrencyconvertor.di.component.DaggerApplicationComponent;

public class BaseApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);

        return component;
    }
}
