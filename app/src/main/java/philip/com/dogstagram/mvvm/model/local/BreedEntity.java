package philip.com.dogstagram.mvvm.model.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by 1000140 on 2018. 1. 22..
 */

@Entity(tableName = "breeds")
public class BreedEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "breed_name")
    public final String breedName;

    @ColumnInfo(name = "sub_breed_name")
    public String subBreedNames;

    public BreedEntity(String breedName, String subBreedNames) {
        this.breedName = breedName;
        this.subBreedNames = subBreedNames;
    }

    @Override
    public String toString() {
        return "BreedEntity{" +
                "breedName='" + breedName + '\'' +
                ", subBreedNames='" + subBreedNames + '\'' +
                '}';
    }
}
