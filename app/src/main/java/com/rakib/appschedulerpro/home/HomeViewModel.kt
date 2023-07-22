package com.rakib.appschedulerpro.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rakib.appschedulerpro.model.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(homeRepository: HomeRepository) : ViewModel() {
    val getScheduleData: LiveData<List<Schedule>> = homeRepository.getScheduleData.flowOn(
        Dispatchers.Main
    ).asLiveData(context = viewModelScope.coroutineContext)
}