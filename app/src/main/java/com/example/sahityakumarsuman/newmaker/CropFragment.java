package com.example.sahityakumarsuman.newmaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sahityakumarsuman.newmaker.AllEventBusModel.ImageCropped;
import com.example.sahityakumarsuman.newmaker.utils.BitmapUtils;
import com.example.sahityakumarsuman.newmaker.utils.Constants;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class CropFragment extends Fragment {


    public CropFragment() {

    }

    private CropImageView cropImageView;
    private Button cropbutton;
    private int imageWidth, imageHeight;
    private String image_uri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.crop_fragment_view, null);
        cropImageView = mainView.findViewById(R.id.cropImageView);
        cropbutton = mainView.findViewById(R.id.cropbutton);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels / 2;
        imageHeight = metrics.heightPixels / 2;

        String myStr = getArguments().getString(Constants.IMAGE_PASSS);
        setImage_uri(myStr);

        cropImageView.setImageBitmap(loadImage(myStr));
        cropbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply_crop_image();
            }
        });

        return mainView;
    }

    private void apply_crop_image() {


        cropImageView.crop(Uri.fromFile(new File(getImage_uri())))
                .execute(new CropCallback() {
                    @Override
                    public void onSuccess(Bitmap cropped) {


                        EventBus.getDefault().post(new ImageCropped(cropped));


//                        activity.changeMainBitmap(cropped, true);
//                        backToMain();
//                        deleteImageFile(savedImageUri);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
//                        backToMain();
//                        deleteImageFile(savedImageUri);
//                        Toast.makeText(getContext(), "Error while saving image", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private Bitmap loadImage(String filePath) {
        return BitmapUtils.getSampledBitmap(filePath, imageWidth,
                imageHeight);
    }


    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
