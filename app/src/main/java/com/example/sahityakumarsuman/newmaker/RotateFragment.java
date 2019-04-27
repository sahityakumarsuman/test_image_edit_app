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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sahityakumarsuman.newmaker.utils.BitmapUtils;
import com.example.sahityakumarsuman.newmaker.utils.Constants;
import com.example.sahityakumarsuman.newmaker.widget.GestureCropImageView;
import com.example.sahityakumarsuman.newmaker.widget.HorizontalProgressWheelView;
import com.example.sahityakumarsuman.newmaker.widget.OverlayView;
import com.example.sahityakumarsuman.newmaker.widget.TransformImageView;
import com.example.sahityakumarsuman.newmaker.widget.UCropView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RotateFragment extends Fragment {


    private Unbinder unbinder;

    @BindView(R.id.ucrop)
    GestureCropImageView mUCropView;

    @BindView(R.id.wrapper_rotate_by_angle)
    FrameLayout wrapper_rotate_by_angle;

    @BindView(R.id.wrapper_reset_rotate)
    FrameLayout wrapper_reset_rotate;

    @BindView(R.id.rotate_scroll_wheel)
    HorizontalProgressWheelView rotate_scroll_wheel;

    @BindView(R.id.text_view_rotate)
    TextView mTextViewRotateAngle;


    private GestureCropImageView mGestureCropImageView;
    private OverlayView mOverlayView;


//    private TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() {
//        @Override
//        public void onRotate(float currentAngle) {
//            setAngleText(currentAngle);
//        }
//
//        @Override
//        public void onScale(float currentScale) {
//            setScaleText(currentScale);
//        }
//
//        @Override
//        public void onBrightness(float currentBrightness) {
//            setBrightnessText(currentBrightness);
//        }
//
//        @Override
//        public void onContrast(float currentContrast) {
//            setContrastText(currentContrast);
//        }
//
//        @Override
//        public void onSaturation(float currentSaturation) {
//            setSaturationText(currentSaturation);
//        }
//
//        @Override
//        public void onSharpness(float currentSharpness) {
//            setSharpnessText(currentSharpness);
//        }
//
//        @Override
//        public void onLoadComplete() {
//            mUCropView.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator());
////            mBlockingView.setClickable(false);
////            mShowLoader = false;
////            supportInvalidateOptionsMenu();
//        }
//
//        @Override
//        public void onLoadFailure(@NonNull Exception e) {
////            setResultError(e);
////            finish();
//        }
//
//    };


    private static final int ROTATE_WIDGET_SENSITIVITY_COEFFICIENT = 42;
    private String image_uri;
    private int imageWidth, imageHeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.rotate_image_by_angle, null);
        unbinder = ButterKnife.bind(this, mainView);


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels / 2;
        imageHeight = metrics.heightPixels / 2;

        String myStr = getArguments().getString(Constants.IMAGE_PASSS);
        setImage_uri(myStr);


        mActiveWidgetColor = ContextCompat.getColor(getActivity(), R.color.ucrop_color_widget_background);

        mGestureCropImageView = mUCropView;
//        mOverlayView = mUCropView.getOverlayView();
        setupRotateWidget();


//        mGestureCropImageView.setTransformImageListener(mImageListener);
        return mainView;
    }


    private int mActiveWidgetColor;


    private Bitmap loadImage(String filePath) {
        return BitmapUtils.getSampledBitmap(filePath, imageWidth,
                imageHeight);
    }


    private void setupRotateWidget() {

        mGestureCropImageView.setImageBitmap(loadImage(getImage_uri()));

        rotate_scroll_wheel.setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() {
            @Override
            public void onScroll(float delta, float totalDistance) {
                mGestureCropImageView.postRotate(delta / ROTATE_WIDGET_SENSITIVITY_COEFFICIENT);
            }

            @Override
            public void onScrollEnd() {
                mGestureCropImageView.setImageToWrapCropBounds();
            }

            @Override
            public void onScrollStart() {
                mGestureCropImageView.cancelAllAnimations();
            }
        });

        rotate_scroll_wheel.setMiddleLineColor(mActiveWidgetColor);


        wrapper_reset_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRotation();
            }
        });


        wrapper_rotate_by_angle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateByAngle(90);
            }
        });
    }


    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
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
