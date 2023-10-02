package com.app.gestaoconsulta.ViewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gestaoconsulta.Data.CadastroEntity
import com.app.gestaoconsulta.Data.DataCadastradaEntity
import com.app.gestaoconsulta.Data.Repository
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.SyncApi.SetCadastroMedicoToServer
import com.app.gestaoconsulta.SyncApi.SetDatasCadastradasToServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ConsultaViewModel  @Inject constructor(
  private val repository: Repository
) :ViewModel() {

   private var _cadastro = MutableStateFlow(CadastroMedico())
    var cadastro : StateFlow<CadastroMedico> = _cadastro

    private var _idDataCadastrada = MutableStateFlow("")
    var idDataCadastrada : StateFlow<String> = _idDataCadastrada

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
    }
    private fun cleanDatasList(){
        datasCadastradas.value.clear()
    }
    fun deleteCadastro(cad: CadastroMedico){
        viewModelScope.launch(Dispatchers.IO) {
            val cadastroEntity = CadastroEntity(
                nome = cad.nome,
                especialidade = cad.especialidade,
                id = cad.id
            )
            repository.deleteCadastroFromDataBase(cadastroEntity)
            deleteDatasPorCadastro(cad.id)
        }
    }
    fun deleteDataCadastrada(dat: DatasCadastradas){
        viewModelScope.launch(Dispatchers.IO) {
            val dataEntity = DataCadastradaEntity(
             id = dat.id,
                idCadastro = dat.idCadastro,
                startDate = dat.startDate,
                idDataCadastrada = dat.idDataCadastrada,
                startHora = dat.startHora,
                endDate = dat.endDate,
                endHora = dat.endHora,
                periodoAtendimento = dat.periodoAtendimento,
                periodoPausa = dat.periodoPausa
            )
            repository.deleteDataFromDataBase(dataEntity)
        }
    }
    fun setDatasCadastradas() {
        viewModelScope.launch {
            val listDatas = mutableListOf<DataCadastradaEntity>()
            datasCadastradas.collectLatest { datas ->
                for (list in datas){
                    val dataEntity = DataCadastradaEntity(
                        id = 0,
                        idCadastro = list.idCadastro,
                        idDataCadastrada = list.idDataCadastrada,
                        startDate = list.startDate,
                        startHora = list.startHora,
                        endDate = list.endDate,
                        endHora = list.endHora,
                        periodoAtendimento = list.periodoAtendimento,
                        periodoPausa = list.periodoPausa
                    )
                    listDatas.add(dataEntity)
                }
                repository.insertDataCadastrada(listDatas)
                cleanDatasList()
            }
        }
    }
    fun updateCadastroServer(){
        viewModelScope.launch {
            allCadastros.collect{ list ->
                 SetCadastroMedicoToServer().fecthCadastroMedico(list)
            }
        }
    }
    fun updateDatasCadastradasServer(){
        viewModelScope.launch {
            allDatasCadastradas.collect{ datasList->
                SetDatasCadastradasToServer().fecthDataCadastrada(datasList)
            }
        }
    }
    private fun deleteDatasPorCadastro(idCadastro: Int) {
        val listDatasToDelete = mutableListOf<DataCadastradaEntity>()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                allDatasCadastradas.collect{ list ->
                    list.forEach {
                        if(it.idCadastro == idCadastro){
                            listDatasToDelete.add(it)
                        }
                    }
                    repository.deleteAllDatasPorCadastro(listDatasToDelete)
                }
            }
        }
    }
    fun  setIdData(idData: String) {
       viewModelScope.launch {
           _idDataCadastrada.value = idData
       }
    }
}