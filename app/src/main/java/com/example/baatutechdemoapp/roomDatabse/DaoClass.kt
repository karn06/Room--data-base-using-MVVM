package com.example.baatutechdemoapp.roomDatabse

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baatutechdemoapp.User


@Dao
interface Daoclass {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(model: ArrayList<User>)

    //Select All Data
    @Query("Select * from  user")
    fun getAllData(): List<User?>?
}