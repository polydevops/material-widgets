package com.polydevops.materialwidgets.materialListView;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


/**
 * GridLayoutManager implementation for use with MaterialListView
 *
 * Can layout items in a grid
 */
public class GridLayoutManager extends LinearLayoutManager {

    public static final int EMPTY_LAYOUT = 0;

    private GridLayout gridLayout;
    private int layoutCount;

    private int spanCount;

    public GridLayoutManager(final Context context, final int spanCount) {
        super(context, VERTICAL);
        this.spanCount = spanCount;
    }

    @Override
    public void addView(View child) {
        initGridLayout();
        addViewToGridLayout(child);

        if (layoutCount == getMaterialListView().getAdapter().getItemCount()) {
            super.addView(child);
        }
    }

    @Override
    protected void fill(MaterialListView materialListView) {
        final MaterialListView.Adapter adapter = materialListView.getAdapter();
        final int adapterItemCount = adapter.getItemCount();
        for (int i = 0; i < adapterItemCount; i++) {
            MaterialListView.ViewHolder viewHolder = adapter.createViewHolder(materialListView, adapter.getItemViewType(i));
            adapter.bindViewHolder(viewHolder, i);

            final View viewToAdd = viewHolder.view;
            materialListView.addViewClickListener(viewToAdd, i);

            addView(viewToAdd);
            addDividerView(materialListView.getDivider());
        }

        layoutCount = EMPTY_LAYOUT;
        gridLayout = null;
    }

    private void initGridLayout() {
        if (gridLayout == null) {
            gridLayout = new GridLayout(getContext());
            gridLayout.setColumnCount(spanCount);
            layoutCount = EMPTY_LAYOUT;
        }
    }

    private void addViewToGridLayout(final View child) {
        final GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setGravity(Gravity.FILL);

        setMargins(child, params);
        setWidth(child, params);

        child.setLayoutParams(params);

        gridLayout.addView(child);

        layoutCount++;
    }

    private void setMargins(final View child, final GridLayout.LayoutParams params) {
        final ViewGroup.MarginLayoutParams childViewMarginLayoutParams = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        params.setMargins(childViewMarginLayoutParams.leftMargin, childViewMarginLayoutParams.topMargin, childViewMarginLayoutParams.rightMargin, childViewMarginLayoutParams.topMargin);
    }

    private void setWidth(final View child, final GridLayout.LayoutParams params) {
        final ViewGroup.MarginLayoutParams childViewMarginLayoutParams = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        final int gridItemWidth = (getMaterialListView().getWidth() / spanCount) - (childViewMarginLayoutParams.leftMargin + childViewMarginLayoutParams.rightMargin);
        params.width = gridItemWidth;

    }
}
