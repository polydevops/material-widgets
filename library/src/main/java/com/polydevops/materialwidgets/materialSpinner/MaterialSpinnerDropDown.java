package com.polydevops.materialwidgets.materialSpinner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.polydevops.materialwidgets.R;
import com.polydevops.materialwidgets.materialAdapter.MaterialAdapter;

/**
 * The 'drop down' for the Material Spinner. Provides a variety of customizations, including setting
 * the vertical offset, enabling/disabling elevation (turning off elevation uses a simpler dialog layout),
 * the drawable for the item divider, etc.
 *
 */
public class MaterialSpinnerDropDown extends PopupWindow {

    private Context context;

    private MaterialSpinner spinner;

    private ListView listView;

    private OnDropDownItemClickedListener listener;

    private int verticalOffset = 0;


    public MaterialSpinnerDropDown(@NonNull MaterialSpinner spinner, @NonNull MaterialSpinnerDropDownAttributeSet attrs) {
        super(spinner.getContext());

        this.context = spinner.getContext();
        this.spinner = spinner;

        initializeView(context, attrs);
    }

    public MaterialSpinnerDropDown setVerticalOffset(final int offset) {
        this.verticalOffset = offset;
        return this;
    }

    public MaterialSpinnerDropDown setAdapter(final MaterialAdapter adapter) {
        listView.setAdapter(adapter);
        return this;
    }

    public MaterialSpinnerDropDown setOnDropDownItemClickedListener(final OnDropDownItemClickedListener listener) {
        this.listener = listener;
        return this;
    }

    public MaterialSpinnerDropDown setItemDivider(final Drawable itemDivider) {
        listView.setDivider(itemDivider);
        return this;
    }

    public MaterialSpinnerDropDown enableElevation(boolean enabled) {
        if (enabled) {
            if (Build.VERSION.SDK_INT >= 21) {
                setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.material_spinner_drop_down_simple_background));
                setElevation(16);
            } else {
                setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.material_spinner_background_shadow));
            }
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                setElevation(0);
            }
            setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.material_spinner_drop_down_simple_background));
        }
        return this;
    }

    public void show() {
        super.showAsDropDown(spinner, 0, verticalOffset);
    }

    public ListView getListView() {
        return listView;
    }

    private void initializeView(final Context context, final MaterialSpinnerDropDownAttributeSet attrs) {

        // this allows the drop down to be dismissed
        // when the user clicks outside or hits the back button
        setOutsideTouchable(true);
        setFocusable(true);

        listView = new ListView(context);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onDropDownItemClicked(view, position);
            }
        });
        setContentView(listView);

        verticalOffset = attrs.getVerticalOffset();
        setItemDivider(attrs.getItemDivider());
        enableElevation(attrs.isElevationEnabled());
    }

}
