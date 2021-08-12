package com.example.smalltalks.di.repository.local

import android.content.Context
import androidx.room.Room
import com.natife.example.domain.local.MessageDatabase
import com.natife.example.domain.local.UserConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "MessageDatabase"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, converter: UserConverter): MessageDatabase {
        return Room.databaseBuilder(context, MessageDatabase::class.java, DATABASE_NAME)
            .addTypeConverter(converter)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMessageDao(messageDatabase: MessageDatabase) = messageDatabase.messageDao()
}