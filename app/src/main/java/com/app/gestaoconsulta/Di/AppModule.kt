package com.app.gestaoconsulta.Di

import android.content.Context
import androidx.room.Room
import com.app.gestaoconsulta.DataBase.Daos.CadastroDao
import com.app.gestaoconsulta.DataBase.DataBaseCadastros
import com.app.gestaoconsulta.DataBase.Daos.DataCadastradaDao
import com.app.gestaoconsulta.DataBase.Daos.HoraCadastradaDao
import com.app.gestaoconsulta.DataBase.Daos.PedidosAgendamentosDao
import com.app.gestaoconsulta.DataBase.Daos.PedidosPorUsuariosDao
import com.app.gestaoconsulta.DataBase.Daos.UsuarioDao
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
  fun provideCadastroDao (dataBaseCadastros : DataBaseCadastros ): CadastroDao {
      return dataBaseCadastros.cadastroDao()
  }
    @Provides
  fun provideDataCadastradaDao (dataBaseCadastros : DataBaseCadastros ): DataCadastradaDao {
      return dataBaseCadastros.dataCadastradaDao()
  }
    @Provides
  fun provideHoraCadastradaDao (dataBaseCadastros : DataBaseCadastros ): HoraCadastradaDao {
      return dataBaseCadastros.horaCadastradaDao()
  }
    @Provides
  fun provideUsuarioaDao (dataBaseCadastros : DataBaseCadastros ): UsuarioDao {
      return dataBaseCadastros.usuarioDao()
  }
    @Provides
  fun providePedidosAgendamento (dataBaseCadastros : DataBaseCadastros ): PedidosAgendamentosDao {
      return dataBaseCadastros.pediddosAgendamentosDao()
  }
    @Provides
    fun provideAgendamentoPorUsuario (dataBaseCadastros : DataBaseCadastros ): PedidosPorUsuariosDao {
      return dataBaseCadastros.pedidosPorUsuarios()
  }

    @Provides
    @Singleton
    fun provideDataBaseCadastros(@ApplicationContext appContext: Context):
            DataBaseCadastros {
        return Room.databaseBuilder(
            appContext,
            DataBaseCadastros::class.java,
            "dataBaseCadastro.db")
            .addMigrations(DataBaseCadastros.MIGRATION_1_2)
            .build()
    }
}