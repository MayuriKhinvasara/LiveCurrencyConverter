package mk.android.com.livecurrencyconvertor.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import mk.android.com.livecurrencyconvertor.R;
import mk.android.com.livecurrencyconvertor.base.BaseFragment;
import mk.android.com.livecurrencyconvertor.util.ViewModelFactory;

public class DetailsFragment extends BaseFragment {

    @BindView(R.id.tv_repo_name)
    TextView repoNameTextView;
    @BindView(R.id.tv_repo_description)
    TextView repoDescriptionTextView;
    @BindView(R.id.tv_forks)
    TextView forksTextView;
    @BindView(R.id.tv_stars)
    TextView starsTextView;

    @Inject
    ViewModelFactory viewModelFactory;
    private DetailsViewModel detailsViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.screen_details;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        detailsViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(DetailsViewModel.class);
        detailsViewModel.restoreFromBundle(savedInstanceState);
        displayRepo();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        detailsViewModel.saveToBundle(outState);
    }

    private void displayRepo() {
        detailsViewModel.getSelectedRepo().observe(this, repo -> {
            if (repo != null) {
                repoNameTextView.setText(repo.name);
                repoDescriptionTextView.setText(repo.description);
                forksTextView.setText(String.valueOf(repo.forks));
                starsTextView.setText(String.valueOf(repo.stars));
            }
        });
    }
}
