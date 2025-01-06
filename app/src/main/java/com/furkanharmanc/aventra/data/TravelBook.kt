package com.furkanharmanc.aventra.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("travel_book_table")
data class TravelBook(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val snippet: String,
    val latitude: Double,
    val longitude: Double
)