package com.example.startwarsdemo.di.module

import com.example.startwarsdemo.data.dataSource.remote.StarWarsDataSourceImpl
import com.example.startwarsdemo.data.dataSource.remote.StarWarsServices
import com.example.startwarsdemo.data.repository.StarWarsRepositoryImpl
import com.example.startwarsdemo.domain.repository.StarWarsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideAppRepository(
        api: StarWarsServices,
    ): StarWarsRepository {
        val starWarsDataSourceImpl = StarWarsDataSourceImpl(api)
        return StarWarsRepositoryImpl(starWarsDataSourceImpl)
    }
}
