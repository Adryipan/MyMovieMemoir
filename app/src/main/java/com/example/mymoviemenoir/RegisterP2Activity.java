package com.example.mymoviemenoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RegisterP2Activity extends AppCompatActivity {

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp2);

        //Get personal info for checking later
        Intent intent = this.getIntent();
        firstName = intent.getStringExtra("FIRSTNAME");
        surname = intent.getStringExtra("SURNAME");
        gender = intent.getStringExtra("GENDER");
        dob = intent.getStringExtra("DOB");


        //Populate the spinner for state
        stateSpinner = findViewById(R.id.stateSpinner);

        List<String> states = new ArrayList<>();
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
                String selectedState = parent.getItemAtPosition(position).toString();
                if(selectedState != null){
                    state = selectedState;
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
                Intent intent1 = new Intent(RegisterP2Activity.this, RegisterP3Activity.class);
                //Get the info from the screen first
                streetAddressET = findViewById(R.id.streetAddressET);
                postCodeET = findViewById(R.id.postcodeET);

                streetAddress = streetAddressET.getText().toString();
                postcode = postCodeET.getText().toString();

                //Query to the backend to see if this person exist

                //if true then proceed
                if(true){
                    intent1.putExtra("FIRSTNAME", firstName);
                    intent1.putExtra("SURNAME", surname);
                    intent1.putExtra("DOB", dob);
                    intent1.putExtra("GENDER", gender);
                    intent1.putExtra("STREET", streetAddress);
                    intent1.putExtra("POSTCODE", postcode);
                    intent1.putExtra("STATE", state);

                    startActivity(intent1);
                }else{
                    //Display a message and make a link back to login page
                }
            }
        });






    }
}
