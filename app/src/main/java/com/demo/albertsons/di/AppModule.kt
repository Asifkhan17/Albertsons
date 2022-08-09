package com.demo.albertsons.di

import com.demo.albertsons.ui.AdapterAcromine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Provides
    @Singleton
    fun provideAdapter(): AdapterAcromine = AdapterAcromine()
}
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope