package in.omgss.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import in.omgss.R;
import in.omgss.pojo.responses.categories.Product;

public class ProductsAdapter extends PagerAdapter {

    private final AppCompatActivity mActivity;
    private final ArrayList<Product> mData;


    public ProductsAdapter(AppCompatActivity mActivity, ArrayList<Product> mData) {
        this.mActivity = mActivity;
        this.mData = mData;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        //inflating layout
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.list_item_home_items, container, false);

        String imageUrl;
        if (mData.get(position).getThumbnail() != null)
            imageUrl = mData.get(position).getThumbnail();
        else
            imageUrl = mData.get(position).getImage();

        ((SimpleDraweeView) itemView.findViewById(R.id.iv_item)).setImageURI(imageUrl);

        ((TextView) itemView.findViewById(R.id.tv_item_name)).setText(mData.get(position).getName());
        ((TextView) itemView.findViewById(R.id.tvDiscount)).setText(mData.get(position).getDiscount());
        ((TextView) itemView.findViewById(R.id.tvdiscountprice)).setText(String.valueOf(mData.get(position).getSaleprice()));
        ((TextView) itemView.findViewById(R.id.tv_original)).setText(String.valueOf(mData.get(position).getActualprice()));
        ((TextView) itemView.findViewById(R.id.tv_original)).setPaintFlags(((TextView) itemView.findViewById(R.id.tv_original)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        container.addView(itemView);
        itemView.setTag(position);
        return itemView;
    }


    @Override
    public int getCount() {
        return mData.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
