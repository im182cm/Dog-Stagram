package philip.com.dogstagram.mvvm.view.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import philip.com.dogstagram.mvvm.model.Repository;
import philip.com.dogstagram.mvvm.model.Resource;
import philip.com.dogstagram.mvvm.model.local.RandomBreedImageEntity;

/**
 * Created by 1000140 on 2018. 1. 30..
 */

public class ViewPagerViewModel extends ViewModel {
    private LiveData<Resource<List<RandomBreedImageEntity>>> mRandomBreedImages;

    @SuppressWarnings("unchecked")
    @Inject
    public ViewPagerViewModel(Repository repository) {
        mRandomBreedImages = repository.fetchRandomBreedImage(null, null);
    }

    public LiveData<Resource<List<RandomBreedImageEntity>>> getmRandomBreedImages() {
        return mRandomBreedImages;
    }
}
