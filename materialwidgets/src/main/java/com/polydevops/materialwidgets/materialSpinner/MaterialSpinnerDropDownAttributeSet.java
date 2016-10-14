package com.polydevops.materialwidgets.materialSpinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.polydevops.materialwidgets.R;

/**
 * Created by connor on 10/12/16.
 */

public class MaterialSpinnerDropDownAttributeSet {

    private int verticalOffset;

    private boolean elevationEnabled;

    private Drawable itemDivider;

    public MaterialSpinnerDropDownAttributeSet(Context context, AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialSpinner, 0, 0);

        try {
            verticalOffset = a.getDimensionPixelSize(R.styleable.MaterialSpinner_dropDownVerticalOffset, 0);
            elevationEnabled = a.getBoolean(R.styleable.MaterialSpinner_dropDownElevationEnabled, true);
            itemDivider = ContextCompat.getDrawable(context, a.getResourceId(R.styleable.MaterialSpinner_dropDownItemDivider, 0));
        } finally {
            a.recycle();
        }
    }

    public int getVerticalOffset() {
        return verticalOffset;
    }

    public void setVerticalOffset(int verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    public boolean isElevationEnabled() {
        return elevationEnabled;
    }

    public void setIsElevationEnabled(boolean elevationEnabled) {
        this.elevationEnabled = elevationEnabled;
    }

    public Drawable getItemDivider() {
        return itemDivider;
    }

    public void setItemDivider(Drawable itemDivider) {
        this.itemDivider = itemDivider;
    }
}
