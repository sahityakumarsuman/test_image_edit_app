package com.example.sahityakumarsuman.newmaker;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sahityakumarsuman.newmaker.AllEventBusModel.ImageCropped;
import com.example.sahityakumarsuman.newmaker.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;

    private String image_path_we_get;

    @BindView(R.id.image_view_show)
    ImageView image_view_show;

    @BindView(R.id.crop_viewstart)
    TextView crop_viewstart;

    @BindView(R.id.sticker_view)
    TextView sticker_view;


    @BindView(R.id.rotate_image)
    TextView rotate_image;

    @BindView(R.id.fry_image)
    TextView fry_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        if (getIntent() != null) {
            setImage_path_we_get(getIntent().getStringExtra(Constants.IMAGE_PASSS));
        }


        CropFragment myf = new CropFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IMAGE_PASSS, getImage_path_we_get());
        myf.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout, myf);
        transaction.commit();

    }


    public String getImage_path_we_get() {
        return image_path_we_get;
    }

    public void setImage_path_we_get(String image_path_we_get) {
        this.image_path_we_get = image_path_we_get;
    }


    @OnClick(R.id.crop_viewstart)
    void on_crop_Click() {
        CropFragment myf = new CropFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IMAGE_PASSS, getImage_path_we_get());
        myf.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout, myf);
        transaction.commit();
    }


    @OnClick(R.id.sticker_view)
    void on_sticker_click() {
        StickerFragment myf = new StickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IMAGE_PASSS, getImage_path_we_get());
        myf.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, myf);
        transaction.commit();
    }


    @OnClick(R.id.rotate_image)
    void on_rotate_image() {
        RotateFragment myf = new RotateFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IMAGE_PASSS, getImage_path_we_get());
        myf.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, myf);
        transaction.commit();
    }


    @OnClick(R.id.bush_image)
    void on_brush_click() {
        BrushViewFragmentWork myf = new BrushViewFragmentWork();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IMAGE_PASSS, getImage_path_we_get());
        myf.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, myf);
        transaction.commit();
    }


    @OnClick(R.id.fry_image)
    void on_fry_image_click() {
        FryEffectFragment myf = new FryEffectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IMAGE_PASSS, getImage_path_we_get());
        myf.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, myf);
        transaction.commit();
    }


    @Subscribe
    void on_image_cropped(ImageCropped imageCropped) {
        frame_layout.setVisibility(View.GONE);
        image_view_show.setVisibility(View.VISIBLE);
        image_view_show.setImageBitmap(imageCropped.getBitmap());
    }

}
