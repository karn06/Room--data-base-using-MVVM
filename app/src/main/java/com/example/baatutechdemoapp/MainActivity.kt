package com.example.baatutechdemoapp

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baatutechdemoapp.databinding.ActivityMainBinding
import com.example.baatutechdemoapp.roomDatabse.DatabaseClass
import com.example.daytoday.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var model: MainViewModel
    private lateinit var adapter: Adapter
    lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        setNetworkReceiver()
        initViewModel()
        adapter = Adapter()
        clickListener()
        callApi()
    }

    private fun initViewModel() {
        model = getViewModel()
    }

    private fun clickListener() {
        activityMainBinding.startButton.setOnClickListener {
            getData()
        }
    }

    private fun getData() {
        model.listLiveData.observe(this,
            Observer {
                when (it) {
                    is Result.Loading -> {
                        activityMainBinding.progressBar.visibility = View.VISIBLE
                        activityMainBinding.startButton.visibility = View.GONE
                    }

                    is Result.Success -> {
                        activityMainBinding.startButton.visibility = View.GONE
                        activityMainBinding.progressBar.visibility = View.GONE
                        activityMainBinding.recyclerView.visibility = View.VISIBLE
                        setAdapter(it.data)
                        setTextValue(getString(R.string.success))
                        DatabaseClass.getDatabase(applicationContext)?.dao?.insertAllData(it.data)
                        saveDataToast()
                    }
                    is Result.Error -> {
                        activityMainBinding.startButton.visibility = View.VISIBLE
                        activityMainBinding.recyclerView.visibility = View.GONE
                        activityMainBinding.progressBar.visibility = View.GONE
                        setTextValue(getString(R.string.fail))
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.something_went_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private fun setTextValue(textValue: String) {
        activityMainBinding.welcome1.text = textValue
        activityMainBinding.welcome2.text = textValue
        activityMainBinding.welcome3.text = textValue
        activityMainBinding.welcome4.text = textValue
    }

    private fun getViewModel(): MainViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    private fun setAdapter(pages: ArrayList<User>) {
        val layoutManager = LinearLayoutManager(applicationContext)
        activityMainBinding.recyclerView.layoutManager = layoutManager
        adapter.setData(applicationContext, pages)
        activityMainBinding.recyclerView.adapter = adapter
    }


    private fun callApi() {
        model.callParseDataCall()
    }

    private fun saveDataToast() {
        Toast.makeText(this, "Data Successfully Saved", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(
                networkChangeReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            unregisterReceiver(networkChangeReceiver)
        }
    }


    private fun setNetworkReceiver() {

        networkChangeReceiver = object : NetworkChangeReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (!FunctionUtil.isInternetOn(this@MainActivity)) {
                    setTextValue("No internet connection")
                    getLocalData()
                } else {
                    activityMainBinding.startButton.visibility = View.VISIBLE
                    activityMainBinding.recyclerView.visibility = View.GONE
                    setTextValue(getString(R.string.back_online))
                }
            }
        }

    }

    private fun getLocalData() {
        activityMainBinding.startButton.visibility = View.GONE
        activityMainBinding.progressBar.visibility = View.GONE
        activityMainBinding.recyclerView.visibility = View.VISIBLE
        setAdapter(DatabaseClass.getDatabase(applicationContext)?.dao?.getAllData() as ArrayList<User>)
    }
}