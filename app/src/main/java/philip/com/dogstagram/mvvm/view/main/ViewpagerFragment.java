package philip.com.dogstagram.mvvm.view.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import philip.com.dogstagram.R;
import philip.com.dogstagram.mvvm.model.Resource;
import philip.com.dogstagram.mvvm.model.local.RandomBreedImageEntity;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ViewpagerFragment extends DaggerFragment {
    private static final String LOG_TAG = ViewpagerFragment.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ViewPagerViewModel mViewPagerViewModel;
    private RequestManager mRequestManager;
    private HorizontalInfiniteCycleViewPager mInfiniteViewPager;
    private Button mButton;
    private boolean mAutoScrolling = false;

    @Inject
    public ViewpagerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestManager = Glide.with(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);

        mInfiniteViewPager = view.findViewById(R.id.viewpager);
        mButton = view.findViewById(R.id.button_auto_scroll);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAutoScrolling) {
                    mAutoScrolling = false;
                    mButton.setText(getString(R.string.button_start_auto_scroll));
                    mInfiniteViewPager.stopAutoScroll();
                } else {
                    mAutoScrolling = true;
                    mButton.setText(getString(R.string.button_stop_auto_scroll));
                    mInfiniteViewPager.startAutoScroll(true);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewPagerViewModel.class);
        mViewPagerViewModel.getmRandomBreedImages().observe(this, new Observer<Resource<List<RandomBreedImageEntity>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<RandomBreedImageEntity>> listResource) {
                if (listResource.data != null) {
                    ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(mRequestManager, listResource.data);
                    mInfiniteViewPager.setAdapter(imagePagerAdapter);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mAutoScrolling) {
            mInfiniteViewPager.stopAutoScroll();
        }
        super.onPause();
    }
}
