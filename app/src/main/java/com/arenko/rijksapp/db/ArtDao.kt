package com.arenko.rijksapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arenko.rijksapp.data.Constants
import com.arenko.rijksapp.data.model.ArtObject

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArtObjects(artList: List<ArtObject>)

    @Query("DELETE FROM ${Constants.db_art_table}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${Constants.db_art_table} WHERE page = :pageNumber")
    suspend fun getArtObjects(pageNumber: Int): List<ArtObject>
}