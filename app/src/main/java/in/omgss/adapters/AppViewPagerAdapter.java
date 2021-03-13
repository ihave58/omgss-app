package in.omgss.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import in.omgss.pojo.ViewPagerModel;

public class AppViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<ViewPagerModel> mFragmentList;

    public AppViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<ViewPagerModel> fragmentList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentList = fragmentList;
    }


    @NotNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position).getFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = mFragmentList.get(position).getTitle();
        return title != null ? title : "";
    }


    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }


}
