package com.example.baatutechdemoapp.roomDatabse

import android.content.Context
import androidx.room.Room

import androidx.room.RoomDatabase

import androidx.room.Database
import com.example.baatutechdemoapp.User


@Database(entities = [User::class], version = 1)
abstract class DatabaseClass : RoomDatabase() {
    abstract val dao: Daoclass?

    companion object {
        private var instance: DatabaseClass? = null
        fun getDatabase(context: Context?): DatabaseClass? {
            if (instance == null) {
                synchronized(DatabaseClass::class.java) {
                    instance = Room.databaseBuilder(
                        context!!,
                        DatabaseClass::class.java, "DATABASE"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}
