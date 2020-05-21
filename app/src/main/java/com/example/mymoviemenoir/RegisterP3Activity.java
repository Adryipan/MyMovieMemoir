package com.example.mymoviemenoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterP3Activity extends AppCompatActivity {

    private String firstName;
    private String surname;
    private String gender;
    private String dob;
    private String streetAddress;
    private String postcode;
    private String state;
    private String email;
    private String password;

    private EditText emailET;
    private EditText passwordET;
    private EditText confirmPWET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp3);

        Intent intent = this.getIntent();
        firstName = intent.getStringExtra("FIRSTNAME");
        surname = intent.getStringExtra("SURNAME");
        gender = intent.getStringExtra("GENDER");
        dob = intent.getStringExtra("DOB");
        streetAddress = intent.getStringExtra("STREET");
        postcode = intent.getStringExtra("POSTCODE");
        state = intent.getStringExtra("STATE");

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailET = findViewById(R.id.emailET);
                passwordET = findViewById(R.id.passwordET);
                confirmPWET = findViewById(R.id.confirmpwET);

                //Check if the two password are the same

                //Query to add this user to the server
            }
        });
    }
}
