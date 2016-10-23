package com.polydevops.materialwidgets.library.materialAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polydevops.materialwidgets.R;

import java.util.List;

/**
 * Basic string adapter for MaterialAdapter. Takes in strings and nicely displays them.
 */
public class MaterialStringAdapter extends MaterialAdapter<String, MaterialStringAdapter.StringViewHolder> {

    public MaterialStringAdapter(final List<String> strings) {
        super(strings);
    }

    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        holder.stringTextView.setText(getItem(position));
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_simple_text, null);
        return new StringViewHolder(itemView);
    }

    public class StringViewHolder extends MaterialAdapter.ViewHolder {

        TextView stringTextView;

        public StringViewHolder(View itemView) {
            super(itemView);

            stringTextView = (TextView) itemView.findViewById(R.id.text_item_material_widgets);
        }
    }
}
