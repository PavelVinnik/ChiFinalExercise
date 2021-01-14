package com.example.chifinalexercise.bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.chifinalexercise.model.Channel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Channel.class}, version = 1, exportSchema = false)
public abstract class ChannelRoomDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "channel_database";

    public abstract ChannelDao channelDao();

    private static volatile ChannelRoomDatabase sInstance;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService sDatabaseWrightExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ChannelRoomDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (ChannelRoomDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            ChannelRoomDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sInstance;
    }
}
