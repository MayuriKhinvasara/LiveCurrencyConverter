package mk.android.com.livecurrencyconvertor.data.dto;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;


@AutoValue
public abstract class CurrencyBaseDTO {

    CurrencyBaseDTO()
    {

    }

    @SerializedName("base")
    public abstract String base();

    @SerializedName("date")
    public abstract String date();

    @SerializedName("rates")
    public abstract LinkedHashMap<String, Double> rates();

    public static TypeAdapter<CurrencyBaseDTO> typeAdapter(Gson gson) {
        return new AutoValue_CurrencyBaseDTO.GsonTypeAdapter(gson);
    }

}