/*******************************************************************************
 * Copyright 2016 stfalcon.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.poovarasan.chatkit.commons;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.poovarasan.chatkit.R;

/**
 * Base class for chat component styles
 */
public abstract class Style {

    protected Context context;
    protected Resources resources;
    protected AttributeSet attrs;

    protected Style(Context context, AttributeSet attrs) {
        this.context = context;
        this.resources = context.getResources();
        this.attrs = attrs;
    }

    protected final int getSystemAccentColor() {
        return getSystemColor(R.attr.colorAccent);
    }

    protected final int getSystemPrimaryColor() {
        return getSystemColor(R.attr.colorPrimary);
    }

    protected final int getSystemPrimaryDarkColor() {
        return getSystemColor(R.attr.colorPrimaryDark);
    }

    protected final int getSystemPrimaryTextColor() {
        return getSystemColor(android.R.attr.textColorPrimary);
    }

    protected final int getSystemHintColor() {
        return getSystemColor(android.R.attr.textColorHint);
    }

    protected final int getSystemColor(@AttrRes int attr) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{attr});
        int color = a.getColor(0, 0);
        a.recycle();

        return color;
    }

    protected final int getDimension(@DimenRes int dimen) {
        return resources.getDimensionPixelSize(dimen);
    }

    protected final int getColor(@ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }

    protected final Drawable getDrawable(@DrawableRes int drawable) {
        return ContextCompat.getDrawable(context, drawable);
    }

    protected final Drawable getVectorDrawable(@DrawableRes int drawable) {
        return ContextCompat.getDrawable(context, drawable);
    }

}
