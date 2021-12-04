package com.arenko.rijksapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.arenko.rijksapp.data.Constants
import com.google.gson.annotations.Expose

@Entity(tableName = Constants.db_art_table)
data class ArtObject(
    @PrimaryKey
    @Expose
    val objectNumber: String,
    @Expose
    val title: String?,
    @Expose
    val principalOrFirstMaker: String?,
    @Expose
    val description: String?,
    @Expose
    @TypeConverters(ArtConverter::class)
    val headerImage: ArtImage?,
    var page: Int = 1
)