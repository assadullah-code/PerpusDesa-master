package com.example.perpusdesa.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.perpusdesa.dao.repository.BookmarkRepository;
import com.example.perpusdesa.model.Bookmark;

import java.util.List;

public class BookmarkViewModel extends ViewModel {

    private BookmarkRepository bookmarkRepository;
    private MutableLiveData<List<Bookmark>> bookmarkList;

    public BookmarkViewModel(Application application) {
        bookmarkRepository = new BookmarkRepository(application);
        bookmarkList = (MutableLiveData<List<Bookmark>>) bookmarkRepository.getBookmarkListLiveData();
    }

    public LiveData<List<Bookmark>> getBookmarkList() {
        return bookmarkList;
    }

    public void addBookmark(Bookmark bookmark) {
        bookmarkRepository.addBookmark(bookmark);
    }

    public void removeBookmark(Bookmark bookmark) {
        bookmarkRepository.removeBookmark(bookmark);
    }

    // Metode lain yang diperlukan untuk operasi lain pada data bookmark
}
