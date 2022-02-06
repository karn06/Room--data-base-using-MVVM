package com.example.baatutechdemoapp

import com.example.baatutechdemoapp.DefaultDataSource
import com.example.baatutechdemoapp.Repository

object InjectorUtil {
    fun provideReceptionRepository(): Repository {
        val dataSource = DefaultDataSource
        return Repository(dataSource)
    }
}
