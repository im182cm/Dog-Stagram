package philip.com.dogstagram.mvvm.view.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import philip.com.dogstagram.mvvm.model.Repository;
import philip.com.dogstagram.mvvm.model.Resource;
import philip.com.dogstagram.mvvm.model.local.BreedEntity;
import philip.com.dogstagram.mvvm.model.local.RandomBreedImageEntity;

/**
 * Created by 1000140 on 2018. 1. 30..
 */

public class MainViewModel extends ViewModel {
    private final LiveData<Resource<List<BreedEntity>>> mBreeds;
    private LiveData<Resource<List<RandomBreedImageEntity>>> mRandomBreedImages;
    @Inject
    Repository mRepository;

    @SuppressWarnings("unchecked")
    @Inject
    public MainViewModel(Repository repository) {
        mBreeds = repository.loadBreeds();
        mRandomBreedImages = repository.fetchRandomBreedImage(null, null);
    }

    public LiveData<Resource<List<BreedEntity>>> getmBreeds() {
        return mBreeds;
    }

    public LiveData<Resource<List<RandomBreedImageEntity>>> getmRandomBreedImages() {
        String breed = null;
        String subbreed = null;
        if (mBreeds != null && mBreeds.getValue() != null && mBreeds.getValue().data != null && mBreeds.getValue().data.size() > 0) {
            BreedEntity breedEntity = pickRandomEntity(mBreeds.getValue().data);
            breed = breedEntity.breedName;
            subbreed = breedEntity.subBreedNames;
        }

        if (!TextUtils.isEmpty(breed)) {
            mRandomBreedImages = mRepository.fetchRandomBreedImage(breed, subbreed);
        }

        return mRandomBreedImages;
    }

    /**
     * Get random breed and sub breed from list.
     * Sub breed can contains many strings with comma and space(", "), so split if it contains those and put just one sub breed to subBreedNames.
     */
    private BreedEntity pickRandomEntity(@NonNull List<BreedEntity> list) {
        if (list.size() == 0) {
            return new BreedEntity(null, null);
        }
        Random random = new Random();
        BreedEntity breedEntity = list.get(random.nextInt(list.size()));

        if (TextUtils.isEmpty(breedEntity.subBreedNames) && !breedEntity.subBreedNames.contains(",")) {
            return breedEntity;
        }

        String[] subBreeds = breedEntity.subBreedNames.split(", ");
        breedEntity.subBreedNames = subBreeds[random.nextInt(subBreeds.length)];

        return breedEntity;
    }
}
