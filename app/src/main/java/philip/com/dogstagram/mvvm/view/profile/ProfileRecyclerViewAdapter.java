package philip.com.dogstagram.mvvm.view.profile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import philip.com.dogstagram.R;
import philip.com.dogstagram.mvvm.model.local.BreedImageEntity;
import philip.com.dogstagram.mvvm.model.local.RandomBreedImageEntity;
import philip.com.dogstagram.util.SquareImageView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link RandomBreedImageEntity} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder> {

    private List<BreedImageEntity> mBreedImages = new ArrayList<>();
    private final RequestManager mRequestManager;

    public ProfileRecyclerViewAdapter(RequestManager mRequestManager) {
        this.mRequestManager = mRequestManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BreedImageEntity breedImageEntity = mBreedImages.get(position);

        setGlideImage(holder.mImageViewImage, breedImageEntity.imagePath, null, BitmapTransitionOptions.withCrossFade());
    }

    @Override
    public int getItemCount() {
        return mBreedImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final SquareImageView mImageViewImage;

        public ViewHolder(View view) {
            super(view);
            mImageViewImage = (SquareImageView) view;
        }
    }

    private void setGlideImage(@NonNull ImageView imageView, @NonNull String imagePath, @Nullable RequestOptions requestOptions, @Nullable TransitionOptions transitionOptions) {
        if (requestOptions == null) {
            requestOptions = new RequestOptions();
        }

        if (transitionOptions == null) {
            transitionOptions = new BitmapTransitionOptions();
        }
        mRequestManager.asBitmap().load(imagePath).apply(requestOptions).transition(transitionOptions).into(imageView);
    }

    public void setmBreedImages(List<BreedImageEntity> items) {
        if (items == null || items.isEmpty() || items.size() == mBreedImages.size()){
            return;
        }

        Log.i("TEST", "breed images\n"+items.toString());

        int count = items.size();
        int previousCount = mBreedImages.size();

        this.mBreedImages = items;
        notifyItemRangeChanged(0, count - previousCount);
    }
}