package com.example.sahityakumarsuman.newmaker.textadding;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sahityakumarsuman.newmaker.R;
import com.example.sahityakumarsuman.newmaker.photoview.PhotoView;
import com.example.sahityakumarsuman.newmaker.utils.BitmapUtils;
import com.example.sahityakumarsuman.newmaker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddTextFragment extends Fragment {


//    @BindView(R.id.text_sticker_panel)
//    TextStickerView textStickersParentView;

    @BindView(R.id.add_text_button)
    Button add_text_button;

    @BindView(R.id.photo_view_used)
    PhotoView photo_view_used;

    private List<View> addedViews;

    private int imageWidth, imageHeight;
    private String image_uri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.text_add_image_view, null);

        init_view();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels / 2;
        imageHeight = metrics.heightPixels / 2;

        String myStr = getArguments().getString(Constants.IMAGE_PASSS);
        setImage_uri(myStr);

        photo_view_used.setImageBitmap(loadImage(myStr));


        return mainView;
    }


    @OnClick(R.id.add_text_button)
    void click_on_add_text_button() {
//        TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show();
//        textEditorDialogFragment.setOnTextEditorListener(this::addText);
    }


//
//    @SuppressLint("ClickableViewAccessibility")
//    private void addText(String text, final int colorCodeTextView) {
//        final View textStickerView = getTextStickerLayout();
//        final TextView textInputTv = textStickerView.findViewById(R.id.text_sticker_tv);
//        final ImageView imgClose = textStickerView.findViewById(R.id.sticker_delete_btn);
//        final FrameLayout frameBorder = textStickerView.findViewById(R.id.sticker_border);
//
//        textInputTv.setText(text);
//        textInputTv.setTextColor(colorCodeTextView);
//        textInputTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,
//                getResources().getDimension(R.dimen.text_sticker_size));
//
//        MultiTouchListener multiTouchListener = new MultiTouchListener(
//                imgClose,
//                this.textStickersParentView,
//                photo_view_used,
//                this, getContext());
//        multiTouchListener.setOnGestureControl(new OnGestureControl() {
//
//            boolean isDownAlready = false;
//
//            @Override
//            public void onClick() {
//                boolean isBackgroundVisible = frameBorder.getTag() != null && (boolean) frameBorder.getTag();
//                if (isBackgroundVisible && !isDownAlready) {
//                    String textInput = textInputTv.getText().toString();
//                    int currentTextColor = textInputTv.getCurrentTextColor();
//                    showTextEditDialog(textStickerView, textInput, currentTextColor);
//                }
//            }
//
//            @Override
//            public void onDown() {
//                boolean isBackgroundVisible = frameBorder.getTag() != null && (boolean) frameBorder.getTag();
//                if (!isBackgroundVisible) {
//                    frameBorder.setBackgroundResource(R.drawable.background_border);
//                    imgClose.setVisibility(View.VISIBLE);
//                    frameBorder.setTag(true);
//                    updateViewsBordersVisibilityExcept(textStickerView);
//                    isDownAlready = true;
//                } else {
//                    isDownAlready = false;
//                }
//            }
//
//            @Override
//            public void onLongClick() {
//            }
//        });
//
//        textStickerView.setOnTouchListener(multiTouchListener);
//        addViewToParent(textStickerView);
//    }

//    private void addViewToParent(View view) {
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//        textStickersParentView.addView(view, params);
//        addedViews.add(view);
//        updateViewsBordersVisibilityExcept(view);
//    }
//
//
//    private void deleteViewFromParent(View view) {
//        textStickersParentView.removeView(view);
//        addedViews.remove(view);
//        textStickersParentView.invalidate();
//        updateViewsBordersVisibilityExcept(null);
//    }
//
//    private void updateViewsBordersVisibilityExcept(@Nullable View keepView) {
//        for (View view : addedViews) {
//            if (view != keepView) {
//                FrameLayout border = view.findViewById(R.id.sticker_border);
//                border.setBackgroundResource(0);
//                ImageView closeBtn = view.findViewById(R.id.sticker_delete_btn);
//                closeBtn.setVisibility(View.GONE);
//                border.setTag(false);
//            }
//        }
//    }

//
//    private View getTextStickerLayout() {
//        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//        View rootView = layoutInflater.inflate(R.layout.view_text_sticker_item, null);
//        TextView txtText = rootView.findViewById(R.id.text_sticker_tv);
//        if (txtText != null) {
//            txtText.setGravity(Gravity.CENTER);
//            ImageView imgClose = rootView.findViewById(R.id.sticker_delete_btn);
//            if (imgClose != null) {
//                imgClose.setOnClickListener(view -> deleteViewFromParent(rootView));
//            }
//        }
//        return rootView;
//    }


    private void init_view() {
        // textStickersParentView.setDrawingCacheEnabled(true);
        addedViews = new ArrayList<>();
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
}
