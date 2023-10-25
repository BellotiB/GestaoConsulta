package com.app.gestaoconsulta.ViewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gestaoconsulta.Data.Entities.CadastroEntity
import com.app.gestaoconsulta.Data.Entities.DataCadastradaEntity
import com.app.gestaoconsulta.Data.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.Data.Entities.UsuarioEntity
import com.app.gestaoconsulta.Data.Repository
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.Model.HorariosCadastrados
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.SyncApi.GetPedidosAgendamentos
import com.app.gestaoconsulta.SyncApi.GetUsuarios
import com.app.gestaoconsulta.SyncApi.SetCadastroMedicoToServer
import com.app.gestaoconsulta.SyncApi.SetDatasCadastradasToServer
import com.app.gestaoconsulta.SyncApi.SetHorasToServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.forEach
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

    val allUsuarios : Flow<MutableList<UsuarioEntity>> = repository.getAllUsersEntity

    val allDatasCadastradas : Flow<MutableList<DataCadastradaEntity>> = repository.getAllDatasEntity

    val allHorasCadastradas : Flow<MutableList<HoraCadastradaEntity>> = repository.getAllHorasEntity

    private var pedidosAgendamentoFlow = MutableStateFlow<MutableList<PedidoAgendamento>>(mutableListOf())
    val pedidosAgendamento: StateFlow<MutableList<PedidoAgendamento>> = pedidosAgendamentoFlow

    private var usuariosFlow = MutableStateFlow<MutableList<Usuarios>>(mutableListOf())
    val usuariosCadastrados: StateFlow<MutableList<Usuarios>> = usuariosFlow


    init {
        streamPedidosAgendamento()
        streamUsuario()
    }

    private fun streamPedidosAgendamento() {
        viewModelScope.launch {
            flow {
                while (true) {
                    val horasCadastradas = GetPedidosAgendamentos().fecthAgendamentos()
                    emit(horasCadastradas)
                    delay(3000)
                }
            }
                .flowOn(Dispatchers.IO)
                .collectLatest { pedAgendamento ->
                    pedidosAgendamentoFlow.value =  pedAgendamento
                    updateHoraFromServer()
                    updateHorasCadastradosServer()
                }
        }
    }
    private fun streamUsuario() {
        viewModelScope.launch {
            flow {
                while (true) {
                    val usuariosCadastrados = GetUsuarios().fecthUsuarios()
                    emit(usuariosCadastrados)
                    delay(13000)
                }
            }
                .flowOn(Dispatchers.IO)
                .collectLatest { users ->
                    usuariosFlow.value = users
                    updateOrInsertUsuario()
                }
        }
    }

    private fun updateOrInsertUsuario() {
        viewModelScope.launch(Dispatchers.IO) {
           allUsuarios.collectLatest {
               if(it.isNotEmpty()){
                   it.forEach {
                       usuariosCadastrados.value.forEach { user ->
                           if(it.idUsuario == user.idUsuario){
                               val usuario = UsuarioEntity(
                                   nome = user.nome,
                                   id = user.id,
                                   idUsuario = user.idUsuario,
                                   email = user.email,
                                   telefone = user.telefone
                               )
                               repository.updateUsuario(usuario)
                           }else{
                               val usuario = UsuarioEntity(
                                   nome = user.nome,
                                   id = user.id,
                                   idUsuario = user.idUsuario,
                                   email = user.email,
                                   telefone = user.telefone
                               )
                               repository.insertUsuario(usuario)
                           }
                       }
                   }
               }else{
                   usuariosCadastrados.value.forEach {user ->
                       val usuario = UsuarioEntity(
                           nome = user.nome,
                           id = user.id,
                           idUsuario = user.idUsuario,
                           email = user.email,
                           telefone = user.telefone
                       )
                       repository.insertUsuario(usuario)
                   }
               }
           }
        }
    }

    private fun updateHoraFromServer() {
        pedidosAgendamentoFlow.value.forEach {
            updateHoraCadastrada(it.horariosDisponiveis)
        }
    }

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
                dataAtendimento = dat.dataAtendimento,
                idDataCadastrada = dat.idDataCadastrada,
            )
            deleteHorasFromDatasCadastradas(dat.idDataCadastrada)
            repository.deleteDataFromDataBase(dataEntity)
        }
    }
    private fun deleteHorasFromDatasCadastradas(dat: String) {
         viewModelScope.launch(Dispatchers.IO) {
             allHorasCadastradas.collect{ list ->
                 list.forEach {
                     if(it.idDataCadastrada == dat){
                        repository.deleteHoraPorData(it)
                     }
                 }
             }
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
                        dataAtendimento = list.dataAtendimento,
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
    fun updateHorasCadastradosServer(){
        viewModelScope.launch {
            allHorasCadastradas.collect{ horasList->
                SetHorasToServer().fecthHoraCadastrada(horasList)
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
                    deleteAllHorasPorData(listDatasToDelete)
                }
            }
        }
    }
    private fun deleteAllHorasPorData(listDatasToDelete: MutableList<DataCadastradaEntity>) {
        listDatasToDelete.forEach {
            deleteHorasFromDatasCadastradas(it.idDataCadastrada)
        }
    }

    fun  setIdData(idData: String) {
       viewModelScope.launch {
           _idDataCadastrada.value = idData
       }
    }

    fun  insertHoraCadastrada(hr: HorariosCadastrados) {
        viewModelScope.launch(Dispatchers.IO) {
           val horaEntity = HoraCadastradaEntity (
               id = hr.id,
               idDataCadastrada = hr.idDataCadastrada,
               cinco = hr.cinco,
               cincoQuinze = hr.cincoQuinze,
               cincoTrinta = hr.cincoTrinta,
               cincoQuarentaCinco = hr.cincoQuarentaCinco,

               seis = hr.seis,
               seisQuinze = hr.seisQuinze,
               seisTrinta = hr.seisTrinta,
               seisQuarentaCinco = hr.seisQuarentaCinco,

               sete = hr.sete,
               seteQuinze = hr.seteQuinze,
               seteTrinta = hr.seteTrinta,
               seteQuarentaCinco = hr.seteQuarentaCinco,

               oito = hr.oito,
               oitoQuinze = hr.oitoQuinze,
               oitoTrinta = hr.oitoTrinta,
               oitoQuarentaCinco = hr.oitoQuarentaCinco,

               nove = hr.nove,
               noveQuinze = hr.noveQuinze,
               noveTrinta = hr.noveTrinta,
               noveQuarentaCinco = hr.noveQuarentaCinco,

               dez = hr.dez,
               dezQuinze = hr.dezQuinze,
               dezTrinta = hr.dezTrinta,
               dezQuarentaCinco = hr.dezQuarentaCinco,

               onze = hr.onze,
               onzeQuinze = hr.onzeQuinze,
               onzeTrinta = hr.onzeTrinta,
               onzeQuarentaCinco = hr.onzeQuarentaCinco
           )
           repository.insertHrCadastrada(horaEntity)
       }
    }
    fun  updateHoraCadastrada(hr: HorariosCadastrados) {
        viewModelScope.launch(Dispatchers.IO) {
            val horaEntity = HoraCadastradaEntity (
                id = hr.id,
                idDataCadastrada = hr.idDataCadastrada,
                cinco = hr.cinco,
                cincoQuinze = hr.cincoQuinze,
                cincoTrinta = hr.cincoTrinta,
                cincoQuarentaCinco = hr.cincoQuarentaCinco,

                seis = hr.seis,
                seisQuinze = hr.seisQuinze,
                seisTrinta = hr.seisTrinta,
                seisQuarentaCinco = hr.seisQuarentaCinco,

                sete = hr.sete,
                seteQuinze = hr.seteQuinze,
                seteTrinta = hr.seteTrinta,
                seteQuarentaCinco = hr.seteQuarentaCinco,

                oito = hr.oito,
                oitoQuinze = hr.oitoQuinze,
                oitoTrinta = hr.oitoTrinta,
                oitoQuarentaCinco = hr.oitoQuarentaCinco,

                nove = hr.nove,
                noveQuinze = hr.noveQuinze,
                noveTrinta = hr.noveTrinta,
                noveQuarentaCinco = hr.noveQuarentaCinco,

                dez = hr.dez,
                dezQuinze = hr.dezQuinze,
                dezTrinta = hr.dezTrinta,
                dezQuarentaCinco = hr.dezQuarentaCinco,

                onze = hr.onze,
                onzeQuinze = hr.onzeQuinze,
                onzeTrinta = hr.onzeTrinta,
                onzeQuarentaCinco = hr.onzeQuarentaCinco
            )
            repository.update(horaEntity)
        }
    }
}