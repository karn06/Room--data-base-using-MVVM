package com.example.baatutechdemoapp

import android.app.Application

class Repository(private val dataSource: DataSource) {

    fun getList(
        application: Application,
        responseHandler: ResponseHandler<Result<ArrayList<User>>>
    ) {
        dataSource.getList( application, responseHandler)
    }

}