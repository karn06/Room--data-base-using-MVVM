package com.example.baatutechdemoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application, val repository: Repository) :
    AndroidViewModel(application) {

    private val listMutableLiveData = MutableLiveData<Result<ArrayList<User>>>()
    val listLiveData: LiveData<Result<ArrayList<User>>>
        get() = listMutableLiveData

    fun callParseDataCall() {
        repository.getList(getApplication(), object : ResponseHandler<Result<ArrayList<User>>> {
            override fun response(response: Result<ArrayList<User>>) {
                listMutableLiveData.value = response
            }
        })
    }
}