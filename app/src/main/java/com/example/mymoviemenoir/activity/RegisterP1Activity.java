package com.example.mymoviemenoir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

public class RegisterP1Activity extends AppCompatActivity {

    private String email;
    private String password;

    private EditText emailET;
    private EditText passwordET;
    private EditText confirmPWET;

    private NetworkConnection networkConnection = null;

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
            if(response.length() == 2){
//                Check if the two password are the same
                if(passwordET.getText().toString().equals(confirmPWET.getText().toString())){
                   Intent intent = new Intent(RegisterP1Activity.this, RegisterP2Activity.class);
                   email = emailET.getText().toString();
                   password = passwordET.getText().toString();
                   intent.putExtra("USERNAME", email);
                   intent.putExtra("PASSWORD", password);
                   startActivity(intent);

                }else{
                    //Password check fails
                    String errorString = "Password does not match. Please try again.";
                    confirmPWET.setError(errorString);
                }

            }else{
                //The Email already exist
                String errorString = "This email already exits. Please try another email or click forget password.";
                emailET.setError(errorString);
            }




        }
    }
}
