
package com.example.test1application;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.example.test1application.custom.MyMarkerView;
import com.example.test1application.notimportant.DemoBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

/**
 * Example of a heavily customized {@link LineChart} with limit lines, custom line shapes, etc.
 *
 * @since 1.7.4
 * @version 3.1.0
 */
public class LineChartActivity1 extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private LineChart chart;
//    private SeekBar seekBarX, seekBarY;
//    private TextView tvX, tvY;
    private DatabaseReference mDatabase;
    private String userid;
    private String mus;
    private String idtag;
    private String arrayc[] = new String[55];
    private List<String> contents = new ArrayList<String>();
    private boolean checkS = false;
    int count1 =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart);

        contents = new ArrayList<>();


        userid = getIntent().getStringExtra("userid");
        mus = getIntent().getStringExtra("mus");
        idtag = String.valueOf((getIntent().getIntExtra("idtag",1)));

        setTitle("LineChartActivity1");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query query = mDatabase.child("users").child(userid).child("exercise").child(mus).child(idtag).orderByValue();
        query.limitToLast(50).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    checkS = true;
                    //arrayc[count1] = postSnapshot.child("acc").getValue().toString();
                    contents.add(count1,postSnapshot.child("acc").getValue().toString());
                    //Log.i("data001",postSnapshot.child("acc").getValue().toString());
                    count1++;
                    Log.i("data001","출력");

                }
                count1=0;
                if(checkS){

//            tvX = findViewById(R.id.tvXMax);
//            tvY = findViewById(R.id.tvYMax);
//
//            seekBarX = findViewById(R.id.seekBar1);
//            seekBarX.setOnSeekBarChangeListener(this);
//
//            seekBarY = findViewById(R.id.seekBar2);
//            seekBarY.setMax(180);
//            seekBarY.setOnSeekBarChangeListener(this);


                    {   // // Chart Style // //
                        chart = findViewById(R.id.chart1);

                        // background color
                        chart.setBackgroundColor(Color.WHITE);

                        // disable description text
                        chart.getDescription().setEnabled(false);

                        // enable touch gestures
                        chart.setTouchEnabled(true);

                        // set listeners
                        chart.setOnChartValueSelectedListener(LineChartActivity1.this);
                        chart.setDrawGridBackground(false);

                        // create marker to display box when values are selected
                        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);

                        // Set the marker to the chart
                        mv.setChartView(chart);
                        chart.setMarker(mv);

                        // enable scaling and dragging
                        chart.setDragEnabled(true);
                        chart.setScaleEnabled(true);
                        // chart.setScaleXEnabled(true);
                        // chart.setScaleYEnabled(true);

                        // force pinch zoom along both axis
                        chart.setPinchZoom(true);
                    }

                    XAxis xAxis;
                    {   // // X-Axis Style // //
                        xAxis = chart.getXAxis();

                        // vertical grid lines
                        xAxis.enableGridDashedLine(10f, 10f, 0f);
                    }

                    YAxis yAxis;
                    {   // // Y-Axis Style // //
                        yAxis = chart.getAxisLeft();

                        // disable dual axis (only use LEFT axis)
                        chart.getAxisRight().setEnabled(false);

                        // horizontal grid lines
                        yAxis.enableGridDashedLine(10f, 10f, 0f);

                        // axis range
                        yAxis.setAxisMaximum(200f);
                        yAxis.setAxisMinimum(-50f);
                    }


                    {   // // Create Limit Lines // //
                        LimitLine llXAxis = new LimitLine(9f, "Index 10");
                        llXAxis.setLineWidth(4f);
                        llXAxis.enableDashedLine(10f, 10f, 0f);
                        llXAxis.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
                        llXAxis.setTextSize(10f);
                        llXAxis.setTypeface(tfRegular);

                        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
                        ll1.setLineWidth(4f);
                        ll1.enableDashedLine(10f, 10f, 0f);
                        ll1.setLabelPosition(LimitLabelPosition.RIGHT_TOP);
                        ll1.setTextSize(10f);
                        ll1.setTypeface(tfRegular);

                        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
                        ll2.setLineWidth(4f);
                        ll2.enableDashedLine(10f, 10f, 0f);
                        ll2.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
                        ll2.setTextSize(10f);
                        ll2.setTypeface(tfRegular);

                        // draw limit lines behind data instead of on top
                        yAxis.setDrawLimitLinesBehindData(true);
                        xAxis.setDrawLimitLinesBehindData(true);

                        // add limit lines
                        yAxis.addLimitLine(ll1);
                        yAxis.addLimitLine(ll2);
                        //xAxis.addLimitLine(llXAxis);
                    }

                    // 데이터 추가
