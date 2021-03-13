package in.omgss.pojo;

import androidx.fragment.app.Fragment;

public class ViewPagerModel {

    private Fragment fragment;
    private String title;

    public ViewPagerModel(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
