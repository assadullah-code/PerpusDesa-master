package com.example.perpusdesa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perpusdesa.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewName, textViewEmail;
    Button buttonLogout;
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewName = findViewById(R.id.name);
        textViewEmail = findViewById(R.id.email);
        buttonLogout = findViewById(R.id.logout);
        bottomNavigationView = findViewById(R.id.profile);
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        if(sharedPreferences.getString("logged","false").equals("false")){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        textViewName.setText(sharedPreferences.getString("name",""));
        textViewEmail.setText(sharedPreferences.getString("email",""));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.profile) {
                    return true;
                } else if (item.getItemId() == R.id.bookmark) {
                    startActivity(new Intent(getApplicationContext(), BookmarkActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
                return false;
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://api.pustakadigital-dewi.com/users/logout";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("logged", "");
                                    editor.putString("name", "");
                                    editor.putString("email", "");
                                    editor.putString("apiKey", "");
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                }else Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", sharedPreferences.getString("email",""));
                        paramV.put("apiKey", sharedPreferences.getString("apiKey",""));
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}