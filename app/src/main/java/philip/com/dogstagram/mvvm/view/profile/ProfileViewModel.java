package philip.com.dogstagram.mvvm.view.profile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import philip.com.dogstagram.mvvm.model.Repository;
import philip.com.dogstagram.mvvm.model.Resource;
import philip.com.dogstagram.mvvm.model.local.BreedImageEntity;

/**
 * Created by 1000140 on 2018. 1. 30..
 */

public class ProfileViewModel extends ViewModel {
    private BreedImageEntity mBreedImageEntity;
    @Inject
    Repository mRepository;

    @SuppressWarnings("unchecked")
    @Inject
    public ProfileViewModel(Repository repository) {
    }

    public LiveData<Resource<List<BreedImageEntity>>> getmBreedImages() {
        return mRepository.fetchBreedImages(mBreedImageEntity.breedName, mBreedImageEntity.subBreedNames);
    }

    public void setmBreedEntity(BreedImageEntity breedImageEntity) {
        this.mBreedImageEntity = breedImageEntity;
    }
}
