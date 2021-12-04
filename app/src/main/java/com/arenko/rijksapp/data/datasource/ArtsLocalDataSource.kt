package com.arenko.rijksapp.data.datasource

import com.arenko.rijksapp.data.model.ArtObject
import com.arenko.rijksapp.db.ArtDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtsLocalDataSource @Inject constructor(private val artDao: ArtDao) {
    suspend fun getArtsFromDB(page:Int): List<ArtObject> {
        return artDao.getArtObjects(page)
    }

    suspend fun saveArtsToDB(arts: List<ArtObject>) {
        CoroutineScope(Dispatchers.IO).launch {
            artDao.saveArtObjects(arts)
        }
    }

    fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            artDao.deleteAll()
        }
    }
}