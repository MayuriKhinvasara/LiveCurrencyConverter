package mk.android.com.livecurrencyconvertor.util

import com.mynameismidori.currencypicker.ExtendedCurrency

object CurrencyUtil {

    fun getFlagImageResByISO(iso: String): Int {
        for (currency in ExtendedCurrency.getAllCurrencies())
            if (currency.code == iso)
                return currency.flag
        return -1
    }

    fun getCurrencyNameResByISO(iso: String): String {
        for (currency in ExtendedCurrency.getAllCurrencies())
            if (currency.code == iso)
                return currency.name
        return ""
    }

}
