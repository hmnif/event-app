package com.example.eventapp.ui.detail_event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventapp.Response.DetailEventResponse
import com.example.eventapp.Retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel : ViewModel() {

    private val _detailEvent = MutableLiveData<DetailEventResponse>()
    val detailEvent: LiveData<DetailEventResponse> = _detailEvent

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getDetailEvent(id: Int) {
        if (_detailEvent.value != null) {
            // Data already loaded, no need to reload
            return
        }

        _loading.postValue(true)


        val client = ApiConfig.getApiService().getDetailEvent(id)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                _loading.postValue(false)
                if (response.isSuccessful) {
                    Log.d("DetailActivityLog", "onResponse: ${response.body()}")
                    _detailEvent.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _loading.postValue(false)

                Log.d("DetailActivityLog", "onFailure: ${t.message}")
            }
        })
    }


}