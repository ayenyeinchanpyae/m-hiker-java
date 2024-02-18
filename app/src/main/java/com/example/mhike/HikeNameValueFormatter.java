package com.example.mhike;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

public class HikeNameValueFormatter extends ValueFormatter {

    private final List<String> hikeNames;

    public HikeNameValueFormatter(List<String> hikeNames) {
        this.hikeNames = hikeNames;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int index = (int) value;
        if (index >= 0 && index < hikeNames.size()) {
            return hikeNames.get(index);
        }
        return "";
    }
}
