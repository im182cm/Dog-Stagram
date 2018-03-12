package philip.com.dogstagram.mvvm.view.profile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import philip.com.dogstagram.R;
import philip.com.dogstagram.mvvm.model.Resource;
import philip.com.dogstagram.mvvm.model.local.BreedImageEntity;
import philip.com.dogstagram.util.Constant;
import philip.com.dogstagram.util.SpacesItemDecoration;

public class ProfileActivity extends DaggerAppCompatActivity {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RequestManager mRequestManager;
    private ProfileRecyclerViewAdapter mProfileRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    @Inject
    public ProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!getIntent().hasExtra(Constant.EXTRA_BREED_ENTITY)) {
            return;
        }

        BreedImageEntity breedImageEntity = (BreedImageEntity) getIntent().getParcelableExtra(Constant.EXTRA_BREED_ENTITY);

        Log.i("TEST", getIntent().getParcelableExtra(Constant.EXTRA_BREED_ENTITY).toString());
        mRequestManager = Glide.with(this);
        initLayout(breedImageEntity);

        ProfileViewModel profileViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel.class);
        profileViewModel.setmBreedEntity(breedImageEntity);
        profileViewModel.getmBreedImages().observe(this, new Observer<Resource<List<BreedImageEntity>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<BreedImageEntity>> listResource) {
                Log.i("TEST", listResource.status.name());
                if (listResource == null) {
                    Log.i("TEST", "listResource is null");
                    return;
                }

                mProfileRecyclerViewAdapter.setmBreedImages(listResource.data);
            }
        });
    }

    private void initLayout(BreedImageEntity breedImageEntity) {
        ImageView imageViewProfile = findViewById(R.id.image_profile);
        TextView textViewSubBreed = findViewById(R.id.text_subbreed);
        TextView textViewBreed = findViewById(R.id.text_breed);

        if (TextUtils.isEmpty(breedImageEntity.subBreedNames)) {
            textViewSubBreed.setText(breedImageEntity.breedName);
            textViewBreed.setVisibility(View.GONE);
        } else {
            textViewBreed.setText(breedImageEntity.breedName);
            textViewSubBreed.setText(breedImageEntity.subBreedNames);
        }
        mRequestManager.asBitmap().load(breedImageEntity.imagePath).apply(RequestOptions.circleCropTransform()).transition(BitmapTransitionOptions.withCrossFade()).into(imageViewProfile);

        mRecyclerView = findViewById(R.id.recycler_profile_images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(3));
        mProfileRecyclerViewAdapter = new ProfileRecyclerViewAdapter(mRequestManager);
        mRecyclerView.setAdapter(mProfileRecyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the adapter.
        // Because the RecyclerView won't unregister the adapter, the
        // ViewHolders are very likely leaked.
        mRecyclerView.setAdapter(null);
    }
}
