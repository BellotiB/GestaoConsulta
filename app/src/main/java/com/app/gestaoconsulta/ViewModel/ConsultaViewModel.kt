package com.app.gestaoconsulta.ViewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gestaoconsulta.Data.Cadastro
import com.app.gestaoconsulta.Data.Repository
import com.app.gestaoconsulta.Model.CadastroMedico
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConsultaViewModel  @Inject constructor(
  private val repository: Repository
) :ViewModel() {

   private var _cadastro = MutableStateFlow(CadastroMedico())
    var cadastro : StateFlow<CadastroMedico> = _cadastro

    private var _cadastroSelected = MutableStateFlow(CadastroMedico())
    var cadastroSelected : StateFlow<CadastroMedico> = _cadastroSelected

    private var _cadastroList = MutableStateFlow<MutableList<CadastroMedico>>(mutableListOf())
    var cadastroList : StateFlow<MutableList<CadastroMedico>> = _cadastroList

    val allCadastros : Flow<MutableList<Cadastro>> = repository.getAllCadastro

    fun insertCadastro() {
        viewModelScope.launch {
            cadastro.collectLatest { cad ->
               val cadastro =  Cadastro(id = 0, nome = cad.nome, especialidade = cad.especialidade)
                repository.insertCadastro(cadastro)
            }
        }
    }
    fun selectCadastroById(id: Int) {
        viewModelScope.launch {
            cadastroList.collectLatest { cadList ->
                cadList.forEach {
                    if(it.id == id){
                        cadastroSelected.value.id = it.id
                        cadastroSelected.value.nome = it.nome
                        cadastroSelected.value.especialidade = it.especialidade
                    }
                }
            }
        }
    }
    fun cleanCadastroList(){
        cadastroList.value.clear()
        cadastroList.value
    }
    fun deleteCadastro(cad: CadastroMedico){
        viewModelScope.launch(Dispatchers.IO) {
            val cadastro = Cadastro(
                nome = cad.nome,
                especialidade = cad.especialidade,
                id = cad.id
            )
            repository.deleteFromDataBase(cadastro)
        }
    }
}