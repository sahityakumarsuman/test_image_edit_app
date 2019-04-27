package com.example.sahityakumarsuman.newmaker;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sahityakumarsuman.newmaker.brushview.FingerPaintImageView;
import com.example.sahityakumarsuman.newmaker.utils.BitmapUtils;
import com.example.sahityakumarsuman.newmaker.utils.Constants;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kotlin.jvm.internal.Intrinsics;

public class BrushViewFragmentWork extends Fragment {

    private HashMap findViewCache;


    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.undo)
    TextView undo;
    @BindView(R.id.clear)
    TextView clear;

    @BindView(R.id.red)
    SeekBar red;

    @BindView(R.id.green)
    SeekBar green;

    @BindView(R.id.blue)
    SeekBar blue;

    @BindView(R.id.tolerance)
    SeekBar tolerance;

    @BindView(R.id.width)
    SeekBar width;

    @BindView(R.id.normal)
    RadioButton normal;

    @BindView(R.id.emboss)
    RadioButton emboss;

    @BindView(R.id.blur)
    RadioButton blur;

    @BindView(R.id.previewContainer)
    RelativeLayout previewContainer;

    @BindView(R.id.preview)
    ImageView preview;

    @BindView(R.id.finger)
    FingerPaintImageView finger;

    @BindView(R.id.colorPreview)
    TextView colorPreview;


    private Unbinder unbinder;

    private String image_uri;

    private int imageWidth, imageHeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.brush_view_fragment, null);
        unbinder = ButterKnife.bind(this, mainView);

        init_view();


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels / 2;
        imageHeight = metrics.heightPixels / 2;


        String myStr = getArguments().getString(Constants.IMAGE_PASSS);
        setImage_uri(myStr);

        finger.setImageBitmap(loadImage(myStr));

        return mainView;
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

    @OnClick(R.id.undo)
    void press_undo() {
        finger.undo();
    }

    @OnClick(R.id.clear)
    void press_clear() {
        finger.clear();
    }

    @OnClick(R.id.close)
    void press_close() {
        hidePreview();
    }

    @OnClick(R.id.save)
    void press_save() {
        showPreview();
    }

    @OnClick(R.id.emboss)
    void press_emboss() {
        finger.emboss();
    }

    @OnClick(R.id.blur)
    void press_blur() {
        finger.blur();
    }

    @OnClick(R.id.normal)
    void press_normal() {
        finger.normal();
    }


    private void init_view() {

        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int r = red.getProgress();
                int g = green.getProgress();
                int b = blue.getProgress();
                int color = Color.argb(255, r, g, b);
                finger.setStrokeColor(color);
                colorPreview.setBackgroundColor(color);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                int r = red.getProgress();
                int g = green.getProgress();
                int b = blue.getProgress();
                int color = Color.argb(255, r, g, b);
                finger.setStrokeColor(color);
                colorPreview.setBackgroundColor(color);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int r = red.getProgress();
                int g = green.getProgress();
                int b = blue.getProgress();
                int color = Color.argb(255, r, g, b);
                finger.setStrokeColor(color);
                colorPreview.setBackgroundColor(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        tolerance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                finger.setTouchTolerance((float) progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        width.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                finger.setTouchTolerance((float) progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    private final void showPreview() {
        previewContainer.setVisibility(View.VISIBLE);
        preview.setImageDrawable(finger.getDrawable());
    }

    private final void hidePreview() {
        previewContainer.setVisibility(View.INVISIBLE);
    }


}
