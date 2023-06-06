package com.example.perpusdesa.dao.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.perpusdesa.dao.BookmarkDao;
import com.example.perpusdesa.dao.database.BookmarkDatabase;
import com.example.perpusdesa.model.Bookmark;

import java.util.List;

public class BookmarkRepository {

    private BookmarkDao bookmarkDao;
    private LiveData<List<Bookmark>> bookmarkListLiveData;

    public BookmarkRepository(Application application) {
        BookmarkDatabase database = BookmarkDatabase.getInstance(application);
        bookmarkDao = database.bookmarkDao();
        bookmarkListLiveData = (LiveData<List<Bookmark>>) bookmarkDao.getAllBookmarks();
    }

    public LiveData<List<Bookmark>> getBookmarkListLiveData() {
        return bookmarkListLiveData;
    }

    public void addBookmark(Bookmark bookmark) {
        // Lakukan operasi untuk menambahkan bookmark ke database melalui DAO
        bookmarkDao.insertBookmark(bookmark);
    }

    public void removeBookmark(Bookmark bookmark) {
        // Lakukan operasi untuk menghapus bookmark dari database melalui DAO
        bookmarkDao.deleteBookmark(bookmark);
    }

    // Metode lain yang diperlukan untuk operasi CRUD lainnya
}
