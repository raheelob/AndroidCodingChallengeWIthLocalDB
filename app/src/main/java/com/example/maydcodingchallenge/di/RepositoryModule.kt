package com.example.maydcodingchallenge.di;

import com.example.maydcodingchallenge.data.repository.ShortenUrlRepository
import com.example.maydcodingchallenge.data.repository.ShortenUrlRepositoryImpl
import dagger.Binds
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindShortenUrlRepository(shortenUrlRepositoryImpl: ShortenUrlRepositoryImpl): ShortenUrlRepository
}
