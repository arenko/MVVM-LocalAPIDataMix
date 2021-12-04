package com.arenko.rijksapp.di

import android.app.Application
import android.content.Context
import com.arenko.rijksapp.data.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Singleton
    class CustomSharedPreferences @Inject constructor(@ApplicationContext context: Context) {
        val prefs = context.getSharedPreferences(Constants.RIJKS_PREFERENCES, Context.MODE_PRIVATE)

        fun getBoolean(tag: String): Boolean {
            return prefs.getBoolean(tag, false)
        }

        fun putBoolean(tag: String, query: Boolean) {
            prefs.edit().putBoolean(tag, query).apply()
        }
    }
}