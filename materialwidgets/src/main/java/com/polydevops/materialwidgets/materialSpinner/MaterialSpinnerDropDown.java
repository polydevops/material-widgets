package com.polydevops.materialwidgets.materialSpinner;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ListPopupWindow;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.polydevops.materialwidgets.R;

/**
 * Created by connor on 9/22/16.
 */

public class MaterialSpinnerDropDown extends ListPopupWindow {

    private MaterialSpinner spinner;

    private boolean isShowing;

    public MaterialSpinnerDropDown(@NonNull Context context) {
        super(context);
    }

    public MaterialSpinnerDropDown(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initializeView();
    }

    public MaterialSpinnerDropDown(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initializeView();
    }

    public MaterialSpinnerDropDown(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initializeView();
    }

    public static MaterialSpinnerDropDown newInstance(@NonNull Context context) {
        return new MaterialSpinnerDropDown(context);
    }



    private void initializeView() {
        setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSpinnerText(spinner.getAdapter().getItem(position).toString()); // TODO: This should set a view rather than text
                if (spinner.getOnDropdownItemClickedListener() != null) {
                    spinner.getOnDropdownItemClickedListener().onDropDownItemClicked(view, position);
                }
            }
        });
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void show() {
        super.show();
        isShowing = true;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShowing = false;
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
            instance.setAdapter(adapter);
            return this;
        }

        public Builder setSpinner(final MaterialSpinner spinner) {
            instance.setSpinner(spinner);
            instance.setAnchorView(spinner);
            return this;
        }

        public Builder setVerticalOffset(int verticalOffset) {
            instance.setVerticalOffset(verticalOffset);
            return this;
        }

        public Builder enableElevation(boolean enabled) {
            if (!enabled) {
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
