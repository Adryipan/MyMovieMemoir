package com.example.mymoviemenoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

public class RegisterP1Activity extends AppCompatActivity {

    private String email;
    private String password;

    private EditText emailET;
    private EditText passwordET;
    private EditText confirmPWET;

    private NetworkConnection networkConnection = null;
    private int errorColour = ContextCompat.getColor(getApplicationContext(), R.color.design_default_color_error);
    private ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColour);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp1);
        networkConnection = new NetworkConnection();


        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailET = findViewById(R.id.emailET);
                passwordET = findViewById(R.id.passwordET);
                confirmPWET = findViewById(R.id.confirmpwET);
                //Check if the user exist
                GetByUsernameTask checkEmail = new GetByUsernameTask();
                checkEmail.execute(emailET.getText().toString());
            }
        });
    }

    private class GetByUsernameTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String username = strings[0];
            return networkConnection.getByUsername(username);
        }

        @Override
        protected void onPostExecute(String response){
            //If this username does not exist
            if(response.isEmpty()){
//                Check if the two password are the same
                if(passwordET.getText().toString().equals(confirmPWET.getText().toString())){
                   Intent intent = new Intent(RegisterP1Activity.this, RegisterP2Activity.class);
                   intent.putExtra("USERNAME", email);
                   intent.putExtra("PASSWORD", password);
                   startActivity(intent);

                }else{
                    //Password check fails
                    String errorString = "Password does not match. Please try again.";
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
                    spannableStringBuilder.setSpan(foregroundColorSpan, 0 , errorString.length(), 0);
                    confirmPWET.setError(spannableStringBuilder);
                }

            }else{
                //The Email already exist
                String errorString = "This email already exits. Please try another email or click forget password.";
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
                spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
                emailET.setError(spannableStringBuilder);
            }




        }
    }
}
