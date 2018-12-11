package mk.android.com.livecurrencyconvertor.ui.list;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mk.android.com.livecurrencyconvertor.R;
import mk.android.com.livecurrencyconvertor.data.model.Currency;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.RepoViewHolder>{

    private CurrencySelectedListener currencySelectedListener;
    private final List<Currency> data = new ArrayList<>();

    CurrencyListAdapter(CurrencyListViewModel viewModel, LifecycleOwner lifecycleOwner, CurrencySelectedListener currencySelectedListener) {
        this.currencySelectedListener = currencySelectedListener;
        viewModel.getRepos().observe(lifecycleOwner, repos -> {
            data.clear();
            if (repos != null) {
                data.addAll(repos);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_details, parent, false);
        return new RepoViewHolder(view, currencySelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

  /*  @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }
*/
    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewCurrency)
        TextView textViewCurrency;
        @BindView(R.id.textViewCurrencyFullname)
        TextView textViewCurrencyFullname;
        @BindView(R.id.editTextCurrencyAmount)
        EditText editTextCurrencyAmount;
        @BindView(R.id.imageViewFlag)
        ImageView imageViewFlag;

        private Currency currency;

        RepoViewHolder(View itemView, CurrencySelectedListener currencySelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if(currency != null) {
                    currencySelectedListener.onRepoSelected(currency);
                }
            });
        }

        void bind(Currency currency) {
            this.currency = currency;
            textViewCurrency.setText(currency.name());
            textViewCurrencyFullname.setText(String.valueOf(currency.name()));
            editTextCurrencyAmount.setText(String.valueOf(currency.value()));
          //  imageViewFlag.setText(String.valueOf(currency.name()));
        }
    }
}
