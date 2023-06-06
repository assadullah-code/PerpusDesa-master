package com.example.perpusdesa.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perpusdesa.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView textViewRegisterNow ;
    TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSubmit;
    String name, email, password, apiKey;
    TextView textViewError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewRegisterNow = findViewById(R.id.registerNow);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonSubmit = findViewById(R.id.submit);
        textViewError = findViewById(R.id.error);
        progressBar = findViewById(R.id.loading);
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewError.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                // Validasi jika email atau password kosong
                if (email.isEmpty() || password.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    textViewError.setText("Email and password are required");
                    textViewError.setVisibility(View.VISIBLE);
                } else {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="https://api.pustakadigital-dewi.com/users/verify";

                if(sharedPreferences.getString("logged","false").equals("false")){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if(status.equals("success")){
                                        name = jsonObject.getString("name");
                                        email = jsonObject.getString("email");
                                        apiKey = jsonObject.getString("apiKey");
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("logged", "true");
                                        editor.putString("name", name);
                                        editor.putString("email", email);
                                        editor.putString("apiKey", apiKey);
                                        editor.apply();

                                        Toast.makeText(Login.this, "Welcome " + email , Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        textViewError.setText(error.getLocalizedMessage());
                        textViewError.setVisibility(View.VISIBLE);
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
                }
            }
        });

        textViewRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }
}