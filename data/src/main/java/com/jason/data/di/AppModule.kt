package com.jason.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jason.data.BuildConfig
import com.jason.data.core.FlowCallAdapterFactory
import com.jason.data.db.SportDatabase
import com.jason.data.repository.MatchRepository
import com.jason.data.repository.TeamRepository
import com.jason.domain.repository.IMatchRepository
import com.jason.domain.repository.ITeamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSportDatabase(@ApplicationContext context: Context): SportDatabase {
        return Room
            .databaseBuilder(context, SportDatabase::class.java, SportDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson
    ): Retrofit {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClientBuilder.build())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideMatchRepository(dataSource: MatchRepository): IMatchRepository = dataSource

    @Provides
    @Singleton
    fun provideTeamRepository(dataSource: TeamRepository): ITeamRepository = dataSource
}