package com.polydevops.materialwidgets.library.materialListView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.polydevops.materialwidgets.R;
import com.polydevops.materialwidgets.library.materialAdapter.MaterialAdapter;

/**
 * Custom 'ListView' implementation that can either grow in size as items are added to it or
 * default to a scrollable list.
 *
 * Implementation of adapter for adding items to MaterialListView is similar to that provided
 * by RecyclerView.Adapter and RecyclerView.ViewHolder.
 *
 * Just like RecyclerView, a LayoutManager is needed to instruct the view how to layout its children.
 * A LinearLayout and GridLayout manager is provided for convenience.
 */
public class MaterialListView extends FrameLayout {

    private MaterialAdapter adapter;
    private LayoutManager layoutManager;
    private OnItemClickListener itemClickListener;

    private LinearLayout contentView;

    // attributes
    private boolean isStaticList;
    private Drawable itemDivider;

    public MaterialListView(Context context) {
        super(context, null);
    }

    public MaterialListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialListView, 0, 0);
        try {
            itemDivider = a.getDrawable(R.styleable.MaterialListView_itemDivider);
            isStaticList = a.getBoolean(R.styleable.MaterialListView_staticList, false);
        } finally {
            a.recycle();
        }
    }

    public void setLayoutManager(final LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        onLayoutManagerAttached();
    }

    public void setAdapter(final MaterialAdapter adapter) {
        this.adapter = adapter;
        setAdapterOnDataAttachedListener();
        onAdapterAttached();
    }

    public MaterialAdapter getAdapter() { return adapter; }

    public void setDivider(final int itemDividerRes) {
        setDivider(ContextCompat.getDrawable(getContext(), itemDividerRes));
    }

    public void setDivider(final Drawable itemDivider) {
        this.itemDivider = itemDivider;
        onDividerAttached();
    }

    public Drawable getDivider() { return itemDivider; }

    public boolean hasDivider() { return itemDivider != null; }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        onItemClickListenerAttached();
    }

    public LinearLayout getContentView() {
        return contentView;
    }

    void addViewClickListener(final View view, final int position) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(MaterialListView.this, position, v);
                }
            }
        });
    }

    private void onLayoutManagerAttached() {
        removeAllViews();
        refreshLayout();
    }

    private void onAdapterAttached() {
        removeAllViews();
        refreshLayout();
    }

    private void onItemClickListenerAttached() {
        removeAllViews();
        refreshLayout();
    }

    private void onDividerAttached() {
        removeAllViews();
        refreshLayout();
    }

    private void setAdapterOnDataAttachedListener() {
        adapter.setOnDataChangedListener(new OnDataSetChangedListener() {
            @Override
            public void onDataSetChanged() {
                removeAllViews();
                refreshLayout();
            }
        });
    }

    private void refreshLayout() {
        if (layoutManager != null && adapter != null) {
            if (isStaticList) {
                contentView = new LinearLayout(getContext());
                addView(contentView);
            } else {
                contentView = new LinearLayout(getContext());

                final ScrollView scrollView = new ScrollView(getContext());
                scrollView.addView(contentView);

                addView(scrollView);
            }

            layoutManager.onLayoutChildren(this);
        }
    }

    /**
     * Listener for detecting when an list item is clicked.
     */
    public interface OnItemClickListener {
        void onItemClicked(final MaterialListView materialListView, final int position, final View view);
    }

}
