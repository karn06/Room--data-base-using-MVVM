package com.example.baatutechdemoapp

import android.app.Application
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

object DefaultDataSource : DataSource {

    override fun getList(
        application: Application,
        responseHandler: ResponseHandler<Result<ArrayList<User>>>
    ) {
        responseHandler.response(Result.Loading)
        val service = MainServiceCreater.createService(application)
        val call = service.getDataList()

        call.enqueue(object : CustomerRetrofitCallBack<ArrayList<User>>() {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                super.onFailure(call, t)
                t.printStackTrace()
                responseHandler.response(Result.Error(Exception(exceptionErrors(t))))
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                super.onResponse(call, response)
                if (response.isSuccessful) {
                    responseHandler.response(Result.Success(response.body()!!))
                } else {
                    responseHandler.response(Result.Error(Exception("Data Not Found")))
                }
            }
        })

    }

    fun exceptionErrors(throwable: Throwable): String {
        if (throwable is IOException) {
            // A network or conversion error happened
            return "Network Error"
        }
        if (throwable is IllegalStateException) {
            // data parsing error
            return "Data Parsing Error"
        }
        // any other network call exception
        return "Please try again Later"
    }
}