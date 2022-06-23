package com.app.lastplayer.di

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.di.modules.MainModule
import com.app.lastplayer.ui.fragments.MainFragment
import com.bumptech.glide.RequestManager
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ MainModule::class ])
interface AppComponent {
    fun inject(fragment: MainFragment)

    val glideRequestManager: RequestManager

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}