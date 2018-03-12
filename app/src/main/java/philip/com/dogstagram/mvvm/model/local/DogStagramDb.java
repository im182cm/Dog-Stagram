package philip.com.dogstagram.mvvm.model.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by 1000140 on 2018. 1. 22..
 */

@Database(entities = {BreedEntity.class, RandomBreedImageEntity.class, BreedImageEntity.class}, version = 1, exportSchema = false)
public abstract class DogStagramDb extends RoomDatabase {
    abstract public BreedDao breedDAO();

    abstract public RandomBreedImageDao randomBreedImageDao();

    abstract public BreedImageDao breedImageDao();
}
