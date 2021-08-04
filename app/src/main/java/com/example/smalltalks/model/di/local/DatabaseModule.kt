package com.example.smalltalks.model.di.local

import android.content.Context
import androidx.room.Room
import com.example.smalltalks.model.repository.local.MessageDatabase
import com.example.smalltalks.model.repository.local.UserConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, converter: UserConverter): MessageDatabase {
        return Room.databaseBuilder(context, MessageDatabase::class.java, "MessageDatabase")
            .addTypeConverter(converter)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMessageDao(messageDatabase: MessageDatabase) = messageDatabase.messageDao()
}