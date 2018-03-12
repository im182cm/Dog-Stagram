package philip.com.dogstagram.mvvm.model.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by 1000140 on 2018. 1. 22..
 */

@Entity(tableName = "random_breed_image")
public class RandomBreedImageEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    @ColumnInfo(name = "breed_name")
    public final String breedName;

    @ColumnInfo(name = "sub_breed_name")
    public final String subBreedNames;

    @NonNull
    @ColumnInfo(name = "image")
    public final String imagePath;

    public RandomBreedImageEntity(@NonNull String breedName, String subBreedNames, String imagePath) {
        this.breedName = breedName;
        this.subBreedNames = subBreedNames == null ? "" : subBreedNames;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "RandomBreedImageEntity{" +
                "breedName='" + breedName + '\'' +
                ", subBreedNames='" + subBreedNames + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(breedName) || TextUtils.isEmpty(imagePath);
    }
}
