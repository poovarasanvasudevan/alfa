package com.poovarasan.afka.widget;

/*
 * Copyright 2016 Elye Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;
import com.poovarasan.afka.R;

public class LoaderImageView extends SimpleDraweeView implements LoaderView {

    private LoaderController loaderController;

    public LoaderImageView(Context context) {
        super(context);
        init(null);
    }

    public LoaderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LoaderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        loaderController = new LoaderController(this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.loader_view, 0, 0);
        loaderController.setUseGradient(typedArray.getBoolean(R.styleable.loader_view_use_gradient, LoaderConstant.USE_GRADIENT_DEFAULT));
        typedArray.recycle();
    }

    public void resetLoader() {
        if (getDrawable() != null) {
            super.setImageDrawable(null);
            loaderController.startLoading();
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        loaderController.onSizeChanged();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        loaderController.onDraw(canvas);
    }

    @Override
    public void setRectColor(Paint rectPaint) {
        rectPaint.setColor(LoaderConstant.COLOR_DEFAULT_GREY);
    }

    @Override
    public boolean valueSet() {
        return (getDrawable() != null);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        loaderController.stopLoading();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        loaderController.stopLoading();
    }


    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        loaderController.stopLoading();
    }

    @Override
    public void setImageURI(String uriString) {
        super.setImageURI(uriString);
        loaderController.stopLoading();
    }



    @Override
    public void setImageIcon(Icon icon) {
        super.setImageIcon(icon);
        loaderController.stopLoading();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        loaderController.stopLoading();
    }
}
