package mk.android.com.livecurrencyconvertor.ui.list;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mk.android.com.livecurrencyconvertor.R;
import mk.android.com.livecurrencyconvertor.data.model.Currency;
import mk.android.com.livecurrencyconvertor.ui.util.UserInputListenerEditText;
import mk.android.com.livecurrencyconvertor.util.CurrencyUtil;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {

    public CurrencyUpdatedListener currencyUpdatedListener;
    private final List<Currency> data = new ArrayList<>();

    CurrencyListAdapter(CurrencyListViewModel viewModel, LifecycleOwner lifecycleOwner, CurrencyUpdatedListener currencyUpdatedListener) {
        this.currencyUpdatedListener = currencyUpdatedListener;
        viewModel.getCurrencies().observe(lifecycleOwner, currencyList -> {
            data.clear();
            if (currencyList != null) {
                data.addAll(currencyList);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_details, parent, false);
        return new CurrencyViewHolder(view, currencyUpdatedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.bind(data.get(position));
        if (position == 0)
            holder.editTextCurrencyAmount.requestFocus();
        else
            holder.editTextCurrencyAmount.clearFocus();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class CurrencyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewCurrency)
        TextView textViewCurrency;
        @BindView(R.id.textViewCurrencyFullName)
        TextView textViewCurrencyFullName;
        @BindView(R.id.editTextCurrencyAmount)
        UserInputListenerEditText editTextCurrencyAmount;
        @BindView(R.id.imageViewFlag)
        ImageView imageViewFlag;
        private Currency currency;

        CurrencyViewHolder(View itemView, CurrencyUpdatedListener currencyUpdatedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (currency != null) {
                    currencyUpdatedListener.onCurrencySelected(currency);
                    v.clearFocus();
                }
            });
            editTextCurrencyAmount.setAmountChangeListener(currencyUpdatedListener);
        }

        void bind(Currency currency) {
            this.currency = currency;

            textViewCurrency.setText(currency.name());
            editTextCurrencyAmount.setText(String.format("%.2f", currency.value()));

            imageViewFlag.setImageResource(CurrencyUtil.INSTANCE.getFlagImageResByISO(currency.name()));
            textViewCurrencyFullName.setText(CurrencyUtil.INSTANCE.getCurrencyNameResByISO(currency.name()));
        }
    }


}
