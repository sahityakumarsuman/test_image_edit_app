package com.example.sahityakumarsuman.newmaker;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sahityakumarsuman.newmaker.photoview.PhotoView;
import com.example.sahityakumarsuman.newmaker.stickeradding.BitmapStickerIcon;
import com.example.sahityakumarsuman.newmaker.stickeradding.DeleteIconEvent;
import com.example.sahityakumarsuman.newmaker.stickeradding.DrawableSticker;
import com.example.sahityakumarsuman.newmaker.stickeradding.FlipHorizontallyEvent;
import com.example.sahityakumarsuman.newmaker.stickeradding.Sticker;
import com.example.sahityakumarsuman.newmaker.stickeradding.StickerView;
import com.example.sahityakumarsuman.newmaker.stickeradding.TextSticker;
import com.example.sahityakumarsuman.newmaker.stickeradding.ZoomIconEvent;
import com.example.sahityakumarsuman.newmaker.utils.BitmapUtils;
import com.example.sahityakumarsuman.newmaker.utils.Constants;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class StickerFragment extends Fragment {


    Unbinder unbinder = null;

    @BindView(R.id.sticker_view)
    StickerView sticker_view;

    @BindView(R.id.photo_view_used)
    PhotoView photo_view_used;

    private TextSticker sticker;
    private int imageWidth, imageHeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.sticker_fragment_layout, null);
        unbinder = ButterKnife.bind(this, mainView);


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels / 2;
        imageHeight = metrics.heightPixels / 2;


        String myStr = getArguments().getString(Constants.IMAGE_PASSS);
        setImage_uri(myStr);


        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());

        BitmapStickerIcon heartIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_favorite_white_24dp),
                        BitmapStickerIcon.LEFT_BOTTOM);
        heartIcon.setIconEvent(new HelloIconEvent());

        sticker_view.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon, heartIcon));

        sticker_view.setBackgroundColor(Color.WHITE);
        sticker_view.setLocked(false);
        sticker_view.setConstrained(true);
        sticker = new TextSticker(getContext());
        sticker.setDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),
                R.drawable.sticker_transparent_background));
        sticker.setText("Hello, world!");
        sticker.setTextColor(Color.BLACK);
        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        sticker.resizeText();


        photo_view_used.setImageBitmap(loadImage(getImage_uri()));


        sticker_view.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                Log.d("----", "onStickerAdded");
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                //stickerView.removeAllSticker();
                if (sticker instanceof TextSticker) {
                    ((TextSticker) sticker).setTextColor(Color.RED);
                    sticker_view.replace(sticker);
                    sticker_view.invalidate();
                }
//                Log.d(TAG, "onStickerClicked");
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
//                Log.d(TAG, "onStickerDeleted");
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
//                Log.d(TAG, "onStickerDragFinished");
            }

            @Override
            public void onStickerTouchedDown(@NonNull Sticker sticker) {
//                Log.d(TAG, "onStickerTouchedDown");
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
//                Log.d(TAG, "onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
//                Log.d(TAG, "onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
//                Log.d(TAG, "onDoubleTapped: double tap will be with two click");
            }
        });


        loadSticker();


        return mainView;

    }


    private String image_uri;

    private void setImage_uri(String myStr) {
        this.image_uri = myStr;
    }

    private String getImage_uri() {
        return this.image_uri;
    }


    private void loadSticker() {
        Drawable drawable =
                ContextCompat.getDrawable(getActivity(), R.drawable.haizewang_215);
        Drawable drawable1 =
                ContextCompat.getDrawable(getActivity(), R.drawable.haizewang_23);
        sticker_view.addSticker(new DrawableSticker(drawable));
        sticker_view.addSticker(new DrawableSticker(drawable1), Sticker.Position.BOTTOM | Sticker.Position.RIGHT);

        Drawable bubble = ContextCompat.getDrawable(getActivity(), R.drawable.bubble);
        sticker_view.addSticker(
                new TextSticker(getActivity().getApplicationContext())
                        .setDrawable(bubble)
                        .setText("Sticker\n")
                        .setMaxTextSize(14)
                        .resizeText()
                , Sticker.Position.TOP);
    }


    private Bitmap loadImage(String filePath) {
        return BitmapUtils.getSampledBitmap(filePath, imageWidth,
                imageHeight);
    }

}
