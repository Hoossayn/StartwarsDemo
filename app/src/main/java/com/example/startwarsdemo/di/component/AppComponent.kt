package com.example.startwarsdemo.di.component

import com.example.startwarsdemo.di.module.NetworkModule
import com.example.startwarsdemo.di.module.RepositoriesModule
import com.example.startwarsdemo.ui.details.CharacterDetailsActivity
import com.example.startwarsdemo.ui.search.SearchCharactersActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [RepositoriesModule::class, NetworkModule::class]
)
interface AppComponent {
    fun inject(searchCharactersActivity: SearchCharactersActivity)

    fun inject(detailsCharactersActivity: CharacterDetailsActivity)
}