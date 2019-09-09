package com.abank.IDCard.data.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abank.IDCard.data.repository.database.entity.*

const val DB_NAME = "abank-database"

@Database(entities = [
ExampleEntity::class],
        version = 1)

abstract class DataBase : RoomDatabase() {

    companion object {

        var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            DataBase::class.java, DB_NAME)
                            .build()
                }
            }
            return INSTANCE
        }

    }

}