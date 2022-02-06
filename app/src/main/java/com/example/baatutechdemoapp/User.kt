package com.example.baatutechdemoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User {
    //Primary key
    @PrimaryKey(autoGenerate = true)
    var key = 0

    @ColumnInfo(name = "name")
    var name: String? = null
}