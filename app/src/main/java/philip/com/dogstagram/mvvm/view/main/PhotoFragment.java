package philip.com.dogstagram.mvvm.view.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import philip.com.dogstagram.R;

import static android.app.Activity.RESULT_OK;
import static philip.com.dogstagram.util.Constant.RC_CAMERA_PERMISSION;
import static philip.com.dogstagram.util.Constant.RC_IMAGE_CAPTURE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class PhotoFragment extends Fragment {
    private static final String LOG_TAG = PhotoFragment.class.getSimpleName();

    private RequestManager mRequestManager;
    private Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestManager = Glide.with(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_photo, container, false);

        final ImageView imageView = view.findViewById(R.id.image_photo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Button doneButton = view.findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = view.findViewById(R.id.edit_content);
                editText.setEnabled(false);
                editText.setBackgroundColor(Color.TRANSPARENT);
                v.setVisibility(View.GONE);
                imageView.setClickable(false);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Log.d(LOG_TAG, "dispatchTakePictureIntent");
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d(LOG_TAG, "no permission");
            requestPermissions(new String[]{Manifest.permission.CAMERA}, RC_CAMERA_PERMISSION);
        } else {
            Log.d(LOG_TAG, "permission");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            uri = Uri.fromFile(getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            startActivityForResult(intent, RC_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        if (requestCode == RC_IMAGE_CAPTURE) {
            ImageView imageView = getView().findViewById(R.id.image_photo);
            mRequestManager.asBitmap().load(uri).into(imageView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(LOG_TAG, "onRequestPermissionsResult");
        if (requestCode == RC_CAMERA_PERMISSION) {
            Log.d(LOG_TAG, "RC_CAMERA_PERMISSION");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(LOG_TAG, "PERMISSION_GRANTED");
                dispatchTakePictureIntent();
            }
        }
    }

    private File getOutputMediaFile() {
        Log.d(LOG_TAG, "getOutputMediaFile()");
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), "DogStagram");

        Log.d(LOG_TAG, "file : " + mediaStorageDir.toString());
        Log.d(LOG_TAG, "Environment.DIRECTORY_PICTURES : " + Environment.DIRECTORY_PICTURES);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(LOG_TAG, "!mediaStorageDir.mkdirs()");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }
}
