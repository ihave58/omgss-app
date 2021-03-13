package in.omgss.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.adapters.PastOrdersAdapter;
import in.omgss.base.BaseFragment;
import in.omgss.interfaces.RecyclerItemClickListener;
import in.omgss.pojo.responses.myorders.Response;

public class PastOrdersFragment extends BaseFragment {
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    @BindView(R.id.atv_no_data_found)
    AppCompatTextView atvNoDataFound;

    private PastOrdersAdapter mOrdersAdapter;
    private ArrayList<Response> ordersList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_and_active_orders, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOrdersAdapter = new PastOrdersAdapter(ordersList, new RecyclerItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                if (position >= 0 && position < ordersList.size()) {
                    if (getActivity() instanceof OrdersActivity) {
                        ((OrdersActivity) getActivity()).viewOrderDetails(ordersList.get(position));
                    }
                }
            }
        });
        rvOrders.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvOrders.setAdapter(mOrdersAdapter);

    }


    @Override
    public void initVariables() {
        ordersList = new ArrayList<>();
    }


    @Override
    public void setListeners() {

    }


    public void setOrdersList(List<Response> response) {
        ordersList.clear();
        if (response != null) {
            for (Response data : response) {
                if (data.getOrderstate().equalsIgnoreCase("Delivered") || data.getOrderstate().equalsIgnoreCase("Cancelled"))
                    ordersList.add(data);
            }
        }
        mOrdersAdapter.notifyDataSetChanged();

        atvNoDataFound.setVisibility(ordersList.size() > 0 ? View.GONE : View.VISIBLE);

    }


}
