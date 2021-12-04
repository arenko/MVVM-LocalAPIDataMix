package com.arenko.rijksapp.di

import android.content.Context
import androidx.room.Room
import com.arenko.rijksapp.db.ArtDao
import com.arenko.rijksapp.db.RijksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideMovieDataBase(context: Context): RijksDatabase {
        return Room.databaseBuilder(context, RijksDatabase::class.java, "rijks_db")
            .build()
    }

    @Singleton
    @Provides
    fun provideArtDao(rijksDatabase: RijksDatabase): ArtDao {
        return rijksDatabase.artDao()
    }

}