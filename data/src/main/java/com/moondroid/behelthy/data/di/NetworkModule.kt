@file:Suppress("SpellCheckingInspection")

package com.moondroid.behelthy.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.moondroid.behealthy.common.BuildConfig
import com.moondroid.behelthy.data.api.ApiInterface
import com.moondroid.behelthy.data.api.response.ResponseAdapterFactory
import com.moondroid.behelthy.data.datasource.local.LocalDataSource
import com.moondroid.behelthy.data.datasource.local.LocalDataSourceImpl
import com.moondroid.behelthy.data.datasource.remote.RemoteDataSource
import com.moondroid.behelthy.data.datasource.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.internal.EMPTY_RESPONSE
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://moondroid.dothome.co.kr/behealthy/"
    //private const val BASE_URL = "http://moondroid.dothome.co.kr/behealthy-test/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor {
            Log.d("BeHealthy", it)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().setLenient().create())
    }

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        nullOnEmptyConverterFactory: NullOnEmptyConverterFactory,
    ): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(ResponseAdapterFactory())
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .client(provideHttpClient())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideNullOnEmptyConvertFactory(): NullOnEmptyConverterFactory {
        return NullOnEmptyConverterFactory()
    }

    /**
     * 비어있는(length=0)인 Response를 받았을 경우 처리
     */
    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit,
        ): Converter<ResponseBody, *> {
            val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
            return Converter<ResponseBody, Any> {
                if (it.contentLength() == 0L) return@Converter EMPTY_RESPONSE
                delegate.convert(it)
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalBindModule {
    @Binds
    @Singleton
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
}
