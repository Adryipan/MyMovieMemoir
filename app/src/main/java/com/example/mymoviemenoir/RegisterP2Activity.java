package com.example.mymoviemenoir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

public class RegisterP2Activity extends AppCompatActivity {

    private TextView dobTextView;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private EditText fNameET;
    private EditText surnameET;
    private RadioGroup genderRadioGroup;
    private RadioButton genderRB;

    private String fName;
    private String surname;
    private String gender;
    private String dob;
    private String email;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp2);
        Intent intent = this.getIntent();
        email = intent.getStringExtra("USERNAME");
        password = intent.getStringExtra("PASSWORD");

        //Setup the date picker widget
        dobTextView = findViewById(R.id.dobTV);
        dobTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterP2Activity.this, onDateSetListener, year, month, day);

                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dob = dayOfMonth + "/" + month + "/" + year;
                dobTextView.setText(dob);
                String monthString = "";
                if(month < 10){
                    monthString = "0" + String.valueOf(month);
                }
                String dayString = String.valueOf(dayOfMonth);
                if(dayOfMonth < 10){
                    dayString = "0" + dayOfMonth;
                }
                dob = year + "-" + monthString + "-" + dayString + "T00:00:00+10:00";

            }
        };

        genderRadioGroup = findViewById(R.id.genderRadioGroup);


        //Go to next page
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterP2Activity.this, RegisterP3Activity.class);
                fNameET = findViewById(R.id.firstNameET);
                surnameET = findViewById(R.id.surnameET);

                fName = fNameET.getText().toString();
                surname = surnameET.getText().toString();
                checkButton(v);

                intent.putExtra("FIRSTNAME", fName);
                intent.putExtra("SURNAME", surname);
                intent.putExtra("DOB", dob);
                intent.putExtra("GENDER", gender);
                intent.putExtra("USERNAME", email);
                intent.putExtra("PASSWORD", password);
                startActivity(intent);

            }
        });
    }

    //Get the selected gender
    protected void checkButton(View v){
        int radioId = genderRadioGroup.getCheckedRadioButtonId();
        genderRB = findViewById(radioId);
        gender = genderRB.getText().toString().substring(0,1);

    }
}
