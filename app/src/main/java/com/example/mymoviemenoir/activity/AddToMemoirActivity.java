package com.example.mymoviemenoir.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.RoomEntity.MOVIE;
import com.example.mymoviemenoir.entity.Cinema;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;
import com.example.mymoviemenoir.viewmodel.WatchlistViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddToMemoirActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    
    private NetworkConnection networkConnection;
    private WatchlistViewModel watchlistViewModel;

    private EditText commentET;
    private TextView watchDateTV;
    private TextView watchTimeTv;
    private RatingBar ratingBar;
    private Button cancelBtn;
    private Button submitBtn;
    private Spinner cinemaSpinner;
    private ArrayAdapter<Cinema> arrayAdapter;
    private TextView addCinema;

    private String movieName;
    private String releaseDate;
    private String watchDate;
    private String watchTime;
    private Cinema cinema;
    private String cinemaSuburb;
    private String comment;
    private String rating;
    private String imdbID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_memoir);

        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        watchlistViewModel.initialize(getApplication());
        watchlistViewModel.getAllMovies().observe(this, new Observer<List<MOVIE>>() {
            @Override
            public void onChanged(List<MOVIE> moviesResult) {
            }
        });

        Intent intent = this.getIntent();
        movieName = intent.getStringExtra("MOVIE");
        //Format releaseDate to match the POST format
        releaseDate = intent.getStringExtra("RELEASE");
        imdbID = intent.getStringExtra("IMDBID");
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
        //Setup the spinner at post execute
        GetAllCinemaTask getAllCinemaTask = new GetAllCinemaTask();
        getAllCinemaTask.execute();

        //If the user wants to add more cinema
        addCinema = findViewById(R.id.addCinemaTV);
        addCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddToMemoirActivity.this, AddCinemaActivity.class);
                startActivityForResult(intent1, 1);
            }
        });

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
                commentET = findViewById(R.id.commentET);
                comment = commentET.getText().toString();

                //Get rating
                ratingBar = findViewById(R.id.ratingBar);
                rating = String.valueOf(ratingBar.getRating());
                SharedPreferences sharedPreferences = getSharedPreferences("USERID", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("USERID", null);
                cinemaSuburb = cinema.getSuburb();
                if(!cinemaSuburb.equals("pick a place or add one")) {
                    String[] details = {movieName, releaseDate, rating, watchDate, watchTime, comment, cinemaSuburb, userId, imdbID};
                    AddMemoirTask addMemoirTask = new AddMemoirTask();
                    addMemoirTask.execute(details);
                }else{
                    Toast.makeText(AddToMemoirActivity.this, "Please pick a cinema", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        //When Cancel button is clicked
        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Update the spinner
                GetAllCinemaTask getAllCinemaTask = new GetAllCinemaTask();
                getAllCinemaTask.execute();
            }
        }
    }

    private class AddMemoirTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            List<MOVIE> result = watchlistViewModel.getAllNoLive();
            MOVIE movie = null;
            for(MOVIE thisMovie : result){
                if(thisMovie.getImdbID().equals(strings[8])){
                    movie = thisMovie;
                }
            }
            watchlistViewModel.delete(movie);
            return networkConnection.addMemoir(strings);
        }

        @Override
        protected void onPostExecute(String s) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private class GetAllCinemaTask extends AsyncTask<Void, Void, ArrayList<Cinema>>{

        @Override
        protected ArrayList<Cinema> doInBackground(Void... voids) {
            String resultString = networkConnection.getAllCinema();
            ArrayList<Cinema> result = new ArrayList<>();
            try{
                JSONArray jsonArray = new JSONArray(resultString);
                int numberOfItems = jsonArray.length();
                if(numberOfItems > 0){
                    for(int i = 0; i < numberOfItems; i++){
                        JSONObject thisCinema = jsonArray.getJSONObject(i);
                        result.add(new Cinema(thisCinema.getInt("cinemaId"), thisCinema.getString("cinemaName"), thisCinema.getString("suburb")));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Cinema> cinemas) {
            cinemas.add(0, new Cinema(0, "Nothing selected", "pick a place or add one"));
            cinemaSpinner = findViewById(R.id.cinemaSpinner);
            arrayAdapter = new ArrayAdapter<Cinema>(AddToMemoirActivity.this, android.R.layout.simple_spinner_dropdown_item, cinemas);
            cinemaSpinner.setAdapter(arrayAdapter);
            cinemaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0) {
                        Cinema selectedCinema = (Cinema) parent.getItemAtPosition(position);
                        cinema = selectedCinema;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }


}
