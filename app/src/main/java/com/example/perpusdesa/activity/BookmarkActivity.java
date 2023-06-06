package com.example.perpusdesa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.perpusdesa.R;
import com.example.perpusdesa.adapter.BookmarkListAdapter;
import com.example.perpusdesa.dao.BookmarkDao;
import com.example.perpusdesa.dao.database.BookmarkDatabase;
import com.example.perpusdesa.model.Bookmark;
import com.example.perpusdesa.model.PepusModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class BookmarkActivity extends AppCompatActivity implements BookmarkListAdapter.ItemClickListener {

    private BookmarkDao bookmarkDao;
    private List<Bookmark> bookmarkedBooks;
    private BookmarkListAdapter adapter;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        bottomNavigationView = findViewById(R.id.book);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.bookmark) {
                    return true;
                } else if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

        // Inisialisasi bookmarkDao
        bookmarkDao = BookmarkDatabase.getInstance(this).bookmarkDao();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewBookmark);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookmarkListAdapter(this, bookmarkedBooks, this);
        recyclerView.setAdapter(adapter);


        // Ambil daftar buku yang ditandai sebagai bookmark dari database
        fetchBookmarks();

    }
    private void fetchBookmarks() {
        // Jalankan operasi database pada thread background
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                bookmarkedBooks = bookmarkDao.getAllBookmarks();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setBookmarkedList(bookmarkedBooks);
                    }
                });
            }
        });
    }


    @Override
    public void onBookmarkClick(Bookmark bookmarkedBook) {

    }
}