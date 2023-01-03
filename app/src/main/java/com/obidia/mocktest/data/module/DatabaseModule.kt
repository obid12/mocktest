package com.obidia.mocktest.data.module

import android.content.Context
import androidx.room.Room
import com.obidia.mocktest.data.database.ProductDatabase
import com.obidia.mocktest.data.repository.ProductRepositoryImplementation
import com.obidia.mocktest.domain.repo.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app, ProductDatabase::class.java,
        ProductDatabase.DB_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideNoteRepository(db: ProductDatabase): ProductRepository {
        return ProductRepositoryImplementation(db.noteDao())
    }
}