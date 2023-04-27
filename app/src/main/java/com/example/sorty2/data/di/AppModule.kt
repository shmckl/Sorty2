package com.example.sorty2.data.di

import com.example.sorty2.data.repository.ListsRepositoryImpl
import com.example.sorty2.domain.ListsRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideListsRepository(database: FirebaseDatabase): ListsRepository =
        ListsRepositoryImpl(database)

    @Provides
    @Singleton
    fun provideRealtimeDatabase(): FirebaseDatabase =
        Firebase.database("https://sorty2-22ac4-default-rtdb.europe-west1.firebasedatabase.app/")

}