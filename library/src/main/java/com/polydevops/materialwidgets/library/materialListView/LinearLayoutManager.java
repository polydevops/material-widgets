package com.polydevops.materialwidgets.library.materialListView;

import android.content.Context;
import android.view.View;

import com.polydevops.materialwidgets.library.materialAdapter.MaterialAdapter;

/**
 * LinearLayoutManager implementation for use with MaterialListView
 *
 * Can layout items either horizontally or vertically
 */
public class LinearLayoutManager extends LayoutManager {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int orientation;

    public LinearLayoutManager(final Context context) { this(context, VERTICAL); }

    public LinearLayoutManager(final Context context, int orientation) {
        super(context);
        setOrientation(orientation);
    }

    @Override
    public void onLayoutChildren(MaterialListView materialListView) {
        super.onLayoutChildren(materialListView);

        materialListView.getContentView().setOrientation(orientation);

        fill(materialListView);
    }

    public int getOrientation() { return orientation; }

    public void setOrientation(final int orientation) {
        if (orientation != VERTICAL && orientation != HORIZONTAL) {
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }

        this.orientation = orientation;
    }

    @Override
    protected void fill(MaterialListView materialListView) {
        final MaterialAdapter adapter = materialListView.getAdapter();
        final int adapterItemCount = adapter.getCount();
        for (int i = 0; i < adapterItemCount; i++) {
            final View viewToAdd = adapter.getView(i, null, materialListView);
            materialListView.addViewClickListener(viewToAdd, i);
            addView(viewToAdd);

            addDividerView();
        }
    }
}
