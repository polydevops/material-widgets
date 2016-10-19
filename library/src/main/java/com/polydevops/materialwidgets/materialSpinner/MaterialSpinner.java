package com.polydevops.materialwidgets.materialSpinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.polydevops.materialwidgets.R;
import com.polydevops.materialwidgets.materialAdapter.MaterialAdapter;

/**
 * Custom 'Spinner' implementation that is stylized more for Material Design and features and more
 * 'user-friendly' and customizable API.
 *
 * Implementation of adapter for adding items to the spinner drop down is fairly similar to that provided
 * by RecyclerView.Adapter and RecyclerView.ViewHolder.
 *
 * Customization options include the ability to set 'hint text', set whether or not elevation is enabled
 * (turning elevation off introduces a simpler dialog), set a drawable for a list divider, etc.
 */
public class MaterialSpinner extends FrameLayout {

    private TextView spinnerTextView;
    private ImageView spinnerIconImageView;
    private MaterialSpinnerDropDown spinnerDropDown;

    private Drawable spinnerIcon;
    private String hintText;

    private MaterialAdapter adapter;

    public MaterialSpinner(Context context) {
        super(context);
        initializeView(context, null);
    }

    public MaterialSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context, attrs);
    }

    public void setAdapter(MaterialAdapter adapter) {
        this.adapter = adapter;
    }

    public MaterialAdapter getAdapter() {
        return adapter;
    }

    public void setOnDropdownItemClickedListener(final OnDropDownItemClickedListener listener) {
        spinnerDropDown.setOnDropDownItemClickedListener(new OnDropDownItemClickedListener() {
            @Override
            public void onDropDownItemClicked(View view, int position) {
                setSpinnerText(getAdapter().getItem(position).toString()); // TODO: This should set a view rather than text
                if (listener != null) {
                    listener.onDropDownItemClicked(view, position);
                }
                closeDropdown();
            }
        });
    }

    public void setSpinnerText(final String text) {
        spinnerTextView.setText(text);
    }

    public void setSpinnerIcon(final int icon) {
        spinnerIcon = ContextCompat.getDrawable(getContext(), icon);
        spinnerIconImageView.setImageDrawable(spinnerIcon);
    }

    public void closeDropdown() {
        if (spinnerDropDown != null) {
            spinnerDropDown.dismiss();
        }
    }

    public void openDropdown() {
        if (adapter != null) {
            spinnerDropDown
                    .setAdapter(adapter)
                    .show();
        }
    }

    private void initializeView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.material_spinner, this);

        final MaterialSpinnerDropDownAttributeSet dropDownAttributeSet = new MaterialSpinnerDropDownAttributeSet(context, attrs);
        spinnerDropDown = new MaterialSpinnerDropDown(this, dropDownAttributeSet);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialSpinner, 0, 0);
        try {
            hintText = a.getString(R.styleable.MaterialSpinner_hint);
            spinnerIcon = a.getDrawable(R.styleable.MaterialSpinner_spinnerIcon);

            if (spinnerIcon == null) {
                spinnerIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_drop_down);
            }

        } finally {
            a.recycle();
        }

        spinnerTextView = (TextView) findViewById(R.id.text_material_spinner);
        spinnerTextView.setText(hintText);

        spinnerIconImageView = (ImageView) findViewById(R.id.icon_material_spinner);
        spinnerIconImageView.setImageDrawable(spinnerIcon);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinnerDropDown.isShowing()) {
                    closeDropdown();
                } else {
                    openDropdown();
                }
            }
        });
    }
}
