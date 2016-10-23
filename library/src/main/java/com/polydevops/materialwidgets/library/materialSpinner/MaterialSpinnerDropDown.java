package com.polydevops.materialwidgets.library.materialSpinner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.polydevops.materialwidgets.R;
import com.polydevops.materialwidgets.library.materialAdapter.MaterialAdapter;

/**
 * The 'drop down' for the Material Spinner. Provides a variety of customizations, including setting
 * the vertical offset, enabling/disabling elevation (turning off elevation uses a simpler dialog layout),
 * the drawable for the item divider, etc.
 *
 */
public class MaterialSpinnerDropDown extends PopupWindow {

    private Context context;

    private MaterialSpinner spinner;
    private MaterialAdapter adapter;

    private ListView listView;

    private OnDropDownItemClickedListener listener;

    private int verticalOffset;
    private int horizontalOffset;
    private boolean dismissOnItemClick;

    public MaterialSpinnerDropDown(@NonNull MaterialSpinner spinner, @NonNull MaterialSpinnerDropDownAttributeSet attrs) {
        super(spinner.getContext());

        this.context = spinner.getContext();
        this.spinner = spinner;

        initializeView(context, attrs);
    }

    public void setVerticalOffset(final int offset) {
        this.verticalOffset = offset;
    }

    public void setHorizontalOffset(final int offset) { this.horizontalOffset = offset;}

    public void setAdapter(final MaterialAdapter adapter) {
        this.adapter = adapter;
        onAdapterAttached();
    }

    public MaterialAdapter getAdapter() {
        return adapter;
    }

    public void setOnDropDownItemClickedListener(final OnDropDownItemClickedListener listener) {
        this.listener = listener;
    }

    public void setItemDivider(final Drawable itemDivider) {
        listView.setDivider(itemDivider);
    }

    public void enableElevation(boolean enabled) {
        if (enabled) {
            if (Build.VERSION.SDK_INT >= 21) {
                setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                setElevation(16);
            } else {
                setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.material_spinner_background_shadow));
                //setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.material_spinner_background_shadow));
            }
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                setElevation(0);
            }
            setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.material_spinner_drop_down_simple_background));
        }
    }

    public void setDismissOnItemClickEnabled(boolean enabled) {
        this.dismissOnItemClick = enabled;
    }

    public void show() {
        super.showAsDropDown(spinner, horizontalOffset, verticalOffset);
    }

    public ListView getListView() {
        return listView;
    }

    private void initializeView(final Context context, final MaterialSpinnerDropDownAttributeSet attrs) {

        // this allows the drop down to be dismissed
        // when the user clicks outside or hits the back button
        setOutsideTouchable(true);
        setFocusable(true);

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        listView = new ListView(context);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSpinnerText(spinner.getAdapter().getItem(position).toString());
                listener.onDropDownItemClicked(spinner.getAdapter(), view, position);
                if (dismissOnItemClick) {
                    spinner.closeDropdown();
                }
            }
        });
        setContentView(listView);

        verticalOffset = attrs.getVerticalOffset();
        horizontalOffset = attrs.getHoriziontalOffset() + spinner.getLeftMargin();
        dismissOnItemClick = attrs.isDismissOnItemClickEnabled();
        setItemDivider(attrs.getItemDivider());
        enableElevation(attrs.isElevationEnabled());
    }

    private void onAdapterAttached() {
        listView.setAdapter(adapter);

        // On APIs before 21, the popup window needs to resize itself when the size of the ListView
        // is changed
        if (Build.VERSION.SDK_INT < 21) {
            listView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (isShowing() && Build.VERSION.SDK_INT < 21) {
                        update(spinner.getRealWidth(), listView.getHeight());
                        listView.removeOnLayoutChangeListener(this);
                    }
                }
            });
        }
    }

}
