package com.polydevops.materialwidgetssampleapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polydevops.materialwidgets.materialListView.MaterialListView;

import java.util.List;

/**
 * Created by connor on 10/14/16.
 */

public class MaterialListViewStringAdapter extends MaterialListView.Adapter<MaterialListViewStringAdapter.StringViewHolder> {

    private List<String> strings;

    public MaterialListViewStringAdapter(final List<String> strings) {
        this.strings = strings;
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_spinner, null);
        return new StringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        final String text = strings.get(position);
        holder.textView.setText(text);
    }

    public List<String> getData() {
        return strings;
    }

    public String getItem(int position) {
        return strings.get(position);
    }

    public static class StringViewHolder extends MaterialListView.ViewHolder {

        TextView textView;

        public StringViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_item_material_widgets);
        }
    }
}
