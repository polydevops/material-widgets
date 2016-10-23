package com.polydevops.materialwidgets.library.materialListView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Abstract LayoutManager allows for different layout implementations.
 *
 * Current implementations are LinearLayoutManager and GridLayoutManager.
 */
public abstract class LayoutManager {

    private Context context;
    private MaterialListView materialListView;

    public LayoutManager(final Context context) { this.context = context; }

    protected abstract void fill(MaterialListView materialListView);

    public void onLayoutChildren(MaterialListView materialListView) {
        if (this.materialListView != materialListView) {
            this.materialListView = materialListView;
        }
    }

    public void addView(View child) { materialListView.getContentView().addView(child);}

    void addDividerView() {
        if (materialListView.hasDivider()) {
            final ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(materialListView.getDivider());
            materialListView.getContentView().addView(imageView);
        }
    }

    Context getContext() { return context; }

    MaterialListView getMaterialListView() { return materialListView; }
}
