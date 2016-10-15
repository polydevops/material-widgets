package com.polydevops.materialwidgets.materialSpinner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter for MaterialSpinner. It extends the BaseAdapter, which allows it to also be used by ListView's
 */
public abstract class MaterialSpinnerAdapter<VH extends MaterialSpinnerAdapter.ViewHolder> extends BaseAdapter {

    public abstract void onBindViewHolder(VH holder, int position);
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    final void bindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position);
        holder.position = position;
    }

    final VH createViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent, viewType);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final VH holder = createViewHolder(viewGroup, getItemViewType(position));
        bindViewHolder(holder, position);

        return holder.itemView;
    }

    public abstract class ViewHolder {

        View itemView;
        int position;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}
