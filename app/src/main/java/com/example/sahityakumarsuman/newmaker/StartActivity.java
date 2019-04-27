package com.example.sahityakumarsuman.newmaker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;


import com.crashlytics.android.Crashlytics;
import com.example.sahityakumarsuman.newmaker.utils.BitmapUtils;
import com.example.sahityakumarsuman.newmaker.utils.Constants;

import io.fabric.sdk.android.Fabric;
import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iamutkarshtiwari.github.io.ananas.editimage.utils.PermissionUtils;

public class StartActivity extends AppCompatActivity {


    public static final int PICK_IMAGE = 1;
    public static final int START_IMAGE_SELECT_GALLERY = 1;


    @BindView(R.id.image_view_show)
    ImageView image_view_show;

    @BindView(R.id.select_image_from_gallry)
    Button select_image_from_gallry;

    @BindView(R.id.select_start_editing)
    Button select_start_editing;

    private int imageWidth, imageHeight;
    private static final int PERMISSIONS_REQUEST_CODE = 110;
    private String image_selecte_path;


    private final String[] requiredPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.start_activity);


        ButterKnife.bind(this);


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels / 2;
        imageHeight = metrics.heightPixels / 2;

        if (!PermissionUtils.hasPermissions(this, requiredPermissions)) {
            ActivityCompat.requestPermissions(this, requiredPermissions, PERMISSIONS_REQUEST_CODE);
        }
    }

    @OnClick(R.id.select_image_from_gallry)
    void select_image() {


        Intent intent = new Intent(this, SelectPictureActivity.class);
        startActivityForResult(intent, START_IMAGE_SELECT_GALLERY);

    }

    @OnClick(R.id.select_start_editing)
    void start_editing() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.IMAGE_PASSS, getImage_selecte_path());
        startActivity(intent);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == START_IMAGE_SELECT_GALLERY) {
            image_view_show.setImageBitmap(loadImage(data.getStringExtra("imgPath")));
            setImage_selecte_path(data.getStringExtra("imgPath"));
        }
    }


    private Bitmap loadImage(String filePath) {
        return BitmapUtils.getSampledBitmap(filePath, imageWidth,
                imageHeight);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String permissions[], @NotNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    finish();
                }
                break;
            }
        }
    }

    public String getImage_selecte_path() {
        return image_selecte_path;
    }

    public void setImage_selecte_path(String image_selecte_path) {
        this.image_selecte_path = image_selecte_path;
    }
}
