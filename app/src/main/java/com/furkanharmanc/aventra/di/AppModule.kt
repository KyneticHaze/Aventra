package com.furkanharmanc.aventra.di

import android.content.Context
import androidx.room.Room
import com.furkanharmanc.aventra.data.AventraDB
import com.furkanharmanc.aventra.data.TravelBookDao
import com.furkanharmanc.aventra.domain.AventraRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AventraDB =
        Room.databaseBuilder(
            context,
            AventraDB::class.java,
            "aventra_db"
        ).build()

    @Provides
    @Singleton
    fun provideTravelBookDao(db: AventraDB) = db.travelBookDao()

    @Provides
    @Singleton
    fun provideAventraRepository(dao: TravelBookDao) = AventraRepository(dao)
}