package com.example.baatutechdemoapp

import android.app.Application

interface DataSource {

    fun getList(
        application: Application,
        responseHandler: ResponseHandler<Result<ArrayList<User>>>
    )

}