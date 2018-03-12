package philip.com.dogstagram.mvvm.model;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import philip.com.dogstagram.AppExecutors;
import philip.com.dogstagram.mvvm.model.local.BreedEntity;
import philip.com.dogstagram.mvvm.model.local.BreedImageEntity;
import philip.com.dogstagram.mvvm.model.local.DogStagramDb;
import philip.com.dogstagram.mvvm.model.local.RandomBreedImageEntity;
import philip.com.dogstagram.mvvm.model.remote.ApiInterface;
import philip.com.dogstagram.mvvm.model.remote.ApiResponse;
import philip.com.dogstagram.mvvm.model.remote.dto.BaseDto;

@Singleton
public class Repository {
    private static final String LOG_TAG = Repository.class.getSimpleName();
    private static Repository INSTANCE = null;
    private final ApiInterface mApiInterface;
    private final DogStagramDb mDogStagramDb;
    private final AppExecutors appExecutors;

    @Inject
    public Repository(DogStagramDb dogStagramDb, ApiInterface apiInterface, AppExecutors appExecutors) {
        this.mDogStagramDb = dogStagramDb;
        this.mApiInterface = apiInterface;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<BreedEntity>>> loadBreeds() {
        return new NetworkBoundResource<List<BreedEntity>, BaseDto>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull BaseDto baseDto) {
                if (!baseDto.isSuccess()) {
                    return;
                }

                Map<String, List<String>> breeds = (Map<String, List<String>>) baseDto.getMessage();
                List<BreedEntity> breedEntityList = new ArrayList<>();
                for (String key : breeds.keySet()) {
                    breedEntityList.add(new BreedEntity(key, breeds.get(key).toString().substring(1, breeds.get(key).toString().length() - 1)));
                }
                mDogStagramDb.breedDAO().insertBreed(breedEntityList);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<BreedEntity> data) {
                return (data == null) || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<BreedEntity>> loadFromDb() {
                return mDogStagramDb.breedDAO().loadBreeds();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseDto>> createCall() {
                return mApiInterface.getAllBreeds();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<RandomBreedImageEntity>>> fetchRandomBreedImage(final String breed, final String subBreed) {
        return new NetworkBoundResource<List<RandomBreedImageEntity>, BaseDto>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull BaseDto baseDto) {
                if (!baseDto.isSuccess()) {
                    return;
                }

                String imagePath = (String) baseDto.getMessage();
                mDogStagramDb.randomBreedImageDao().insertRandomBreedImage(new RandomBreedImageEntity(breed, subBreed, imagePath));
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RandomBreedImageEntity> data) {
                return !TextUtils.isEmpty(breed);
            }

            @NonNull
            @Override
            protected LiveData<List<RandomBreedImageEntity>> loadFromDb() {
                return mDogStagramDb.randomBreedImageDao().loadRandomBreedImage();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseDto>> createCall() {
                if (TextUtils.isEmpty(subBreed)) {
                    return mApiInterface.getBreedRandomImages(breed);
                } else {
                    return mApiInterface.getSubBreedRandomImages(breed, subBreed);
                }
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<BreedImageEntity>>> fetchBreedImages(@NonNull final String breed, @Nullable final String subBreed) {
        return new NetworkBoundResource<List<BreedImageEntity>, BaseDto>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull BaseDto baseDto) {
                List<String> imageList = (List<String>) baseDto.getMessage();
                if (!baseDto.isSuccess() || imageList == null || imageList.isEmpty()) {
                    return;
                }

                List<BreedImageEntity> breedImageEntityList = new ArrayList<>();
                for (String imagePath : imageList) {
                    breedImageEntityList.add(new BreedImageEntity(breed, subBreed, imagePath));
                }
                mDogStagramDb.breedImageDao().insertBreedImage(breedImageEntityList);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<BreedImageEntity> data) {
                return !TextUtils.isEmpty(breed);
            }

            @NonNull
            @Override
            protected LiveData<List<BreedImageEntity>> loadFromDb() {
                return mDogStagramDb.breedImageDao().loadBreedImages(breed, subBreed);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseDto>> createCall() {
                if (TextUtils.isEmpty(subBreed))
                    return mApiInterface.getBreedImages(breed);
                else
                    return mApiInterface.getSubBreedImages(breed, subBreed);
            }
        }.asLiveData();
    }
}
