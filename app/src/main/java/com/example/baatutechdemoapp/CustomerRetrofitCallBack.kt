package com.example.baatutechdemoapp

import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

open class CustomerRetrofitCallBack<T> : Callback<ArrayList<User>> {

    private val retrofit: Retrofit? = null


    private fun bodyToString(request: RequestBody?): String? {
        return try {
            val buffer = Buffer()
            if (request != null) request.writeTo(buffer) else return ""
            buffer.readUtf8()
        } catch (e: IOException) {
            ""
        }
    }

    protected fun handleError(response: Response<ArrayList<User>>, request: Request?) {
        try {
            if (response.errorBody() != null) {
                val errorBody = response.errorBody()!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
        if (!response.isSuccessful) {
            handleError(response, call.request())
        }
    }

    override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
    }
}