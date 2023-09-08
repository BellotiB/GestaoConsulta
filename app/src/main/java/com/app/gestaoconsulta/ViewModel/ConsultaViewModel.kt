package com.app.gestaoconsulta.ViewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gestaoconsulta.Data.CadastroEntity
import com.app.gestaoconsulta.Data.DataCadastradaEntity
import com.app.gestaoconsulta.Data.Repository
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.DatasCadastradas
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

    private var _datasCadastradas = MutableStateFlow<MutableList<DatasCadastradas>>(mutableListOf())
    var datasCadastradas : StateFlow<MutableList<DatasCadastradas>> = _datasCadastradas


    val allCadastros : Flow<MutableList<CadastroEntity>> = repository.getAllCadastroEntity

    val allDatasCadastradas : Flow<MutableList<DataCadastradaEntity>> = repository.getAllDatasEntity

    fun insertCadastro() {
        viewModelScope.launch {
            cadastro.collectLatest { cad ->
               val cadastroEntity =  CadastroEntity(id = 0, nome = cad.nome, especialidade = cad.especialidade)
                repository.insertCadastro(cadastroEntity)
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
            val cadastroEntity = CadastroEntity(
                nome = cad.nome,
                especialidade = cad.especialidade,
                id = cad.id
            )
            repository.deleteCadastroFromDataBase(cadastroEntity)
        }
    }
    fun setDatasCadastradas() {
        viewModelScope.launch {
            val listDatas = mutableListOf<DataCadastradaEntity>()
            datasCadastradas.collectLatest { datas ->
                for (list in datas){
                    val dataEntity = DataCadastradaEntity(
                        id = list.id,
                        startDate = list.startDate,
                        startHora = list.startHora,
                        endDate = list.endDate,
                        endHora = list.endHora,
                        periodoAtendimento = list.periodoAtendimento
                    )
                    listDatas.add(dataEntity)
                }
                repository.insertDataCadastrada(listDatas)
            }
        }
    }
}