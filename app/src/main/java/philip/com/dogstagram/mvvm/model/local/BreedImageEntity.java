package philip.com.dogstagram.mvvm.model.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by 1000140 on 2018. 1. 22..
 */

@Entity(tableName = "breed_image")
public class BreedImageEntity implements Parcelable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    @ColumnInfo(name = "breed_name")
    public final String breedName;

    @Nullable
    @ColumnInfo(name = "sub_breed_name")
    public final String subBreedNames;

    @NonNull
    @ColumnInfo(name = "image")
    public final String imagePath;

    public BreedImageEntity(@NonNull String breedName, String subBreedNames, String imagePath) {
        this.breedName = breedName;
        this.subBreedNames = subBreedNames == null ? "" : subBreedNames;
        this.imagePath = imagePath;
    }

    protected BreedImageEntity(Parcel in) {
        id = in.readInt();
        breedName = in.readString();
        subBreedNames = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<BreedImageEntity> CREATOR = new Creator<BreedImageEntity>() {
        @Override
        public BreedImageEntity createFromParcel(Parcel in) {
            return new BreedImageEntity(in);
        }

        @Override
        public BreedImageEntity[] newArray(int size) {
            return new BreedImageEntity[size];
        }
    };

    @Override
    public String toString() {
        return "BreedImageEntity{" +
                "breedName='" + breedName + '\'' +
                ", subBreedNames='" + subBreedNames + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(breedName) || TextUtils.isEmpty(imagePath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(breedName);
        dest.writeString(subBreedNames);
        dest.writeString(imagePath);
    }
}
