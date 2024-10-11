package com.example.eventapp.ui.finished

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

class FinishedViewModel : ViewModel() {
    private val _listFinished = MutableLiveData<List<ListEventsItem>>()
    val listFinished: LiveData<List<ListEventsItem>> = _listFinished

    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()
    val isLoading: LiveData<Boolean> = _isLoading
    val errorMessage: LiveData<String> = _errorMessage

    companion object {
        private const val TAG = "FinishedViewModel"
        private const val ACTIVE = 0
    }

    init {
        getFinishedEvent()

    }

    private fun getFinishedEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents(ACTIVE)

        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, response: Response<AllEventResponse>) {

                _isLoading.value = false

                if (response.isSuccessful) {
                    _listFinished.value = response.body()?.listEvents
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