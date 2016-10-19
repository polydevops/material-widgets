package com.polydevops.materialwidgets.materialAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.polydevops.materialwidgets.materialListView.OnDataSetChangedListener;

import java.util.List;

/**
 * Adapter for use with material-widgets, particular MaterialSpinner and MaterialListView.
 *
 * To use, extend this class just like you would a RecyclerView.Adapter.
 * Similar methods (onCreateViewHolder, onBindViewHolder, etc.) allow users to define
 * the item views.
 *
 * @param <VH> - the ViewHolder used by the adapter
 */
public abstract class MaterialAdapter<T, VH extends MaterialAdapter.ViewHolder> extends BaseAdapter {

    private List<T> data;

    private OnDataSetChangedListener onDataChangedListener;

    public MaterialAdapter(List<T> data) {
        this.data = data;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position);

    final void bindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position);
        holder.position = position;
    }

    final VH createViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent, viewType);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final VH holder = createViewHolder(parent, getItemViewType(position));
        bindViewHolder(holder, position);

        return holder.itemView;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    /**
     * Override if you want to take advantage of this convenience method
     *
     * @param position - index
     * @return - the id of the object corresponding to the index. Default is 0
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Returns the size of the data backing the adapter
     *
     * @return - size of the data backing the adapter
     */
    public int getCount() {
        return data.size();
    }

    /**
     * Returns the data backing the adapter
     *
     * @return - the data backing the adapter
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Override if you need to inflate different layouts for different data
     *
     * @param position - index
     * @return - viewType for a specific index
     */
    public int getItemViewType(int position) { return 0; }

    public void setOnDataChangedListener(OnDataSetChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }

    public void notifyDataSetChanged() {
        if (onDataChangedListener != null) onDataChangedListener.onDataSetChanged();
    }

    public static abstract class ViewHolder {
        
        View itemView;
        int position;
        
        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
        
        public int getPosition() { return position; }
    }
}
