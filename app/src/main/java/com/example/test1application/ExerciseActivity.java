package com.example.test1application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.example.test1application.listviewitems.BarChartItem;
import com.example.test1application.listviewitems.ChartItem;
import com.example.test1application.listviewitems.LineChartItem;
import com.example.test1application.listviewitems.PieChartItem;
import com.example.test1application.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);


    }

    public void mOnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button:
                ((MainActivity) MainActivity.mContext).sendData("s");

                break;
            case R.id.button2:
                ((MainActivity) MainActivity.mContext).sendData("s");
                break;
            case R.id.button3:
                ((MainActivity) MainActivity.mContext).sendData("s");
                break;
        }
    }

}


