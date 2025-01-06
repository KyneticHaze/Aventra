package com.furkanharmanc.aventra.ui.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanharmanc.aventra.data.TravelBook
import com.furkanharmanc.aventra.domain.AventraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: AventraRepository
): ViewModel() {

    private val _viewState = MutableStateFlow(MapViewState())
    val viewState = _viewState.asStateFlow()

    fun upsertTravelBook(travelBook: TravelBook) = viewModelScope.launch {
        repository.upsertTravelBook(travelBook)
        _viewState.update {
            it.copy(travelBooks = it.travelBooks + travelBook)
        }
    }

    fun deleteTravelBook(travelBook: TravelBook) = viewModelScope.launch {
        repository.deleteTravelBook(travelBook)
        _viewState.update { viewState ->
            viewState.copy(
                travelBooks = viewState.travelBooks.filter { it.id != travelBook.id },
                selectedTravel = if(viewState.selectedTravel == travelBook) null else viewState.selectedTravel
            )
        }
    }

    fun selectTravelBook(travelBook: TravelBook) =
        _viewState.update {
            it.copy(selectedTravel = travelBook)
        }

    fun resetTravelBook() =
        _viewState.update {
            it.copy(selectedTravel = null)
        }
}

data class MapViewState(
    val travelBooks: List<TravelBook> = emptyList(),
    val selectedTravel: TravelBook? = null
)