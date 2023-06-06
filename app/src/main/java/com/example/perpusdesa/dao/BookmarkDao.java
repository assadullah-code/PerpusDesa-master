package com.example.perpusdesa.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.perpusdesa.model.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert
    void insertBookmark(Bookmark bookmark);

    @Delete
    void deleteBookmark(Bookmark bookmark);

    @Query("SELECT * FROM bookmarks")
    List<Bookmark> getAllBookmarks();
}
