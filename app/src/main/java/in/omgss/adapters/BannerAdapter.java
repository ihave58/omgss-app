package in.omgss.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import in.omgss.R;
import in.omgss.pojo.responses.Response;

public class BannerAdapter extends PagerAdapter {

    private final AppCompatActivity mActivity;
    private final ArrayList<Response> mData;


    public BannerAdapter(AppCompatActivity mActivity, ArrayList<Response> mData) {
        this.mActivity = mActivity;
        this.mData = mData;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        //inflating layout
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.list_item_banner, container, false);

        ((SimpleDraweeView) itemView.findViewById(R.id.image)).setImageURI(Uri.parse(mData.get(position).getSliderimage()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mActivity.startActivity(new Intent(mActivity, ItemListActivity.class));
            }
        });


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
