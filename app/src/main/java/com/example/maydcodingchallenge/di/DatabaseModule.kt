package com.example.maydcodingchallenge.di

import android.content.Context
import com.example.maydcodingchallenge.data.local.ShortLinkDao
import com.example.maydcodingchallenge.data.local.ShortLinkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ShortLinkDatabase {
        return ShortLinkDatabase.getInstance(context)
    }

    @Provides
    fun providePlantDao(appDatabase: ShortLinkDatabase): ShortLinkDao {
        return appDatabase.ShortLinkDao()
    }

}
