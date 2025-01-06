package com.furkanharmanc.aventra.domain

import com.furkanharmanc.aventra.data.TravelBook
import com.furkanharmanc.aventra.data.TravelBookDao

class AventraRepository(private val dao: TravelBookDao) {

    suspend fun upsertTravelBook(travelBook: TravelBook) =
        dao.upsertTravelBook(travelBook)

    suspend fun deleteTravelBook(travelBook: TravelBook) =
        dao.deleteTravelBook(travelBook)

    fun getTravelBookById(id: Int) =
        dao.getTravelBookById(id)

    fun getAllTravelBooks() =
        dao.getAllTravelBooks()
}