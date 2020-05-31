package com.example.mymoviemenoir.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;
import com.example.mymoviemenoir.securitywidget.HashingFunction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterP3Activity extends AppCompatActivity {

    private EditText streetAddressET;
    private EditText postCodeET;
    private Spinner stateSpinner;
    private ArrayAdapter<String> spinnerAdapter;

    private String firstName;
    private String surname;
    private String gender;
    private String dob;
    private String streetAddress;
    private String postcode;
    private String state;
    private String email;
    private String password;
    private NetworkConnection networkConnection = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp3);
        networkConnection = new NetworkConnection();

        //Get personal info for checking later
        Intent intent = this.getIntent();
        firstName = intent.getStringExtra("FIRSTNAME");
        surname = intent.getStringExtra("SURNAME");
        gender = intent.getStringExtra("GENDER");
        dob = intent.getStringExtra("DOB");
        email = intent.getStringExtra("USERNAME");
        password = intent.getStringExtra("PASSWORD");


        //Populate the spinner for state
        stateSpinner = findViewById(R.id.stateSpinner);

        List<String> states = new ArrayList<>();
        states.add("Pick a state");
        states.add("NSW");
        states.add("VIC");
        states.add("QLD");
        states.add("WA");
        states.add("TAS");
        states.add("SA");
        states.add("ACT");
        states.add("NT");

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, states);
        stateSpinner.setAdapter(spinnerAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    String selectedState = parent.getItemAtPosition(position).toString();
                    if (selectedState != null) {
                        state = selectedState;
                    }
                }else{
                    Toast.makeText(RegisterP3Activity.this, "Please pick a state", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //onclick next
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the info from the screen first
                streetAddressET = findViewById(R.id.streetAddressET);
                postCodeET = findViewById(R.id.suburbET);

                streetAddress = streetAddressET.getText().toString();
                if(!streetAddress.trim().isEmpty()) {
                    postcode = postCodeET.getText().toString();
                    if (!postcode.trim().isEmpty() && Pattern.matches("[0-9]{4}", postcode)) {

                        //Get current day and time
                        Date date = Calendar.getInstance().getTime();

                        //Format today
                        SimpleDateFormat formatToday = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                        String today = formatToday.format(date);

                        //ADD the person to the database
                        //Need to hash the password first
                        password = HashingFunction.getHash(password);
                        String[] details = {firstName, surname, gender, dob, streetAddress, postcode, state, email, password, today};
                        AddUserTask addUserTask = new AddUserTask();
                        addUserTask.execute(details);

                        Intent intent = new Intent(RegisterP3Activity.this, LoginActivity.class);
                        startActivity(intent);
                    }else if(postcode.trim().isEmpty()){
                        postCodeET.setError("Postcode must not be empty");
                    }else{
                        postCodeET.setError("Please enter a valid 4 digit postcode");
                    }
                }else{
                    streetAddressET.setError("Address must not be empty");
                }
            }
        });
    }

    private class AddUserTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.addUser(strings);
        }
    }
}
