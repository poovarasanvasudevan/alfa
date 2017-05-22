package com.poovarasan.afka.picker.engine.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.poovarasan.afka.picker.engine.ImageEngine;

/**
 * Created by poovarasanv on 22/5/17.
 *
 * @author poovarasanv
 * @project Afka
 * @on 22/5/17 at 12:31 PM
 */

public class FerescoEngine implements ImageEngine {

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, SimpleDraweeView imageView, Uri uri) {
        imageView.setImageURI(uri);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, SimpleDraweeView imageView, Uri uri) {
        imageView.setImageURI(uri);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, SimpleDraweeView imageView, Uri uri) {
        imageView.setImageURI(uri);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, SimpleDraweeView imageView, Uri uri) {
        imageView.setImageURI(uri);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }
}
