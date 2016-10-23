package com.polydevops.materialwidgets.library.materialSpinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polydevops.materialwidgets.R;
import com.polydevops.materialwidgets.library.materialAdapter.MaterialAdapter;

/**
 * Custom 'Spinner' implementation that is stylized more for Material Design and features more
 * 'user-friendly' and customizable API.
 * <p>
 * Implementation of adapter for adding items to the spinner drop down is similar to that provided
 * by RecyclerView.Adapter and RecyclerView.ViewHolder.
 * <p>
 * Customization options include the ability to set 'hint text', set whether or not elevation is enabled
 * (turning elevation off introduces a simpler dialog), set a drawable for a list divider, etc.
 */
public class MaterialSpinner extends FrameLayout implements View.OnClickListener {

    private LinearLayout spinnerLayout;
    private TextView spinnerTextView;
    private ImageView spinnerIconImageView;
    private MaterialSpinnerDropDown spinnerDropDown;

    public MaterialSpinner(Context context) {
        super(context);
        initializeView(context, null);
    }

    public MaterialSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context, attrs);
    }

    public MaterialSpinnerDropDown getDropDown() { return spinnerDropDown; }

    public void setAdapter(MaterialAdapter adapter) {
        spinnerDropDown.setAdapter(adapter);
    }

    public MaterialAdapter getAdapter() {
        return spinnerDropDown.getAdapter();
    }

    public void setOnDropDownItemClickListener(final OnDropDownItemClickedListener listener) {
        spinnerDropDown.setOnDropDownItemClickedListener(listener);
    }

    public void setSpinnerText(final String text) {
        spinnerTextView.setText(text);
    }

    public String getSpinnerText() {
        return spinnerTextView.getText().toString();
    }

    public void setSpinnerIcon(final int icon) {
        setSpinnerIcon(ContextCompat.getDrawable(getContext(), icon));
    }

    public void setSpinnerIcon(final Drawable icon) {
        if (icon == null) {
            spinnerIconImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_drop_down));
        } else {
            spinnerIconImageView.setImageDrawable(icon);
        }
    }

    public void closeDropdown() {
        spinnerDropDown.dismiss();
    }

    public void openDropdown() {
        spinnerDropDown.show();
    }

    int getLeftMargin() {
        final MarginLayoutParams params = (MarginLayoutParams) spinnerLayout.getLayoutParams();
        return params.leftMargin;
    }

    int getRealWidth() {
        return getWidth() - getMargins() - getPadding();
    }


    private void initializeView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.material_spinner, this);

        spinnerLayout = (LinearLayout) findViewById(R.id.layout_material_spinner);
        spinnerTextView = (TextView) findViewById(R.id.text_material_spinner);
        spinnerIconImageView = (ImageView) findViewById(R.id.icon_material_spinner);

        final MaterialSpinnerDropDownAttributeSet dropDownAttributeSet = new MaterialSpinnerDropDownAttributeSet(context, attrs);
        spinnerDropDown = new MaterialSpinnerDropDown(this, dropDownAttributeSet);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialSpinner, 0, 0);
        try {
            setSpinnerText(a.getString(R.styleable.MaterialSpinner_spinnerHint));
            setSpinnerTextMargins(
                    a.getDimensionPixelSize(R.styleable.MaterialSpinner_spinnerTextLeftMargin, 0),
                    a.getDimensionPixelSize(R.styleable.MaterialSpinner_spinnerTextTopMargin, 0),
                    a.getDimensionPixelSize(R.styleable.MaterialSpinner_spinnerTextRightMargin, 0),
                    a.getDimensionPixelSize(R.styleable.MaterialSpinner_spinnerTextBottomMargin, 0)
            );

            setSpinnerIcon(a.getDrawable(R.styleable.MaterialSpinner_spinnerIcon));
            setSpinnerIconMargins(
                    a.getDimensionPixelSize(R.styleable.MaterialSpinner_spinnerIconLeftMargin, 0),
                    a.getDimensionPixelSize(R.styleable.MaterialSpinner_spinnerIconTopMargin, 0),
                    a.getDimensionPixelSize(R.styleable.MaterialSpinner_spinnerIconRightMargin, 0),
                    a.getDimensionPixelSize(R.styleable.MaterialSpinner_spinnerIconBottomMargin, 0)
            );
        } finally {
            a.recycle();
        }

        setOnClickListener(this);

        post(new Runnable() {
            @Override
            public void run() {
                final int width = getRealWidth();
                spinnerDropDown.setWidth(width);
            }
        });
    }

    private void setSpinnerTextMargins(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        final ViewGroup.MarginLayoutParams params = (MarginLayoutParams) spinnerTextView.getLayoutParams();
        params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
    }

    private void setSpinnerIconMargins(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        final ViewGroup.MarginLayoutParams params = (MarginLayoutParams) spinnerIconImageView.getLayoutParams();
        params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
    }

    private int getMargins() {
        final MarginLayoutParams params = (MarginLayoutParams) spinnerLayout.getLayoutParams();
        return params.leftMargin + params.rightMargin;
    }

    private int getPadding() {
        return spinnerLayout.getPaddingLeft() + spinnerLayout.getPaddingRight();
    }

    /**
     * On click listener for when the spinner button is clicked.
     * Opens or closes the spinner depending on its current state
     *
     * @param v - the spinner button
     */
    @Override
    public void onClick(View v) {
        if (spinnerDropDown.isShowing()) {
            closeDropdown();
        } else {
            openDropdown();
        }
    }
}
