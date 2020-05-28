package com.example.mymoviemenoir.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportFragment extends Fragment {

    private NetworkConnection networkConnection = null;
    private DatePickerDialog.OnDateSetListener onDateSetListenerStartDate;
    private DatePickerDialog.OnDateSetListener onDateSetListenerEndDate;
    private View view = null;
    private PieChart pieChart;
    private BarChart barChart;
    private Button plotPieChartBtn;
    private TextView startDateTV;
    private TextView endDateTV;
    private Spinner barchartYearSpinner;
    private ArrayAdapter<String> spinnerAdapter;

    private String uid;
    private String startDate;
    private String endDate;

    private ArrayList<PieEntry> pieEntries;
    private ArrayList<BarEntry> barEntries;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_reports, container, false);
        networkConnection = new NetworkConnection();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("USERID", null);

        //Get start date
        startDateTV = view.findViewById(R.id.startDateTV);
        startDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReportFragment.this.getContext(), onDateSetListenerStartDate, year, month, day);

                dialog.show();
            }
        });

        onDateSetListenerStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                startDate = dayOfMonth + "/" + month + "/" + year;
                startDateTV.setText(startDate);
                startDate = dayOfMonth + "-" + month + "-" + year;

            }
        };

        //Get end date
        endDateTV = view.findViewById(R.id.endDateTV);
        endDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReportFragment.this.getContext(), onDateSetListenerEndDate, year, month, day);

                dialog.show();
            }
        });

        onDateSetListenerEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                endDate = dayOfMonth + "/" + month + "/" + year;
                endDateTV.setText(endDate);
                endDate = dayOfMonth + "-" + month + "-" + year;

            }
        };


        //Get the piechart ready
        pieChart = view.findViewById(R.id.pieChart);
        plotPieChartBtn = view.findViewById(R.id.plotBtn);
        plotPieChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Query the numbers
                GetPieChartResultTask getPieChartResultTask = new GetPieChartResultTask();
                getPieChartResultTask.execute(uid, startDate, endDate);
            }
        });


        //Get the spinner ready
        //Populate the spinner with 5 years
        //Get current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> yearList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            yearList.add(String.valueOf(currentYear - i));
        }
        spinnerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, yearList);
        barchartYearSpinner = view.findViewById(R.id.barchartYearSpinner);
        barchartYearSpinner.setAdapter(spinnerAdapter);

        barChart = view.findViewById(R.id.barChart);
        barchartYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Get the barChart ready
                String selectedYear = parent.getItemAtPosition(position).toString();
                if(selectedYear != null){
                    //Query for the year
                    GetBarChartResultTask getBarChartResultTask = new GetBarChartResultTask();
                    getBarChartResultTask.execute(uid, selectedYear);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        return view;
    }

    private class GetPieChartResultTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.findByIdDate(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(String result) {
            //Extract Json -> make PieEntry -> Add to arraylist
            try {
                pieEntries = new ArrayList<PieEntry>();
                JSONArray jsonArray = new JSONArray(result);
                int numberOfItems = jsonArray.length();
                if(numberOfItems > 0) {
                    //Count total
                    int total = 0;
                    for (int i = 0; i < numberOfItems; i++) {
                        total += jsonArray.getJSONObject(i).getInt("Count");
                    }

                    //Make pieEntry with %
                    for (int i = 0; i < numberOfItems; i++) {
                        JSONObject thisLocation = jsonArray.getJSONObject(i);
                        int thisCount = thisLocation.getInt("Count");
                        float thisPercentage = ((float)thisCount / (float)total) * 100;
                        String thisSuburb = thisLocation.getString("Suburb");
                        PieEntry thisPieEntry = new PieEntry(thisPercentage, thisSuburb);
                        pieEntries.add(thisPieEntry);
                    }

                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
                    pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    pieChart.setEntryLabelColor(Color.BLACK);

                    PieData pieData = new PieData(pieDataSet);
                    pieData.setValueTextSize(40f);
                    pieChart.setData(pieData);
                    pieChart.invalidate();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetBarChartResultTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.countMoviePerMonth(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                barEntries = new ArrayList<BarEntry>();
                final List<String> xLabels = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(result);
                int numberOfItems = jsonArray.length();
                if (numberOfItems > 0) {
                    //Get BarEntry ready and populate the arraylist
                    for (int i = 0; i < numberOfItems; i++) {
                        JSONObject thisMonth = jsonArray.getJSONObject(i);
                        barEntries.add(new BarEntry(i, thisMonth.getInt("Count")));
                        xLabels.add(thisMonth.getString("Month"));
                    }

                    BarDataSet barDataSet = new BarDataSet(barEntries, "");
                    BarData barData = new BarData();
                    barData.addDataSet(barDataSet);

                    barChart.getAxisLeft().setAxisMinimum(0);
                    barChart.getAxisRight().setAxisMinimum(0);
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setAxisMinimum(0);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            axis.setLabelCount(12, true);
                            return xLabels.get((int) value);
                        }
                    });

                    barChart.getLegend().setEnabled(false);
                    barChart.getDescription().setEnabled(false);
                    barChart.setDrawGridBackground(false);
                    barChart.setData(barData);
                    barChart.invalidate();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
