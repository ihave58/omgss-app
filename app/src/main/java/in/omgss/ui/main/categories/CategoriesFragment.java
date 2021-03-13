package in.omgss.ui.main.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.adapters.CategoriesAdapter;
import in.omgss.base.BaseFragment;
import in.omgss.pojo.responses.categories.CategoriesResponse;
import in.omgss.pojo.responses.categories.Response;
import okhttp3.RequestBody;

public class CategoriesFragment extends BaseFragment {


    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    private CategoriesAdapter mAdapter;

    private CategoriesViewModel mCategoriesViewmodel;
    private ArrayList<Response> mData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCategoriesViewmodel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        mCategoriesViewmodel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        mCategoriesViewmodel.getmResendOtpLiveData().observe(getViewLifecycleOwner(), new Observer<CategoriesResponse>() {
            @Override
            public void onChanged(CategoriesResponse responseBody) {
                mData = responseBody.getResponse();
                mAdapter = new CategoriesAdapter(mData);
                rvCategories.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                rvCategories.setAdapter(mAdapter);
            }
        });


        RequestBody body = getMultipartRequestBuilder()
                .addFormDataPart("userid", getUserId())
                .build();
        mCategoriesViewmodel.getCategories(body);

    }


    @Override
    public void initVariables() {

    }

    @Override
    public void setListeners() {

    }
}
