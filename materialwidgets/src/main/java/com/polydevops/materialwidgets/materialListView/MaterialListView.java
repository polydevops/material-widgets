package com.polydevops.materialwidgets.materialListView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.polydevops.materialwidgets.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom 'ListView' implementation that can either grow in size as items are added to it or
 * default to a scrollable list.
 *
 * Implementation of adapter for adding items to MaterialListView is fairly similar to that provided
 * by RecyclerView.Adapter and RecyclerView.ViewHolder.
 *
 * Just like RecyclerView, a LayoutManager is needed to instruct the view how to layout its children.
 * A LinearLayout and GridLayout manager is provided for convenience.
 */
public class MaterialListView extends FrameLayout {

    public static final int NO_DIVIDER = -1;

    private Adapter adapter;
    private LayoutManager layoutManager;
    private OnItemClickListener itemClickListener;

    private int dividerLayoutRes = NO_DIVIDER;

    private LinearLayout contentView;

    // attributes
    private boolean isStaticList;

    public MaterialListView(Context context) {
        super(context, null);
    }

    public MaterialListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialListView, 0, 0);
        try {
            isStaticList = a.getBoolean(R.styleable.MaterialListView_staticList, false);
        } finally {
            a.recycle();
        }
    }

    public void setLayoutManager(final LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        onLayoutManagerAttached();
    }

    public void setAdapter(final Adapter adapter) {
        this.adapter = adapter;
        setAdapterOnDataAttachedListener();
        onAdapterAttached();
    }

    public Adapter getAdapter() { return adapter; }

    public void setDivider(final int drawableRes) {
        this.dividerLayoutRes = drawableRes;
        onDividerAttached();
    }

    public int getDivider() { return dividerLayoutRes; }

    public boolean hasDivider() { return dividerLayoutRes != NO_ID; }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        onItemClickListenerAttached();
    }

    public void addViewClickListener(final View view, final int position) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(MaterialListView.this, position, v);
                }
            }
        });
    }

    public ViewHolder findViewHolderForAdapterPosition(int position) {
        return (adapter != null) ? (ViewHolder) adapter.getViewHolderCache().get(position) : null;
    }

    protected LinearLayout getContentView() {
        return contentView;
    }

    private void onLayoutManagerAttached() {
        removeAllViews();
        clearAdapterCache();
        refreshLayout();
    }

    private void onAdapterAttached() {
        removeAllViews();
        clearAdapterCache();
        refreshLayout();
    }

    private void onItemClickListenerAttached() {
        removeAllViews();
        clearAdapterCache();
        refreshLayout();
    }

    private void onDividerAttached() {
        removeAllViews();
        clearAdapterCache();
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

    private void clearAdapterCache() {
        if (adapter != null) {
            adapter.clearViewHolderCache();
        }
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
     * Adapter for MaterialListView.
     *
     * To use, extend this class just like you would a RecyclerView.Adapter.
     * Similar methods (onCreateViewHolder, onBindViewHolder, etc.) allow users to define
     * the item views.
     *
     * @param <VH> - the ViewHolder used by the adapter
     */
    public static abstract class Adapter<VH extends ViewHolder> {
        public OnDataSetChangedListener onDataChangedListener;
        private Map<Integer, ViewHolder> viewHolderCache;

        public Adapter() { viewHolderCache = new HashMap<>(); }

        public abstract int getItemCount();

        public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

        public abstract void onBindViewHolder(VH holder, int position);

        final void bindViewHolder(VH holder, int position) {
            onBindViewHolder(holder, position);
            holder.position = position;
            viewHolderCache.put(position, holder);
        }

        final VH createViewHolder(ViewGroup parent, int viewType) {
            return onCreateViewHolder(parent, viewType);
        }

        public int getItemViewType(int position) { return 0; }

        public void setOnDataChangedListener(OnDataSetChangedListener listener) {
            this.onDataChangedListener = listener;
        }

        public void notifyDataSetChanged() {
            if (onDataChangedListener != null) onDataChangedListener.onDataSetChanged();
        }

        Map<Integer, ViewHolder> getViewHolderCache() { return viewHolderCache; }

        void clearViewHolderCache() { viewHolderCache.clear(); }
    }

    /**
     * ViewHolder used by Adapter
     *
     * To use, extend this class just like you would RecyclerView.Holder.
     */
    public static abstract class ViewHolder {

        View view;
        int position;

        public ViewHolder(final View view) { this.view = view; }

        public int getPosition() { return position; }
    }

    /**
     * Abstract LayoutManager allows for different layout implementations.
     *
     * Current implementations are LinearLayoutManager and GridLayoutManager.
     */
    public static abstract class LayoutManager {

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

        void addDividerView(final int dividerLayoutRes) {
            if (materialListView.hasDivider()) {
                final ImageView imageView = new ImageView(context);
                imageView.setImageDrawable(ContextCompat.getDrawable(context, dividerLayoutRes));
                materialListView.getContentView().addView(imageView);
            }
        }

        Context getContext() { return context; }

        MaterialListView getMaterialListView() { return materialListView; }
    }

    /**
     * Listener for detecting when an list item is clicked.
     */
    public interface OnItemClickListener {
        void onItemClicked(final MaterialListView materialListView, final int position, final View v);
    }

    /**
     * Listener for detecting when the data in a list is changed.
     */
    public interface OnDataSetChangedListener {
        void onDataSetChanged();
    }

}
