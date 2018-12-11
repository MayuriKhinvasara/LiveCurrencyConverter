package mk.android.com.livecurrencyconvertor.ui.main

import android.os.Bundle

import mk.android.com.livecurrencyconvertor.R
import mk.android.com.livecurrencyconvertor.base.BaseActivity
import mk.android.com.livecurrencyconvertor.ui.list.ListFragment

class MainActivity : BaseActivity() {

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.screenContainer, ListFragment()).commit()
    }
}
