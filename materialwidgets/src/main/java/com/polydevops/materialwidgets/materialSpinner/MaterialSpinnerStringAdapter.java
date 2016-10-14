package com.polydevops.materialwidgets.materialSpinner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polydevops.materialwidgets.R;

import java.util.List;

/**
 * Created by connor on 10/11/16.
 */

public class MaterialSpinnerStringAdapter extends MaterialSpinnerAdapter<MaterialSpinnerStringAdapter.StringViewHolder> {

    private List<String> strings;

    public MaterialSpinnerStringAdapter(final List<String> strings) {
        this.strings = strings;
    }

    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        holder.stringTextView.setText(strings.get(position));
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_spinner, null);
        return new StringViewHolder(itemView);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public String getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class StringViewHolder extends MaterialSpinnerAdapter.ViewHolder {

        TextView stringTextView;

        public StringViewHolder(View itemView) {
            super(itemView);

            stringTextView = (TextView) itemView.findViewById(R.id.text_item_material_spinner);
        }
    }
}
