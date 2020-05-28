package com.example.mymoviemenoir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
                EditText usernameET = findViewById(R.id.usernameET);
                EditText passwordET = findViewById(R.id.passwordET);

                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                //for debug
                username = "john.smith@monash.edu.au";
                password = "john";

                if(!username.isEmpty() && !password.isEmpty()){
                    GetByAuthenticationTask getByAuthenticationTask = new GetByAuthenticationTask();
                    getByAuthenticationTask.execute(username, password);
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
            if(!userId.isEmpty()) {
                Intent intent = new Intent(LoginActivity.this, NavHomeActivity.class);
                intent.putExtra("USERID", userId);
                startActivity(intent);
            }else{
                CharSequence message = "Username and Password not matched.";
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0,0);
                toast.show();
            }
        }
    }
}
