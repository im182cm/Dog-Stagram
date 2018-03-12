package philip.com.dogstagram.mvvm.model.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by 1000140 on 2018. 1. 22..
 */

@Dao
public interface BreedImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBreedImage(List<BreedImageEntity> breedImageEntity);

    @Query("SELECT * from breed_image where breed_name = :breed_name and sub_breed_name = :sub_breed_name")
    LiveData<List<BreedImageEntity>> loadBreedImages(String breed_name, String sub_breed_name);
}
