package com.cheapta.app.di

import android.content.Context
import com.cheapta.app.data.Api
import com.cheapta.app.data.BASE_URL
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideHttpInterceptor(@ApplicationContext context: Context): Interceptor =
        ChuckerInterceptor.Builder(context).build()

    @Provides
    fun provideOkHttpClient(httpInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .build()

    @Provides
    fun provideApi(client: OkHttpClient): Api =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

}