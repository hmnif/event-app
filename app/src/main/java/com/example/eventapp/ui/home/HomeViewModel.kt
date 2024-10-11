package com.example.eventapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventapp.Response.AllEventResponse
import com.example.eventapp.Response.ListEventsItem
import com.example.eventapp.Retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _upComingEvent = MutableLiveData<List<ListEventsItem>>()
    val upComingEvent: LiveData<List<ListEventsItem>> = _upComingEvent

    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    val finishedEvent: LiveData<List<ListEventsItem>> = _finishedEvent

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {
        getUpComingEvent()
        getFinishedEvent()
    }

    private fun getUpComingEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents(1)

        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, response: Response<AllEventResponse>) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    _upComingEvent.value = response.body()?.listEvents?.take(5)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(p0: Call<AllEventResponse>, error: Throwable) {
                _isLoading.value = false
                _errorMessage.value = error.message

                Log.e(TAG, "onFailure: ${error.message}")

            }
        })
    }

    private fun getFinishedEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents(0)

        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, response: Response<AllEventResponse>) {

                _isLoading.value = false

                if (response.isSuccessful) {
                    _finishedEvent.value = response.body()?.listEvents?.take(5)
                } else {
                    Log.e(TAG, "aduh onFailure: ${response.message()}")
                }
            }

            override fun onFailure(p0: Call<AllEventResponse>, error: Throwable) {
                _isLoading.value = false
                _errorMessage.value = error.message

                Log.e(TAG, "aduh onFailure: ${error.message}")

            }
        })
    }
}