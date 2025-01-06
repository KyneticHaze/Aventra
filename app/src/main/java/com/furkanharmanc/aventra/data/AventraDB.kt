package com.furkanharmanc.aventra.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TravelBook::class],
    version = 1
)
abstract class AventraDB: RoomDatabase() {
    abstract fun travelBookDao(): TravelBookDao
}