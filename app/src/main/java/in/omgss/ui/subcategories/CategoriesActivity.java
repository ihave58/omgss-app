package in.omgss.ui.subcategories;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.SubCategoriesAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.interfaces.RecyclerItemClickListener;
import in.omgss.pojo.responses.subcategory.Response;
import in.omgss.pojo.responses.subcategory.SubcategoryResponse;
import in.omgss.ui.main.itemlist.ItemListActivity;
import okhttp3.RequestBody;

public class CategoriesActivity extends BaseActivity implements RecyclerItemClickListener {

    @BindView(R.id.rv_items)
    RecyclerView rvCategories;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private SubCategoriesAdapter mAdapter;
    private CategoriesViewModel mCategoriesViewmodel;
    private ArrayList<Response> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        tvTitle.setText("Sub Categories");
        mCategoriesViewmodel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        mCategoriesViewmodel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());
        mCategoriesViewmodel.getmResendOtpLiveData().observe(this, new Observer<SubcategoryResponse>() {
            @Override
            public void onChanged(SubcategoryResponse responseBody) {
                mData = responseBody.getResponse();
                mAdapter = new SubCategoriesAdapter(responseBody.getResponse(), CategoriesActivity.this);
                rvCategories.setLayoutManager(new LinearLayoutManager(CategoriesActivity.this, RecyclerView.VERTICAL, false));
                rvCategories.setAdapter(mAdapter);
            }
        });
        RequestBody body = getMultipartRequestBuilder()
                .addFormDataPart("catid", String.valueOf(getIntent().getIntExtra("id", 0)))
                .build();
        mCategoriesViewmodel.getCategories(body);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_categories;
    }

    @OnClick(R.id.iv_toolbar_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClicked(int position) {
        startActivity(new Intent(this, ItemListActivity.class).putExtra("catid", String.valueOf(getIntent().getIntExtra("id", 0))).putExtra("subcatid", String.valueOf(mData.get(position).getId())));
    }
}