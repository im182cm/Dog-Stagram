package philip.com.dogstagram.mvvm.view.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import philip.com.dogstagram.R;
import philip.com.dogstagram.mvvm.model.local.RandomBreedImageEntity;
import philip.com.dogstagram.util.SquareImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link RandomBreedImageEntity} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {
    private List<RandomBreedImageEntity> mRandomBreedImages = new ArrayList<>();
    private final RequestManager mRequestManager;
    private final PostRecyclerListener mListener;

    public PostRecyclerViewAdapter(RequestManager mRequestManager, PostRecyclerListener postRecyclerListener) {
        this.mRequestManager = mRequestManager;
        this.mListener = postRecyclerListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RandomBreedImageEntity randomBreedImageEntity = mRandomBreedImages.get(position);

        setGlideImage(holder.mImageViewProfile, randomBreedImageEntity.imagePath, RequestOptions.circleCropTransform(), BitmapTransitionOptions.withCrossFade());
        holder.mTextViewId.setText(randomBreedImageEntity.breedName);
        setGlideImage(holder.mImageViewImage, randomBreedImageEntity.imagePath, null, BitmapTransitionOptions.withCrossFade());
        holder.mTextViewBreedTag.setText("#" + randomBreedImageEntity.breedName);
        if (!TextUtils.isEmpty(randomBreedImageEntity.subBreedNames)) {
            holder.mTextViewSubBreedTag.setText("#" + randomBreedImageEntity.subBreedNames);
        }
    }

    @Override
    public int getItemCount() {
        return mRandomBreedImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageViewProfile;
        public final TextView mTextViewId;
        public final SquareImageView mImageViewImage;
        public final TextView mTextViewBreedTag;
        public final TextView mTextViewSubBreedTag;

        public ViewHolder(View view) {
            super(view);
            mImageViewProfile = view.findViewById(R.id.image_postrcy_profile);
            mTextViewId = view.findViewById(R.id.text_postrcy_name);
            mImageViewImage = view.findViewById(R.id.image_item);
            mTextViewBreedTag = view.findViewById(R.id.text_breed_tag);
            mTextViewSubBreedTag = view.findViewById(R.id.text_subbreed_tag);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRandomBreedImages.isEmpty()){
                        return;
                    }

                    RandomBreedImageEntity randomBreedImageEntity = mRandomBreedImages.get(getAdapterPosition());
                    mListener.onClick(randomBreedImageEntity.breedName, randomBreedImageEntity.subBreedNames, randomBreedImageEntity.imagePath);
                }
            });
        }
    }

    public void setmRandomBreedImages(List<RandomBreedImageEntity> items) {
        if (items == null || items.isEmpty() || items.size() == mRandomBreedImages.size()) {
            return;
        }

        Log.i("TEST", "items\n" + items.toString());

        int count = items.size();
        int previousCount = mRandomBreedImages.size();
        this.mRandomBreedImages = items;
        notifyItemRangeChanged(0, count - previousCount);
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
}