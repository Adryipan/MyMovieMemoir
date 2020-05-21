package com.example.mymoviemenoir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterP1Activity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp1);
        dobTextView = findViewById(R.id.dobTV);

        dobTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterP1Activity.this, android.R.style.Theme_DeviceDefault, onDateSetListener, year, month, day);

                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dob = dayOfMonth + "/" + month + "/" + year;
                dobTextView.setText(dob);

            }
        };

        genderRadioGroup = findViewById(R.id.genderRadioGroup);



        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterP1Activity.this, RegisterP2Activity.class);
                fNameET = findViewById(R.id.firstNameET);
                surnameET = findViewById(R.id.surnameET);

                fName = fNameET.getText().toString();
                surname = surnameET.getText().toString();
                checkButton(v);

                intent.putExtra("FIRSTNAME", fName);
                intent.putExtra("SURNAME", surname);
                intent.putExtra("DOB", dob);
                intent.putExtra("GENDER", gender);
                startActivity(intent);


            }
        });
    }

    protected void checkButton(View v){
        int radioId = genderRadioGroup.getCheckedRadioButtonId();
        genderRB = findViewById(radioId);
        gender = genderRB.getText().toString().substring(0,1);

    }
}
