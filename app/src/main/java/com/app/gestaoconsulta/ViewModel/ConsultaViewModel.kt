package com.app.gestaoconsulta.ViewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gestaoconsulta.Data.Cadastro
import com.app.gestaoconsulta.Data.Repository
import com.app.gestaoconsulta.Model.CadastroMedico
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConsultaViewModel  @Inject constructor(
  private val repository: Repository
) :ViewModel() {

   private var _cadastro = MutableStateFlow(CadastroMedico())
    var cadastro : StateFlow<CadastroMedico> = _cadastro

    val allCadastros : Flow<MutableList<Cadastro>> = repository.getAllCadastro

    fun insertCadastro() {
        viewModelScope.launch {
            cadastro.collectLatest { cad ->
               val cadastro =  Cadastro(id = 0, nome = cad.nome, especialidade = cad.especialidade)
                repository.insertCadastro(cadastro)
            }
        }
    }
}