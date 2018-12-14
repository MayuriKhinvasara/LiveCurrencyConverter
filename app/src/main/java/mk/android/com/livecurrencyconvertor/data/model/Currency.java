package mk.android.com.livecurrencyconvertor.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Currency {

    @SerializedName("name")
    public abstract String name();

    @SerializedName("value")
    public abstract Double value();

    public static Builder builder() {
        return new AutoValue_Currency.Builder();
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setValue(Double value);
        public abstract Builder setName(String name);
        public abstract Currency build();
    }

    public static Currency create(String name, Double value){
        return new AutoValue_Currency(name, value);
    }

    public static TypeAdapter<Currency> typeAdapter(Gson gson) {
        return new AutoValue_Currency.GsonTypeAdapter(gson);
    }
}
