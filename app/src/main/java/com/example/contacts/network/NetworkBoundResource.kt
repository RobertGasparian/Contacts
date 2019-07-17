package com.example.contacts.network

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class NetworkBoundResource<ResultType>(needLoading: Boolean = true) {
    val liveData: MutableLiveData<Resource<ResultType>> = MutableLiveData()

    init {
        if (needLoading) {
            liveData.value = Resource.Loading(data = null)
        }
        this.createCall()
    }

    @MainThread
    protected abstract fun createCall()

    fun setValue(value: Resource<ResultType>) {
        liveData.value = value
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return liveData
    }
}