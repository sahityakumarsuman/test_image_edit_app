package com.example.sahityakumarsuman.newmaker.AllEventBusModel;

import android.graphics.Bitmap;

public class ImageCropped {

    private Bitmap bitmap;

    public ImageCropped(Bitmap bitmap) {
        setBitmap(bitmap);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
