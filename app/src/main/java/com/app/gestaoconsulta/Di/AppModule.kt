package com.app.gestaoconsulta.Di

import android.content.Context
import androidx.room.Room
import com.app.gestaoconsulta.Data.CadastroDao
import com.app.gestaoconsulta.Data.DataBaseCadastros
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {
  @Provides
  fun provideCadastroDao (dataBaseCadastros : DataBaseCadastros ): CadastroDao{
      return dataBaseCadastros.cadastroDao()
  }

    @Provides
    @Singleton
    fun provideDataBaseCadastros(@ApplicationContext appContext: Context):
            DataBaseCadastros {
        return Room.databaseBuilder(
            appContext,
            DataBaseCadastros::class.java,
            "dataBaseCadastro.db"
        ).build()
    }

}