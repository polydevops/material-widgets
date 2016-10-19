package com.polydevops.materialwidgetssampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.polydevops.materialwidgets.materialAdapter.MaterialAdapter;
import com.polydevops.materialwidgets.materialAdapter.MaterialStringAdapter;
import com.polydevops.materialwidgets.materialListView.LinearLayoutManager;
import com.polydevops.materialwidgets.materialListView.MaterialListView;
import com.polydevops.materialwidgets.materialSpinner.MaterialSpinner;
import com.polydevops.materialwidgets.materialSpinner.OnDropDownItemClickedListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final List<String> spinnerItems = Arrays.asList("One", "Two", "Red", "Blue");

    private static final List<String> listItems = Arrays.asList("Wesley Crusher",
            "Geordi LeForge",
            "William T. Riker",
            "Deanna Troi",
            "Jean Luc Picard",
            "Cathrine Pulaski",
            "Worf",
            "Data",
            "Beverely Crusher");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MaterialSpinner materialSpinner = (MaterialSpinner) findViewById(R.id.material_spinner);
        final MaterialStringAdapter materialSpinnerAdapter = new MaterialStringAdapter(spinnerItems);
        materialSpinner.setAdapter(materialSpinnerAdapter);
        materialSpinner.setOnDropdownItemClickedListener(new OnDropDownItemClickedListener() {
            @Override
            public void onDropDownItemClicked(View view, int position) {
                Toast.makeText(MainActivity.this, materialSpinnerAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

        final MaterialListView materialListView = (MaterialListView) findViewById(R.id.material_list_view);
        final MaterialAdapter adapter = new MaterialStringAdapter(listItems);
        materialListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        materialListView.setAdapter(adapter);
        materialListView.setDivider(R.drawable.material_spinner_item_divider);
        materialListView.setOnItemClickListener(new MaterialListView.OnItemClickListener() {
            @Override
            public void onItemClicked(MaterialListView materialListView, int position, View v) {
                final String string = ((MaterialStringAdapter) materialListView.getAdapter()).getItem(position);
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
