package com.polydevops.materialwidgets.materialSpinner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.polydevops.materialwidgets.R;

/**
 * The 'drop down' for the Material Spinner. Provides a variety of customizations, including setting
 * the vertical offset, enabling/disabling elevation (turning off elevation uses a simpler dialog layout),
 * the drawable for the item divider, etc.
 *
 * Class also includes a Builder.
 */
public class MaterialSpinnerDropDown extends PopupWindow {

    private MaterialSpinner spinner;

    private ListView listView;

    private boolean isShowing;

    private int verticalOffset = 0;


    public MaterialSpinnerDropDown(@NonNull Context context) {
        super(context);
        initializeView(context);
    }

    public MaterialSpinnerDropDown(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    private void initializeView(final Context context) {

        // this allows the drop down to be dismissed
        // when the user clicks outside or hits the back button
        setOutsideTouchable(true);
        setFocusable(true);

        listView = new ListView(context);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSpinnerText(spinner.getAdapter().getItem(position).toString()); // TODO: This should set a view rather than text
                if (spinner.getOnDropdownItemClickedListener() != null) {
                    spinner.getOnDropdownItemClickedListener().onDropDownItemClicked(view, position);
                }
            }
        });

        setContentView(listView);
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShowing = false;
    }

    public void setVerticalOffset(final int offset) {
        this.verticalOffset = offset;
    }

    public void show() {
        super.showAsDropDown(spinner, 0, verticalOffset);
        isShowing = true;
    }

    public ListView getListView() {
        return listView;
    }

    private void setSpinner(MaterialSpinner spinner) {
        this.spinner = spinner;
    }

    private MaterialSpinner getSpinner() {
        return spinner;
    }

    static class Builder {

        private MaterialSpinnerDropDown instance;

        public Builder(final Context context) {
            instance = new MaterialSpinnerDropDown(context);
        }

        public Builder setAdapter(final ListAdapter adapter) {
            instance.getListView().setAdapter(adapter);
            return this;
        }

        public Builder setSpinner(final MaterialSpinner spinner) {
            instance.setSpinner(spinner);
            return this;
        }

        public Builder setVerticalOffset(int verticalOffset) {
            instance.setVerticalOffset(verticalOffset);
            return this;
        }

        public Builder setItemDivider(final Drawable itemDivider) {
            instance.getListView().setDivider(itemDivider);
            return this;
        }

        public Builder enableElevation(boolean enabled) {
            if (enabled) {
                if (Build.VERSION.SDK_INT >= 21) {
                    instance.setBackgroundDrawable(ContextCompat.getDrawable(instance.getSpinner().getContext(), R.drawable.material_spinner_drop_down_simple_background));
                    instance.setElevation(16);
                }
            } else {
                instance.setBackgroundDrawable(ContextCompat.getDrawable(instance.getSpinner().getContext(), R.drawable.material_spinner_drop_down_simple_background));
                instance.setAnimationStyle(android.R.style.Animation_Dialog);
            }

            return this;
        }

        public MaterialSpinnerDropDown create() {
            return instance;
        }
    }

}
