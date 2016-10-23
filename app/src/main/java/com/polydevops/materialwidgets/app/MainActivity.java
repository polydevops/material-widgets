package com.polydevops.materialwidgets.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.polydevops.materialwidgets.library.materialAdapter.MaterialAdapter;
import com.polydevops.materialwidgets.library.materialAdapter.MaterialStringAdapter;
import com.polydevops.materialwidgets.library.materialListView.LinearLayoutManager;
import com.polydevops.materialwidgets.library.materialListView.MaterialListView;
import com.polydevops.materialwidgets.library.materialSpinner.MaterialSpinner;
import com.polydevops.materialwidgets.library.materialSpinner.OnDropDownItemClickedListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final List<String> initialSpinnerItems = Arrays.asList("One", "Two", "Three", "Four", "More");
    private static final List<String> moreSpinnerItems = Arrays.asList("Red", "Blue", "Yellow", "Green");

    private static final List<String> starTrekItems = Arrays.asList(
            "Wesley Crusher",
            "Geordi LeForge",
            "William T. Riker",
            "Deanna Troi",
            "Jean Luc Picard",
            "Cathrine Pulaski"
    );

    private static final List<String> starWarsItems = Arrays.asList(
            "Luke Skywalker",
            "Obi-wan Kenobi",
            "Han Solo",
            "Darth Vader"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MaterialSpinner materialSpinner = (MaterialSpinner) findViewById(R.id.material_spinner);
        final MaterialStringAdapter materialSpinnerAdapter = new MaterialStringAdapter(initialSpinnerItems);
        materialSpinner.setAdapter(materialSpinnerAdapter);
        materialSpinner.setOnDropDownItemClickListener(new OnDropDownItemClickedListener() {
            @Override
            public void onDropDownItemClicked(MaterialAdapter adapter, View itemView, int position) {
                final String selectedString = (String) materialSpinner.getAdapter().getItem(position);
                if (selectedString.equalsIgnoreCase("More")) {
                    materialSpinner.setAdapter(new MaterialStringAdapter(moreSpinnerItems));
                } else {
                    Toast.makeText(MainActivity.this, (String) materialSpinner.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
                    materialSpinner.closeDropdown();
                }
            }
        });

        final Button starWarsButton = (Button) findViewById(R.id.btn_star_wars);
        final Button starTrekButton = (Button) findViewById(R.id.btn_star_trek);

        final MaterialListView materialListView = (MaterialListView) findViewById(R.id.material_list_view);
        final MaterialAdapter adapter = new MaterialStringAdapter(starWarsItems);
        materialListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        materialListView.setAdapter(adapter);
        materialListView.setDivider(R.drawable.material_item_divider);
        materialListView.setOnItemClickListener(new MaterialListView.OnItemClickListener() {
            @Override
            public void onItemClicked(MaterialListView materialListView, int position, View v) {
                final String string = ((MaterialStringAdapter) materialListView.getAdapter()).getItem(position);
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });

        starWarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialAdapter adapter = new MaterialStringAdapter(starWarsItems);
                materialListView.setAdapter(adapter);
            }
        });

        starTrekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialAdapter adapter = new MaterialStringAdapter(starTrekItems);
                materialListView.setAdapter(adapter);
            }
        });
    }
}
