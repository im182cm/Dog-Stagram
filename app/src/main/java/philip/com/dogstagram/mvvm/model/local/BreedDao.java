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
public interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBreed(List<BreedEntity> breedEntityList);

    @Query("SELECT * from breeds")
    LiveData<List<BreedEntity>> loadBreeds();
}
