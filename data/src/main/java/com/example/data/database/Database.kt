package com.abank.idcard.data.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abank.idcard.data.repository.database.entity.*
import com.example.data.BuildConfig

@Database(entities = [ExampleEntity::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    companion object {

        var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            DataBase::class.java, BuildConfig.DB_NAME)
                            .build()
                }
            }
            return INSTANCE
        }

    }

}