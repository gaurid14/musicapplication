package com.example.musicapplication.homepage;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapplication.R;
import com.example.musicapplication.database.CSVHandler;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    //    private String[] theAssetFiles=getAssetList();
//    private String fileName;
//    private ArrayList<String> theData = readCSV();
//    private String cleanData;
    public static final String[] songs = new String[]{"Shape of you", "Despacito", "Desi Boyz", "Kamariya", "Sau Dard"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        CSVHandler csvReader = new CSVHandler(getApplicationContext());
//        String[] columnData = csvReader.getColumnData(1);


//        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.search);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songs);
//        autoCompleteTextView.setAdapter(adapter);

//        InputStream inputStream = null;
//        try {
//            inputStream = getResources().getAssets().open("english.csv");
//            inputStream = getResources().getAssets().open("hindi.csv");
//            inputStream = getResources().getAssets().open("marathi.csv");
//            inputStream = getResources().getAssets().open("tamil.csv");
//            inputStream = getResources().getAssets().open("punjabi.csv");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
//
//        List<String[]> rows = null;
//        try {
//            rows = csvReader.readAll();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        List<String> columnData = new ArrayList<>();
//
//        for (String[] row : rows) {
//            columnData.add(row[0]);
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.search);
        autoCompleteTextView.setAdapter(adapter);

    }
}
