package mk.android.com.livecurrencyconvertor.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import mk.android.com.livecurrencyconvertor.R;
import mk.android.com.livecurrencyconvertor.base.BaseFragment;
import mk.android.com.livecurrencyconvertor.data.model.Currency;
import mk.android.com.livecurrencyconvertor.util.ViewModelFactory;

public class ListFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView listView;
    @BindView(R.id.tv_error)
    TextView errorTextView;
    @BindView(R.id.loading_view)
    View loadingView;

    @Inject
    ViewModelFactory viewModelFactory;
    private CurrencyListViewModel viewModel;
    private CurrencyListAdapter currencyListAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.list_currency;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyListViewModel.class);

        listView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
         currencyListAdapter = new CurrencyListAdapter(viewModel, this, new CurrencyUpdatedListener() {
             @Override
             public void onCurrencySelected(Currency currency) {
                 listView.smoothScrollToPosition(0);
                 viewModel.setSelectedCurrency(currency.name());
                 Toast.makeText(getContext(), " New currency selected '"+currency.name()+"'",Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onAmountUpdated(Double newAmount) {
                 viewModel.setUpdatedAmount(newAmount);
                 listView.clearFocus();
             }
         });
        listView.setAdapter(currencyListAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));


        observableViewModel();
    }

    private void observableViewModel() {
        viewModel.getCurrencies().observe(this, currencyList -> {
            if(currencyList != null) listView.setVisibility(View.VISIBLE);
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) if(isError) {
                errorTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                errorTextView.setText("An Error Occurred While Loading Data!");
            }else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                }
            }
        });
    }
}
