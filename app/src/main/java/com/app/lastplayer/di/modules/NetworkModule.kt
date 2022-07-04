package com.app.lastplayer.di.modules

import android.content.Context
import com.app.lastplayer.Constants.BASE_JAMENDO_URL
import com.app.lastplayer.api.jamendo.JamendoApi
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ UseCasesModule::class, PagingModule::class ])
class NetworkModule {

    @Provides
    fun provideJamendoApi(): JamendoApi = Retrofit.Builder()
        .baseUrl(BASE_JAMENDO_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JamendoApi::class.java)

    @Provides
    fun provideGlideRequestManager(context: Context) = Glide.with(context)

    @Singleton
    @Provides
    fun provideAuthentication(): FirebaseAuth = FirebaseAuth.getInstance()
}