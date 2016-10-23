package com.polydevops.materialwidgets.library.materialSpinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.polydevops.materialwidgets.R;

/**
 * Provides custom AttributeSet for MaterialSpinnerDropDown
 */
public class MaterialSpinnerDropDownAttributeSet {

    private int verticalOffset;
    private int horiziontalOffset;

    private boolean elevationEnabled;

    private Drawable itemDivider;

    private boolean dismissOnItemClick;

    public MaterialSpinnerDropDownAttributeSet(Context context, AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialSpinner, 0, 0);

        try {
            verticalOffset = a.getDimensionPixelSize(R.styleable.MaterialSpinner_dropDownVerticalOffset, 0);
            horiziontalOffset = a.getDimensionPixelSize(R.styleable.MaterialSpinner_dropDownHorizontalOffset, 0);
            elevationEnabled = a.getBoolean(R.styleable.MaterialSpinner_dropDownElevationEnabled, true);
            itemDivider = ContextCompat.getDrawable(context, a.getResourceId(R.styleable.MaterialSpinner_dropDownItemDivider, 0));
            dismissOnItemClick = a.getBoolean(R.styleable.MaterialSpinner_dropDownDismissOnItemClick, true);
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

    public int getHoriziontalOffset() {
        return horiziontalOffset;
    }

    public void setHoriziontalOffset(int horiziontalOffset) {
        this.horiziontalOffset = horiziontalOffset;
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

    public boolean isDismissOnItemClickEnabled() {
        return dismissOnItemClick;
    }

    public void setIsDismissOnItemClickEnabled(boolean dismissOnItemClick) {
        this.dismissOnItemClick = dismissOnItemClick;
    }
}
