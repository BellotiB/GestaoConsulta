import com.app.gestaoconsulta.DataBase.Entities.CadastroEntity
import com.app.gestaoconsulta.SyncApi.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetCadastroMedicoToServer @Inject constructor(
   private val apiService: ApiService
) {
    fun fetchCadastroMedico(list: MutableList<CadastroEntity>) {
        val result = runCatching {apiService.setCadastroMedico(list).execute() }

        result.onSuccess { response ->
            response.isSuccessful
        }
        result.onFailure { respnose ->
          respnose.printStackTrace()
        }
    }
}
