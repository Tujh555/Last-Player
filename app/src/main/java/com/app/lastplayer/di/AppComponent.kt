package com.app.lastplayer.di

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.di.modules.MainModule
import com.app.lastplayer.ui.fragments.AlbumDetailedFragment
import com.app.lastplayer.ui.fragments.MainFragment
import com.bumptech.glide.RequestManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ MainModule::class ])
@Singleton
interface AppComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: AlbumDetailedFragment)

    val glideRequestManager: RequestManager

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}