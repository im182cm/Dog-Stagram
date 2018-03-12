package philip.com.dogstagram.mvvm.view.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import philip.com.dogstagram.R;
import philip.com.dogstagram.mvvm.model.Resource;
import philip.com.dogstagram.mvvm.model.local.BreedEntity;
import philip.com.dogstagram.mvvm.model.local.BreedImageEntity;
import philip.com.dogstagram.mvvm.model.local.RandomBreedImageEntity;
import philip.com.dogstagram.mvvm.view.profile.ProfileActivity;
import philip.com.dogstagram.util.Constant;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class PostFragment extends DaggerFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String LOG_TAG = PostFragment.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel mainViewModel;
    private PostRecyclerViewAdapter mPostRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RequestManager mRequestManager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    @Inject
    public PostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestManager = Glide.with(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        Context context = view.getContext();

        mSwipeRefreshLayout = view.findViewById(R.id.layout_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mPostRecyclerViewAdapter = new PostRecyclerViewAdapter(mRequestManager, new PostRecyclerListener() {
            @Override
            public void onClick(@NonNull String breed, @Nullable String subBreed, @NonNull String imagePath) {
                if (TextUtils.isEmpty(breed)){
                    return;
                }

                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra(Constant.EXTRA_BREED_ENTITY, new BreedImageEntity(breed, subBreed, imagePath));
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mPostRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        mainViewModel.getmBreeds().observe(this, new Observer<Resource<List<BreedEntity>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<BreedEntity>> listResource) {
                Log.d(LOG_TAG, listResource.status.name());
                if (listResource.data != null) {
                    Log.d(LOG_TAG, listResource.data.toString());

                    fetchRandomImage();
                }
            }
        });
        fetchRandomImage();

    }

    private void fetchRandomImage() {
        mainViewModel.getmRandomBreedImages().observe(this, new Observer<Resource<List<RandomBreedImageEntity>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<RandomBreedImageEntity>> listResource) {
                if (listResource.data == null) {
                    return;
                }

                mPostRecyclerViewAdapter.setmRandomBreedImages(listResource.data);

                mRecyclerView.scrollToPosition(listResource.data.size() - 1);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        fetchRandomImage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unregister the adapter.
        // Because the RecyclerView won't unregister the adapter, the
        // ViewHolders are very likely leaked.
        mRecyclerView.setAdapter(null);
    }
}
