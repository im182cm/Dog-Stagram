package philip.com.dogstagram.mvvm.model.remote;

import android.arch.lifecycle.LiveData;

import philip.com.dogstagram.mvvm.model.remote.dto.BaseDto;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("/api/breeds/list/all")
    LiveData<ApiResponse<BaseDto>> getAllBreeds();

    @GET("/api/breed/{breed}/images/random")
    LiveData<ApiResponse<BaseDto>> getBreedRandomImages(@Path("breed") String breed);

    @GET("/api/breed/{breed}/{subbreed}/images/random")
    LiveData<ApiResponse<BaseDto>> getSubBreedRandomImages(@Path("breed") String breed, @Path("subbreed") String subbreed);

    @GET("/api/breed/{breed}/images")
    LiveData<ApiResponse<BaseDto>> getBreedImages(@Path("breed") String breed);

    @GET("/api/breed/{breed}/{subbreed}/images")
    LiveData<ApiResponse<BaseDto>> getSubBreedImages(@Path("breed") String breed, @Path("subbreed") String subbreed);
}
