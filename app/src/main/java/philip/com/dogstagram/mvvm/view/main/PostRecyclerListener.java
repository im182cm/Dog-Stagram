package philip.com.dogstagram.mvvm.view.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by 1000140 on 2018. 1. 31..
 */

interface PostRecyclerListener {
    void onClick(@NonNull String breed, @Nullable String subBreed, @NonNull String imagePath);
}
