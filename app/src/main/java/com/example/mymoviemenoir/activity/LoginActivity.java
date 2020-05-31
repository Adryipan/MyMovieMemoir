package com.example.mymoviemenoir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

public class LoginActivity extends AppCompatActivity {

    private NetworkConnection networkConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        networkConnection = new NetworkConnection();

        Button signinBtn = findViewById(R.id.signinBtn);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                EditText usernameET = findViewById(R.id.usernameET);
                EditText passwordET = findViewById(R.id.passwordET);

                String username = usernameET.getText().toString();
                if(!username.trim().isEmpty()) {
                    String password = passwordET.getText().toString();
                    if(!password.trim().isEmpty()) {

//                //for debug
                username = "john.smith@monash.edu.au";
                password = "john";

                        if (!username.isEmpty() && !password.isEmpty()) {
                            GetByAuthenticationTask getByAuthenticationTask = new GetByAuthenticationTask();
                            getByAuthenticationTask.execute(username, password);
                        }
                    }else{
                        passwordET.setError("Password must not be empty");
                    }
                }else{
                    usernameET.setError("Email must not be empty");
                }
            }
        });

        TextView register = findViewById(R.id.registerTV);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterP1Activity.class);
                startActivity(intent);

            }
        });
    }

    private class GetByAuthenticationTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            return networkConnection.getByAuthentication(username, password);
        }

        @Override
        protected void onPostExecute(String userId){
            if(userId != null) {
                if (!userId.isEmpty()) {
                    Intent intent = new Intent(LoginActivity.this, NavHomeActivity.class);
                    intent.putExtra("USERID", userId);
                    startActivity(intent);
                } else {
                    CharSequence message = "Username and Password not matched.";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                    toast.show();
                }
            }else{
                Toast toast = Toast.makeText(LoginActivity.this, "Fail to connect to server", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
