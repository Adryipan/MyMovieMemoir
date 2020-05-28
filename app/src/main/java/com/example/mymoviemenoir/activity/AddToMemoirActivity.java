package com.example.mymoviemenoir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddToMemoirActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    
    private NetworkConnection networkConnection;

    private EditText postcodeET;
    private EditText commentET;
    private TextView watchDateTV;
    private TextView watchTimeTv;
    private RatingBar ratingBar;
    private Button cancelBtn;
    private Button submitBtn;

    private String movieName;
    private String releaseDate;
    private String watchDate;
    private String watchTime;
    private String cinemaPostcode;
    private String comment;
    private String rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_memoir);
        Intent intent = this.getIntent();
        movieName = intent.getStringExtra("MOVIE");
        //Format releaseDate to match the POST format
        releaseDate = intent.getStringExtra("RELEASE");
        String[] releaseDateComponent = releaseDate.split(" ");
        String month = "";
        try {
            Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(releaseDateComponent[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            month =  String.valueOf(calendar.get(Calendar.MONTH));
            if(calendar.get(Calendar.MONTH) < 10){
                month = "0" + String.valueOf(calendar.get(Calendar.MONTH));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        releaseDate = releaseDateComponent[2] + "-" + month + "-" + releaseDateComponent[0] + "T00:00:00+10:00";


        networkConnection = new NetworkConnection();

        //Setup the date picker widget
        watchDateTV = findViewById(R.id.watchDateTV);
        watchDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddToMemoirActivity.this,  onDateSetListener, year, month, day);
                datePickerDialog.show();

            }
        });
        //Initiate on date set listener
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                watchDate = dayOfMonth + "/" + month + "/" + year;
                watchDateTV.setText(watchDate);
                String monthString = "";
                if(month < 10){
                    monthString = "0" + String.valueOf(month);
                }
                String dayString = String.valueOf(dayOfMonth);
                if(dayOfMonth < 10){
                    dayString = "0" + dayOfMonth;
                }
                watchDate = year + "-" + monthString + "-" + dayString + "T00:00:00+10:00";
            }
        };

        //Setup the time picker widget
        watchTimeTv = findViewById(R.id.watchTimeTV);
        watchTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddToMemoirActivity.this, onTimeSetListener ,hour, minutes, true);
                timePickerDialog.show();

            }
        });
        //Initiate on time set listener
        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hourString = String.valueOf(hourOfDay);
                if(hourOfDay < 10){
                    hourString = "0" + hourOfDay;
                }
                String minuteString = String.valueOf(minute);
                if(minute < 10){
                    minuteString = "0" + minute;
                }

                watchTime = hourString + " : " + minuteString;
                watchTimeTv.setText(watchTime);
                watchTime = watchDate.substring(0,10) + "T" + hourString + ":" + minuteString + ":00+10:00";
            }
        };

        //When Submit button is clicked
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get postcode and comment
                postcodeET = findViewById(R.id.postcodeET);
                commentET = findViewById(R.id.commentET);

                cinemaPostcode = postcodeET.getText().toString();
                comment = commentET.getText().toString();

                //Get rating
                ratingBar = findViewById(R.id.ratingBar);
                rating = String.valueOf(ratingBar.getRating());
                SharedPreferences sharedPreferences = getSharedPreferences("USERID", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("USERID", null);
                String[] details = {movieName, releaseDate, rating, watchDate, watchTime, comment, cinemaPostcode, userId};
                AddMemoirTask addMemoirTask = new AddMemoirTask();
                addMemoirTask.execute(details);

            }
        });
        
        //When Cancel button is clicked



    }

    private class AddMemoirTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.addMemoir(strings);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(AddToMemoirActivity.this, "Memoir of added", Toast.LENGTH_SHORT).show();
        }
    }
}