//            seekBarX.setProgress(45);
//            seekBarY.setProgress(180);
                    setData(50, 10);

                    // draw points over time
                    //chart.animateX(1500);

                    // get the legend (only possible after setting data)
                    Legend l = chart.getLegend();

                    // draw legend entries as lines
                    l.setForm(LegendForm.LINE);
                    chart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setData(int count, float range) {
        String text;
        String[] aaaa=new String[5];
        String[][] senser= new String[50][10];
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();
        ArrayList<Entry> values3 = new ArrayList<>();


        for(int i=0;i<50;i++){
            //text = arrayc[i];
            text = contents.get(i);
            aaaa= text.split("/");
            for(int j=0;j<3;j++)
            {

                senser[i][j] = aaaa[j];
            }
        }


        for (int i = 0; i < count; i++) {

            float val = (float)(Float.parseFloat(senser[i][0])) ;               //  1번센서값
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));
            float val2 = (float)(Float.parseFloat(senser[i][1])) ;              //  2번센서값
            values2.add(new Entry(i, val2, getResources().getDrawable(R.drawable.star)));
            float val3 = (float)(Float.parseFloat(senser[i][2])) / range;       //  3번센서값
            values3.add(new Entry(i, val3, getResources().getDrawable(R.drawable.star)));
        }

        LineDataSet set1;
        LineDataSet set2;
        LineDataSet set3;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set2.setValues(values2);
            set2.notifyDataSetChanged();
            set3 = (LineDataSet) chart.getData().getDataSetByIndex(2);
            set3.setValues(values3);
            set3.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "1번 압력센서");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            set2 = new LineDataSet(values2, "2번 압력센서");

            set2.setDrawIcons(false);

            // draw dashed line
            set2.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set2.setColor(Color.BLACK);
            set2.setCircleColor(Color.BLACK);

            // line thickness and point size
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);

            // draw points as solid circles
            set2.setDrawCircleHole(false);

            // customize legend entry
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);

            // text size of values
            set2.setValueTextSize(9f);

            // draw selection line as dashed
            set2.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set2.setDrawFilled(true);
            set2.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });



            set3 = new LineDataSet(values3, "3번 압력센서");

            set3.setDrawIcons(false);

            // draw dashed line
            set3.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set3.setColor(Color.BLACK);
            set3.setCircleColor(Color.BLACK);

            // line thickness and point size
            set3.setLineWidth(1f);
            set3.setCircleRadius(3f);

            // draw points as solid circles
            set3.setDrawCircleHole(false);

            // customize legend entry
            set3.setFormLineWidth(1f);
            set3.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set3.setFormSize(15.f);

            // text size of values
            set3.setValueTextSize(9f);

            // draw selection line as dashed
            set3.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set3.setDrawFilled(true);
            set3.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
                Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.fade_blue);
                set2.setFillDrawable(drawable2);
                Drawable drawable3 = ContextCompat.getDrawable(this, R.drawable.fade_yellow);
                set3.setFillDrawable(drawable3);
            } else {
                set1.setFillColor(Color.BLACK);
                set2.setFillColor(Color.BLACK);
                set3.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);
            dataSets.add(set3);// add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/LineChartActivity1.java"));
                startActivity(i);
                break;
            }
            case R.id.actionToggleValues: {
                List<ILineDataSet> sets = chart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }

                chart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                List<ILineDataSet> sets = chart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                chart.invalidate();
                break;
            }
            case R.id.animateXY: {
                chart.animateXY(2000, 2000);
                break;
            }
            case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart);
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//        tvX.setText(String.valueOf(seekBarX.getProgress()));
//        tvY.setText(String.valueOf(seekBarY.getProgress()));
//
//        setData(seekBarX.getProgress(), seekBarY.getProgress());

        // redraw
//        chart.invalidate();
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "LineChartActivity1");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOW HIGH", "low: " + chart.getLowestVisibleX() + ", high: " + chart.getHighestVisibleX());
        Log.i("MIN MAX", "xMin: " + chart.getXChartMin() + ", xMax: " + chart.getXChartMax() + ", yMin: " + chart.getYChartMin() + ", yMax: " + chart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
