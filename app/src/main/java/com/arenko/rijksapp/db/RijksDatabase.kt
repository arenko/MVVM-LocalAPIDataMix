package com.arenko.rijksapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arenko.rijksapp.data.model.ArtConverter
import com.arenko.rijksapp.data.model.ArtObject


@Database(
    entities = [ArtObject::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ArtConverter::class)
abstract class RijksDatabase : RoomDatabase() {
    abstract fun artDao(): ArtDao

}