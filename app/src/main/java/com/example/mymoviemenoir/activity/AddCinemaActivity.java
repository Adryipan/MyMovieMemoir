package com.example.mymoviemenoir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;


public class AddCinemaActivity extends AppCompatActivity {

    private EditText cinemaNameET;
    private EditText cinemaSuburbET;
    private Button submitBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cinema);

        submitBtn = findViewById(R.id.addCinemaSubmitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cinemaNameET = findViewById(R.id.addCinemaNameET);
                String name = cinemaNameET.getText().toString();

                cinemaSuburbET = findViewById(R.id.addCinemaSuburbET);
                String suburb = cinemaSuburbET.getText().toString();

                if(!name.trim().isEmpty() && !suburb.trim().isEmpty()) {
                    hideKeyboard(v);
                    AddCinemaTask addCinemaTask = new AddCinemaTask();
                    addCinemaTask.execute(name, suburb);
                }else if(name.trim().isEmpty()){
                    cinemaSuburbET.setError("Name must not be empty");
                }else if(suburb.trim().isEmpty()){
                    cinemaSuburbET.setError("Suburb must not be empty");
                }
            }
        });

        cancelBtn = findViewById(R.id.addCinemaCancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                Intent intent = new Intent(AddCinemaActivity.this, AddToMemoirActivity.class);
                setResult(RESULT_CANCELED);
                finish();
            }
        });


    }

    private class AddCinemaTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.addCinema(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(AddCinemaActivity.this, "Cinema added.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddCinemaActivity.this, AddToMemoirActivity.class);
            setResult(RESULT_OK);
            finish();
        }
    }

    private void hideKeyboard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }
}
