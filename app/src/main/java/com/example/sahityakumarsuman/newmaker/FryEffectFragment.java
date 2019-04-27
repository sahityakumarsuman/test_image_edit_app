package com.example.sahityakumarsuman.newmaker;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sahityakumarsuman.newmaker.utils.BitmapUtils;
import com.example.sahityakumarsuman.newmaker.utils.Constants;
import com.example.sahityakumarsuman.newmaker.widget.GestureCropImageView;
import com.example.sahityakumarsuman.newmaker.widget.HorizontalProgressWheelView;
import com.example.sahityakumarsuman.newmaker.widget.TransformImageView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FryEffectFragment extends Fragment {


    // Enables dynamic coloring
    private int mToolbarColor;
    private int mStatusBarColor;
    private int mActiveWidgetColor;
    private int mActiveControlsWidgetColor;
    private int mToolbarWidgetColor;

    private int imageWidth, imageHeight;
    private String image_uri;

    @BindView(R.id.state_scale)
    LinearLayout state_scale;

    @BindView(R.id.state_brightness)
    LinearLayout state_brightness;

    @BindView(R.id.state_contrast)
    LinearLayout state_contrast;

    @BindView(R.id.state_saturation)
    LinearLayout state_saturation;

    @BindView(R.id.state_sharpness)
    LinearLayout state_sharpness;


    // Layout included

    @BindView(R.id.sharpess_layout_RL)
    RelativeLayout sharpess_layout_RL;

    @BindView(R.id.scale_layout_RL)
    RelativeLayout scale_layout_RL;

    @BindView(R.id.brightness_layout_RL)
    RelativeLayout brightness_layout_RL;

    @BindView(R.id.contract_layout_RL)
    RelativeLayout contract_layout_RL;

    @BindView(R.id.saturation_layout_RL)
    RelativeLayout saturation_layout_RL;

    private int selected_position = -1;

    // Scale image
    @BindView(R.id.text_view_scale)
    TextView text_view_scale;
    @BindView(R.id.scale_scroll_wheel)
    HorizontalProgressWheelView scale_scroll_wheel;

    //Brightness
    @BindView(R.id.text_view_brightness)
    TextView text_view_brightness;
    @BindView(R.id.brightness_scroll_wheel)
    HorizontalProgressWheelView brightness_scroll_wheel;


    //Saturation
    @BindView(R.id.text_view_saturation)
    TextView text_view_saturation;
    @BindView(R.id.saturation_scroll_wheel)
    HorizontalProgressWheelView saturation_scroll_wheel;

    //Sharpness
    @BindView(R.id.text_view_sharpness)
    TextView text_view_sharpness;
    @BindView(R.id.sharpness_scroll_wheel)
    HorizontalProgressWheelView sharpness_scroll_wheel;

    //Contrast
    @BindView(R.id.text_view_contrast)
    TextView text_view_contrast;
    @BindView(R.id.contrast_scroll_wheel)
    HorizontalProgressWheelView contrast_scroll_wheel;

    @BindView(R.id.ucrop)
    GestureCropImageView mGestureCropImageView;


    private Unbinder unbinder = null;


    private static final int SCALE_WIDGET_SENSITIVITY_COEFFICIENT = 15000;
    private static final int ROTATE_WIDGET_SENSITIVITY_COEFFICIENT = 42;
    private static final int BRIGHTNESS_WIDGET_SENSITIVITY_COEFFICIENT = 3;
    private static final int CONTRAST_WIDGET_SENSITIVITY_COEFFICIENT = 4;
    private static final int SATURATION_WIDGET_SENSITIVITY_COEFFICIENT = 3;
    private static final int SHARPNESS_WIDGET_SENSITIVITY_COEFFICIENT = 400;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fry_effect_fragment, null);

        unbinder = ButterKnife.bind(this, mainView);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels / 2;
        imageHeight = metrics.heightPixels / 2;

        String myStr = getArguments().getString(Constants.IMAGE_PASSS);
        setImage_uri(myStr);

        mGestureCropImageView.setImageBitmap(loadImage(getImage_uri()));

        init_views();
        set_event_change_listener();

        return mainView;
    }

    private void init_views() {
        mActiveWidgetColor = ContextCompat.getColor(getActivity(), R.color.ucrop_color_widget_background);

        mGestureCropImageView.setTransformImageListener(mImageListener);

        brightness_layout_RL.setVisibility(View.GONE);
        contract_layout_RL.setVisibility(View.GONE);
        saturation_layout_RL.setVisibility(View.GONE);
        sharpess_layout_RL.setVisibility(View.GONE);
        scale_layout_RL.setVisibility(View.VISIBLE);
    }

    private void set_event_change_listener() {
        scale_scroll_wheel.setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() {
            @Override
            public void onScrollStart() {
                mGestureCropImageView.cancelAllAnimations();
            }

            @Override
            public void onScroll(float delta, float totalDistance) {
                if (delta > 0) {
                    mGestureCropImageView.zoomInImage(mGestureCropImageView.getCurrentScale()
                            + delta * ((mGestureCropImageView.getMaxScale() - mGestureCropImageView.getMinScale()) / SCALE_WIDGET_SENSITIVITY_COEFFICIENT));
                } else {
                    mGestureCropImageView.zoomOutImage(mGestureCropImageView.getCurrentScale()
                            + delta * ((mGestureCropImageView.getMaxScale() - mGestureCropImageView.getMinScale()) / SCALE_WIDGET_SENSITIVITY_COEFFICIENT));
                }
            }

            @Override
            public void onScrollEnd() {
                mGestureCropImageView.setImageToWrapCropBounds();
            }
        });


        brightness_scroll_wheel.setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() {
            @Override
            public void onScrollStart() {
                mGestureCropImageView.cancelAllAnimations();
            }

            @Override
            public void onScroll(float delta, float totalDistance) {

                mGestureCropImageView.postBrightness(delta / BRIGHTNESS_WIDGET_SENSITIVITY_COEFFICIENT);

            }

            @Override
            public void onScrollEnd() {
                mGestureCropImageView.setImageToWrapCropBounds();
            }
        });


        saturation_scroll_wheel.setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() {
            @Override
            public void onScrollStart() {
                mGestureCropImageView.cancelAllAnimations();
            }

            @Override
            public void onScroll(float delta, float totalDistance) {
                mGestureCropImageView.postSaturation(delta / SATURATION_WIDGET_SENSITIVITY_COEFFICIENT);

            }

            @Override
            public void onScrollEnd() {
                mGestureCropImageView.setImageToWrapCropBounds();
            }
        });


        contrast_scroll_wheel.setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() {
            @Override
            public void onScrollStart() {
                mGestureCropImageView.cancelAllAnimations();
            }

            @Override
            public void onScroll(float delta, float totalDistance) {
                mGestureCropImageView.postContrast(delta / CONTRAST_WIDGET_SENSITIVITY_COEFFICIENT);

            }

            @Override
            public void onScrollEnd() {

                mGestureCropImageView.setImageToWrapCropBounds();

            }
        });


        sharpness_scroll_wheel.setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() {
            @Override
            public void onScrollStart() {
                mGestureCropImageView.cancelAllAnimations();
            }

            @Override
            public void onScroll(float delta, float totalDistance) {
                mGestureCropImageView.postSharpness(delta / SHARPNESS_WIDGET_SENSITIVITY_COEFFICIENT);


            }

            @Override
            public void onScrollEnd() {
                mGestureCropImageView.setImageToWrapCropBounds();

            }
        });


    }


    void show_contrast_layout() {
        sharpess_layout_RL.setVisibility(View.GONE);
        scale_layout_RL.setVisibility(View.GONE);
        brightness_layout_RL.setVisibility(View.GONE);
        saturation_layout_RL.setVisibility(View.GONE);
        contract_layout_RL.setVisibility(View.VISIBLE);
    }

    void show_sharpness_layout() {
        contract_layout_RL.setVisibility(View.GONE);
        scale_layout_RL.setVisibility(View.GONE);
        brightness_layout_RL.setVisibility(View.GONE);
        saturation_layout_RL.setVisibility(View.GONE);
        sharpess_layout_RL.setVisibility(View.VISIBLE);
    }

    void show_scale_layout() {
        contract_layout_RL.setVisibility(View.GONE);
        brightness_layout_RL.setVisibility(View.GONE);
        saturation_layout_RL.setVisibility(View.GONE);
        sharpess_layout_RL.setVisibility(View.GONE);
        scale_layout_RL.setVisibility(View.VISIBLE);
    }

    void show_brightness() {
        contract_layout_RL.setVisibility(View.GONE);
        saturation_layout_RL.setVisibility(View.GONE);
        sharpess_layout_RL.setVisibility(View.GONE);
        scale_layout_RL.setVisibility(View.GONE);
        brightness_layout_RL.setVisibility(View.VISIBLE);
    }

    void show_saturation_layout() {
        contract_layout_RL.setVisibility(View.GONE);
        sharpess_layout_RL.setVisibility(View.GONE);
        scale_layout_RL.setVisibility(View.GONE);
        brightness_layout_RL.setVisibility(View.GONE);
        saturation_layout_RL.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.state_sharpness)
    void on_state_shapness_clicked() {
        show_sharpness_layout();
        sharpness_scroll_wheel.setMiddleLineColor(mActiveWidgetColor);

    }


    @OnClick(R.id.state_saturation)
    void on_state_staturation_clicked() {
        show_saturation_layout();
        saturation_scroll_wheel.setMiddleLineColor(mActiveWidgetColor);

    }

    @OnClick(R.id.state_brightness)
    void on_state_brightness_clicked() {
        show_brightness();
        brightness_scroll_wheel.setMiddleLineColor(mActiveWidgetColor);

    }

    @OnClick(R.id.state_contrast)
    void on_state_contrast_clicked() {
        show_contrast_layout();
        contrast_scroll_wheel.setMiddleLineColor(mActiveWidgetColor);

    }

    @OnClick(R.id.state_scale)
    void on_state_scale_clicked() {
        show_scale_layout();
        scale_scroll_wheel.setMiddleLineColor(mActiveWidgetColor);

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



    private TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() {
        @Override
        public void onRotate(float currentAngle) {
//            setAngleText(currentAngle);
        }

        @Override
        public void onScale(float currentScale) {
            setScaleText(currentScale);
        }

        @Override
        public void onBrightness(float currentBrightness) {
            setBrightnessText(currentBrightness);
        }

        @Override
        public void onContrast(float currentContrast) {
            setContrastText(currentContrast);
        }

        @Override
        public void onSaturation(float currentSaturation) {
            setSaturationText(currentSaturation);
        }

        @Override
        public void onSharpness(float currentSharpness) {
            setSharpnessText(currentSharpness);
        }

        @Override
        public void onLoadComplete() {
            mGestureCropImageView.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator());
//            mBlockingView.setClickable(false);
//            mShowLoader = false;
//            supportInvalidateOptionsMenu();
        }

        @Override
        public void onLoadFailure(@NonNull Exception e) {
//            setResultError(e);
//            finish();
        }

    };




    private void setScaleText(float scale) {
        if (text_view_scale != null) {
            text_view_scale.setText(String.format(Locale.getDefault(), "%d%%", (int) (scale * 100)));
        }
    }

    private void setBrightnessText(float brightness) {
        if (text_view_brightness != null) {
            text_view_brightness.setText(String.format(Locale.getDefault(), "%d", (int) brightness));
        }
    }

    private void setContrastText(float contrast) {
        if (text_view_contrast != null) {
            text_view_contrast.setText(String.format(Locale.getDefault(), "%d", (int) contrast));
        }
    }

    private void setSaturationText(float saturation) {
        if (text_view_saturation != null) {
            text_view_saturation.setText(String.format(Locale.getDefault(), "%d", (int) saturation));
        }
    }

    private void setSharpnessText(float sharpness) {
        if (text_view_sharpness != null) {
            text_view_sharpness.setText(String.format(Locale.getDefault(), "%d", (int) sharpness));
        }
    }

    private void resetRotation() {
        mGestureCropImageView.postRotate(-mGestureCropImageView.getCurrentAngle());
        mGestureCropImageView.setImageToWrapCropBounds();
    }

    private void rotateByAngle(int angle) {
        mGestureCropImageView.postRotate(angle);
        mGestureCropImageView.setImageToWrapCropBounds();
    }

}
