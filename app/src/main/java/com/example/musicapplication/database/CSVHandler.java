package com.example.musicapplication.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Parcelable;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHandler {
    private Context mContext;

    public CSVHandler(Context context) {
        mContext = context;
    }

    public String[] getColumnData(int columnIndex) {
        String[] columnData = null;

        try {
            InputStream inputStream;
            inputStream = mContext.getAssets().open("Files/songs.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            ArrayList<String> columnList = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > columnIndex) {
                    columnList.add(parts[columnIndex]);
                }
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            columnData = new String[columnList.size()];
            columnData = columnList.toArray(columnData);
//            System.out.println("Previous columnlist: " + columnList);
//            System.out.println("Previous columndata: " + Arrays.toString(columnData));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return columnData;
    }
}

