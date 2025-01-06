package com.furkanharmanc.aventra.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelBookDao {

    @Upsert
    suspend fun upsertTravelBook(travelBook: TravelBook)

    @Delete
    suspend fun deleteTravelBook(travelBook: TravelBook)

    @Query(
        "SELECT * FROM travel_book_table"
    )
    fun getAllTravelBooks(): Flow<List<TravelBook>>

    @Query(
        "SELECT * FROM travel_book_table WHERE id = :id"
    )
    fun getTravelBookById(id: Int): Flow<TravelBook>
}