package com.example.perpusdesa.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.perpusdesa.R;
import com.example.perpusdesa.WebActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailHomeActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private ImageView imageView;
    private String imageUrl;
    private String url;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_home);

        imageView = findViewById(R.id.imageView);
        Button button = (Button) findViewById(R.id.btn_pdf);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        // pdfView = findViewById(R.id.pdfView);



                // Ambil URL gambar dan URL PDF dari intent
        imageUrl = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("pdf");

        // Tampilkan gambar menggunakan Glide atau library lainnya
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);



        // Set onClickListener untuk imageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Periksa izin internet sebelum membuka PDF viewer
                if (checkInternetPermission()) {
                    // Buka PDF viewer
                    openPdfViewer();
                }
            }
        });
    }

    private boolean checkInternetPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private void openPdfViewer() {
        // Set visibility gambar menjadi GONE dan PDF viewer menjadi VISIBLE
        imageView.setVisibility(View.GONE);
        // pdfView.setVisibility(View.VISIBLE);

        // Pengecekan URL
        if (url != null) {
            // Download dan tampilkan PDF dari URL menggunakan Volley
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        // downloadPdf();
        } else {
            Toast.makeText(this, "PDF URL is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadPdf() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Tangani respons sukses
                savePdfToLocal(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Tangani respons error
                String errorMessage = "Failed to retrieve PDF";
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMessage = new String(error.networkResponse.data);
                }
                Toast.makeText(DetailHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void savePdfToLocal(String pdfContent) {
        try {
            // Buat file PDF lokal
            File file = new File(getFilesDir(), "document.pdf");

            // Tulis konten PDF ke file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(pdfContent.getBytes());
            fos.close();

            // Tampilkan PDF dari file menggunakan PDFViewer
            pdfView.fromFile(file)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(DetailHomeActivity.this)
                    .onLoad(DetailHomeActivity.this)
                    .load();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save PDF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        Log.d("PDF Viewer", "Page changed: " + (page + 1) + " of " + pageCount);
    }

    @Override
    public void loadComplete(int nbPages) {
        Log.d("PDF Viewer", "PDF load complete. Total pages: " + nbPages);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, buka PDF viewer
                openPdfViewer();
            } else {
                Toast.makeText(this, "Internet permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}