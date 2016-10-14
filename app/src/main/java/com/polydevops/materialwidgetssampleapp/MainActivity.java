package com.polydevops.materialwidgetssampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.polydevops.materialwidgets.materialSpinner.MaterialSpinner;
import com.polydevops.materialwidgets.materialSpinner.MaterialSpinnerAdapter;
import com.polydevops.materialwidgets.materialSpinner.MaterialSpinnerStringAdapter;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final List<String> spinnerItems = Arrays.asList("One", "Two", "Red", "Blue");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MaterialSpinner materialSpinner = (MaterialSpinner) findViewById(R.id.material_spinner);
        final MaterialSpinnerAdapter materialSpinnerAdapter = new MaterialSpinnerStringAdapter(spinnerItems);
        materialSpinner.setAdapter(materialSpinnerAdapter);
    }
}
