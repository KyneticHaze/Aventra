package com.furkanharmanc.aventra.ui.screen.travelbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanharmanc.aventra.data.TravelBook
import com.furkanharmanc.aventra.domain.AventraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TravelBookViewModel @Inject constructor(
    private val repository: AventraRepository
): ViewModel() {


    private val _travelBooks = MutableStateFlow<List<TravelBook>>(emptyList())
    val travelBooks = _travelBooks.asStateFlow()

    init {
        loadTravelBooks()
    }

    private fun loadTravelBooks() = viewModelScope.launch {
        repository.getAllTravelBooks().collect(_travelBooks::emit)
    }

    fun deleteTravelBook(travelBook: TravelBook) = viewModelScope.launch {
        repository.deleteTravelBook(travelBook)
    }
}