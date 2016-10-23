package com.polydevops.materialwidgets.library.materialSpinner;

import android.view.View;

import com.polydevops.materialwidgets.library.materialAdapter.MaterialAdapter;

/**
 * Listener for detecting when a MaterialSpinnerDropDown item is clicked.
 */
public interface OnDropDownItemClickedListener {
    void onDropDownItemClicked(MaterialAdapter adapter, View itemView, int position);
}
