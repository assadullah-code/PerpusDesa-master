package com.example.perpusdesa.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpusdesa.R;
import com.example.perpusdesa.activity.BookmarkActivity;
import com.example.perpusdesa.activity.ProfileActivity;
import com.example.perpusdesa.adapter.HomeListAdapter;
import com.example.perpusdesa.databinding.ActivityMainBinding;
import com.example.perpusdesa.model.PepusModel;
import com.example.perpusdesa.viewmodel.PerpusListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeListAdapter.ItemClickListener {

    private List<PepusModel> pepusModelList;
    private HomeListAdapter adapter;
    private PerpusListViewModel viewModel;
    private SearchView searchView;
    private ActivityMainBinding binding;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyAppName" , MODE_PRIVATE);
        editor = sharedPreferences.edit();

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        bottomNavigationView = findViewById(R.id.nav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.bookmark) {
                    startActivity(new Intent(getApplicationContext(), BookmarkActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new HomeListAdapter(this, pepusModelList, this);
        recyclerView.setAdapter(adapter);


        viewModel = ViewModelProviders.of(this).get(PerpusListViewModel.class);
        viewModel.getPerpusListObserver().observe(this, new Observer<List<PepusModel>>() {
            @Override
            public void onChanged(List<PepusModel> pepusModels) {
                if(pepusModels != null) {
                    pepusModelList = pepusModels;
                    adapter.setPerpusList(pepusModels);
                }
            }
        });
        viewModel.makeApiCall();
    }

    private void filterList(String text) {
        List<PepusModel> filteredList = new ArrayList<>();
        for (PepusModel pepusModel : pepusModelList){
            if (pepusModel.getId().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(pepusModel);
            }
            if (pepusModel.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(pepusModel);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }
    }

    @Override
    public void onPerpusClick(PepusModel book) {
        Toast.makeText(this, "Clicked Book Name is : " +book.getTitle(), Toast.LENGTH_SHORT).show();
    }
}