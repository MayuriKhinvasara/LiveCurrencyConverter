package mk.android.com.livecurrencyconvertor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mk.android.com.livecurrencyconvertor.data.dto.CurrencyBaseDTO;
import mk.android.com.livecurrencyconvertor.data.model.Currency;

public final class Mappers {

    public static Double parseDouble(String s){
        try {
            return Double.parseDouble(s);
        }catch (NumberFormatException n){
            return 0.0;
        }
    }

    public static List<Currency> mapRemoteToLocal(CurrencyBaseDTO currencyBaseDTO){
        List<Currency> list = new ArrayList<>();
        for(Map.Entry<String, Double> current: currencyBaseDTO.rates().entrySet())
            list.add(Currency.create(current.getKey(), current.getValue()));
        return list;
    }

}