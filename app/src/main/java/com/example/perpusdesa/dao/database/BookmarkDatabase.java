package com.example.perpusdesa.dao.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.perpusdesa.dao.BookmarkDao;
import com.example.perpusdesa.model.Bookmark;

@Database(entities = {Bookmark.class}, version = 1)
public abstract class BookmarkDatabase extends RoomDatabase {
    private static BookmarkDatabase instance;

    public abstract BookmarkDao bookmarkDao();

    public static synchronized BookmarkDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BookmarkDatabase.class, "bookmark_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
