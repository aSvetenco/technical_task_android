package com.sa.gorestuserstask.app.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sa.gorestuserstask.BuildConfig
import com.sa.gorestuserstask.data.remote.AuthenticationInterceptor
import com.sa.gorestuserstask.data.remote.ErrorResponse
import com.sa.gorestuserstask.data.remote.error.ErrorResponseDeserializer
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder()
        .registerTypeAdapter(
            object : TypeToken<List<ErrorResponse>>() {}.type,
            ErrorResponseDeserializer())
        .create()

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(BuildConfig.AUTH_TOKEN))
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun retrofitClient(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    companion object {
        private const val HOST = "gorest.co.in/public"
        private const val SCHEMA = "https://"
        private const val BASE_URL = "$SCHEMA$HOST/"
        private const val CONNECTION_TIME_OUT = 60L
        private const val READ_TIME_OUT = 60L
        private const val WRITE_TIME_OUT = 60L
    }
}
