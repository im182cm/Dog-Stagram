package philip.com.dogstagram.mvvm.view.main;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;

import java.util.List;

import philip.com.dogstagram.R;
import philip.com.dogstagram.mvvm.model.local.RandomBreedImageEntity;
import philip.com.dogstagram.util.SquareImageView;

/**
 * Created by 1000140 on 2018. 2. 5..
 */

public class ImagePagerAdapter extends PagerAdapter {
    private RequestManager mRequestManager;
    private List<RandomBreedImageEntity> mRandomBreedImageEntityList;

    public ImagePagerAdapter(RequestManager mRequestManager, List<RandomBreedImageEntity> mRandomBreedImageEntityList) {
        this.mRequestManager = mRequestManager;
        this.mRandomBreedImageEntityList = mRandomBreedImageEntityList;
    }

    @Override
    public int getCount() {
        return mRandomBreedImageEntityList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((SquareImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_item);
        mRequestManager.asBitmap().load(mRandomBreedImageEntityList.get(position).imagePath).transition(BitmapTransitionOptions.withCrossFade()).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((SquareImageView) object);
    }
}