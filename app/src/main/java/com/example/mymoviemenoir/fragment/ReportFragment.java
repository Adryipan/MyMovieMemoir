package com.example.mymoviemenoir.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ReportFragment extends Fragment {

    NetworkConnection networkConnection = null;
    View view = null;
    PieChart pieChart;
    int[] colourClassArray = new int[]{Color.GREEN, Color.BLUE, Color.YELLOW, Color.GRAY, Color.LTGRAY};

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_reports, container, false);

        pieChart = view.findViewById(R.id.pieChart);
        PieDataSet pieDataSet = new PieDataSet(dataValues(),"");
        pieDataSet.setColors();

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
        return view;
    }

    private ArrayList<PieEntry> dataValues(){
        ArrayList<PieEntry> dataVals = new ArrayList<>();

        dataVals.add(new PieEntry(50, "Sun"));
        dataVals.add(new PieEntry(50, "Mon"));

        return dataVals;
    }

}
