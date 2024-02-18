package com.example.mhike;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        BarChart barChart = findViewById(R.id.barChart);
        setupBarChart(barChart);

        // Replace this with your actual data retrieval logic
        List<String> hikeNames = getHikeNames();
        List<String> distances = databaseHelper.getAllHikeLengths();
        for (String hikeLength : distances) {
            Log.d("HikeLength", "Hike Length: " + hikeLength);
        }

        setChartData(barChart, hikeNames, distances);
    }

    private void setupBarChart(BarChart barChart) {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        //xAxis.setValueFormatter(new HikeNameValueFormatter()); // Set custom value formatter

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setChartData(BarChart barChart, List<String> hikeNames, List<String> distances) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < distances.size(); i++) {
            entries.add(new BarEntry(i, Float.parseFloat(distances.get(i))));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Hike Distances");
        dataSet.setColor(Color.BLUE); // Set the color of the bars

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f); // Set the width of the bars

        barChart.setData(barData);
        barChart.invalidate(); // Refresh the chart
    }

    private List<String> getHikeNames() {
        // Replace this with your actual data retrieval logic for hike names
        List<String> hikeNames = new ArrayList<>();
        hikeNames.add("Hike 1");
        hikeNames.add("Hike 2");
        hikeNames.add("Hike 3");
        // Add more hike names as needed
        return hikeNames;
    }
}
