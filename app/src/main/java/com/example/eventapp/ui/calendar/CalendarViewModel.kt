package com.example.eventapp.ui.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventapp.Response.ListEventsItem
import com.example.eventapp.Response.AllEventResponse
import com.example.eventapp.Retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalendarViewModel : ViewModel() {

    private val _listCalendar = MutableLiveData<List<ListEventsItem>>()
    val listCalendar: LiveData<List<ListEventsItem>> = _listCalendar

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    companion object {
        private const val TAG = "CalendarViewModel"
        private const val ACTIVE = 1
    }

    init {
        getCalendarEvent()
    }

    private fun getCalendarEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents(ACTIVE)

        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, response: Response<AllEventResponse>) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    _listCalendar.value = response.body()?.listEvents
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
}