package mk.android.com.livecurrencyconvertor.util;

import java.text.DecimalFormat;
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

    public static List<Currency> mapRemoteToLocal(CurrencyBaseDTO currencyBaseDTO, String base){
        List<Currency> list = new ArrayList<>();
        //Add 1st row as base, all currencies are with respect to base
        list.add(Currency.builder().setName(base).setValue(1.0D).build());
        for(Map.Entry<String, Double> current: currencyBaseDTO.rates().entrySet()) {
            DecimalFormat df = new DecimalFormat("#.##");
            Double formatedAmount = Double.valueOf(df.format(current.getValue()));
            list.add(Currency.create(current.getKey(), formatedAmount));
        }
        return list;
    }

}