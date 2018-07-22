package com.example.aliyuadamukasallah.hubtooth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button login, forgetPass;
    EditText userName, password;
    TextView display;

    public String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        forgetPass = (Button) findViewById(R.id.forgetPassword);

        display = (TextView) findViewById(R.id.display_error);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userName.getText().toString().toLowerCase();
                pass = password.getText().toString().toLowerCase();

                if ((user.equals("kasalian") && pass.equals("lara")) || (user.equals("masterbluetooth") && pass.equals("") )){
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);

                    userName.setText("");
                    password.setText("");
//                    user = pass = "";

                }else {
                    display.setText("Invalid Login details !!!");
                    userName.setText("");
                    password.setText("");
                    user = pass = "";
                }
            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "Enter the master password in ONLY the User Name Field or Contact the Developer";
                Toast.makeText(getApplicationContext(), s ,Toast.LENGTH_LONG).show();

                userName.setText("");
                password.setText("");
                user = pass = "";
            }
        });
    }
}
