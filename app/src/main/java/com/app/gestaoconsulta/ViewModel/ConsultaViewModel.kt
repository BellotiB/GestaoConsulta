package com.app.gestaoconsulta.ViewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gestaoconsulta.DataBase.Entities.AgendamentoPorUsuarioEntity
import com.app.gestaoconsulta.DataBase.Entities.CadastroEntity
import com.app.gestaoconsulta.DataBase.Entities.DataCadastradaEntity
import com.app.gestaoconsulta.DataBase.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.DataBase.Entities.PedidosAgendamentosEntity
import com.app.gestaoconsulta.DataBase.Entities.UsuarioEntity
import com.app.gestaoconsulta.Repository.Repository
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.Model.HorariosCadastrados
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.Usuarios
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    private var pedidosAgendamentoFlow = MutableStateFlow<MutableList<PedidoAgendamento>>(mutableListOf())
    val pedidosAgendamento: StateFlow<MutableList<PedidoAgendamento>> = pedidosAgendamentoFlow

    private var pedidosNotificationFlow = MutableStateFlow<MutableList<PedidoAgendamento>>(mutableListOf())
    val pedidosNotification: StateFlow<MutableList<PedidoAgendamento>> = pedidosNotificationFlow

    private var usuariosFlow = MutableStateFlow<MutableList<Usuarios>>(mutableListOf())
    val usuariosCadastrados: StateFlow<MutableList<Usuarios>> = usuariosFlow

    private var _usuarioSelecionado = MutableStateFlow(Usuarios())
    val usuarioSelecionado : StateFlow<Usuarios> = _usuarioSelecionado

    private var usuariosFromRoomDataBase = mutableListOf<UsuarioEntity>()

    val allAtendimentosPorUsuarios : Flow<MutableList<AgendamentoPorUsuarioEntity>> = repository.getAllPedidoPorUsuario
    val allCadastros : Flow<MutableList<CadastroEntity>> = repository.getAllCadastroEntity

    val allUsuarios : Flow<MutableList<UsuarioEntity>> = repository.getAllUsersEntity

    val allDatasCadastradas : Flow<MutableList<DataCadastradaEntity>> = repository.getAllDatasEntity

    val allHorasCadastradas : Flow<MutableList<HoraCadastradaEntity>> = repository.getAllHorasEntity

    val allPedidosAtendimento : Flow<MutableList<PedidosAgendamentosEntity>> = repository.getAllPedidosEntity

    var usuariosFromFirebase = mutableListOf<UsuarioEntity>()

    init {
        streamPedidosAgendamento()
        streamUsuario()
    }

    private fun streamPedidosAgendamento() {
        viewModelScope.launch {
           runCatching {
                flow {
                    while (true) {
                        emit(repository.getAgendamentos())
                        delay(1000)
                    }
                }
                    .flowOn(Dispatchers.IO)
                    .collectLatest { pedAgendamento ->
                        pedidosAgendamentoFlow.value =  pedAgendamento
                        pedidosNotificationFlow.value = pedAgendamento
                        updateHoraFromServer()
                        updateHorasCadastradosServer()
                    }
            }.onFailure { throwable ->
                println("Erro durante a execução: $throwable")
            }
        }
    }

    private fun streamUsuario() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                flow {
                    while (true) {
                        emit(repository.getUsersApi())
                        delay(5000)
                    }
                }
                    .flowOn(Dispatchers.IO)
                    .collectLatest { users ->
                        usuariosFlow.value = users
                        updateOrInsertUsuario()
                    }
            }.onFailure{ trowable->
                println("Erro durante a execução: $trowable")

            }
        }
    }

    private fun updateOrInsertUsuario() {
        viewModelScope.launch(Dispatchers.IO){
            allUsuarios.collectLatest {
                usuariosFromRoomDataBase.clear()
                usuariosFromRoomDataBase.addAll(it)

                if (usuariosFromRoomDataBase.isEmpty()) {
                    usuariosFromFirebase.clear()
                    usuariosCadastrados.value.forEach { user ->
                        val usuario = UsuarioEntity(
                            nome = user.nome,
                            id = user.id,
                            idUsuario = user.idUsuario,
                            email = user.email,
                            telefone = user.telefone,
                            cpf = user.cpf
                        )
                        usuariosFromFirebase.add(usuario)
                    }
                    repository.insertUsuarioList(usuariosFromFirebase)
                    usuariosCadastrados.value.clear()
                    usuariosFlow.value.clear()
                }else {
                    val usuariosNovos = usuariosCadastrados.value.filter { usuarioFromFireBase ->
                        usuariosFromRoomDataBase.none { usuarioDB -> usuarioDB.idUsuario == usuarioFromFireBase.idUsuario}
                    }

                    if (usuariosNovos.isNotEmpty()) {
                        usuariosFromFirebase.clear()
                        usuariosNovos.forEach { user ->
                            val usuario = UsuarioEntity(
                                nome = user.nome,
                                id = user.id,
                                idUsuario = user.idUsuario,
                                email = user.email,
                                telefone = user.telefone,
                                cpf = user.cpf
                            )
                            usuariosFromFirebase.add(usuario)
                        }
                        insertNovosUsuarios(usuariosFromFirebase)
                        usuariosNovos.toMutableList().clear()
                    }
                }
            }
        }
    }

    private fun insertNovosUsuarios(usuariosNovos: MutableList<UsuarioEntity>) {
        if (usuariosNovos.isNotEmpty()){
            viewModelScope.launch(Dispatchers.IO){
                repository.insertUsuarioList(usuariosNovos)
                usuariosNovos.clear()
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
    fun setCadastroSelected(cadastro: CadastroMedico) {
        _cadastroSelected.value = cadastro
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
        viewModelScope.launch(Dispatchers.IO) {
            allCadastros.collect{ list ->
                repository.setMedicos(list)
            }
        }
    }
    fun updateDatasCadastradasServer(){
        viewModelScope.launch(Dispatchers.IO) {
            allDatasCadastradas.collect{ datasList->
             repository.setDatas(datasList)
            }
        }
    }
    fun updateHorasCadastradosServer(){
        viewModelScope.launch (Dispatchers.IO){
            allHorasCadastradas.collect{ horasList->
                repository.setHoras(horasList)
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
               onzeQuarentaCinco = hr.onzeQuarentaCinco,

               doze = hr.doze,
               dozeQuinze = hr.dozeQuinze,
               dozeTrinta = hr.dozeTrinta,
               dozeQuarentaCinco = hr.dozeQuarentaCinco,

               treze = hr.treze,
               trezeQuinze = hr.trezeQuinze,
               trezeTrinta = hr.trezeTrinta,
               trezeQuarentaCinco = hr.trezeQuarentaCinco,

               quatorze = hr.quatorze,
               quatorzeQuinze = hr.quatorzeQuinze,
               quatorzeTrinta = hr.quatorzeTrinta,
               quatorzeQuarentaCinco = hr.quatorzeQuarentaCinco,

               quinze = hr.quinze,
               quinzeQuinze = hr.quinzeQuinze,
               quinzeTrinta = hr.quinzeTrinta,
               quinzeQuarentaCinco = hr.quinzeQuarentaCinco,

               dezesseis = hr.dezesseis,
               dezesseisQuinze = hr.dezesseisQuinze,
               dezesseisTrinta = hr.dezesseisTrinta,
               dezesseisQuarentaCinco = hr.dezesseisQuarentaCinco,

               dezessete = hr.dezessete,
               dezesseteQuinze = hr.dezesseteQuinze,
               dezesseteTrinta = hr.dezesseteTrinta,
               dezesseteQuarentaCinco = hr.dezesseteQuarentaCinco,

               dezoito = hr.dezoito,
               dezoitoQuinze = hr.dezoitoQuinze,
               dezoitoTrinta = hr.dezoitoTrinta,
               dezoitoQuarentaCinco = hr.dezoitoQuarentaCinco,

               dezenove = hr.dezenove,
               dezenoveQuinze = hr.dezenoveQuinze,
               dezenoveTrinta = hr.dezenoveTrinta,
               dezenoveQuarentaCinco = hr.dezenoveQuarentaCinco,
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
                onzeQuarentaCinco = hr.onzeQuarentaCinco,

                doze = hr.doze,
                dozeQuinze = hr.dozeQuinze,
                dozeTrinta = hr.dozeTrinta,
                dozeQuarentaCinco = hr.dozeQuarentaCinco,

                treze = hr.treze,
                trezeQuinze = hr.trezeQuinze,
                trezeTrinta = hr.trezeTrinta,
                trezeQuarentaCinco = hr.trezeQuarentaCinco,

                quatorze = hr.quatorze,
                quatorzeQuinze = hr.quatorzeQuinze,
                quatorzeTrinta = hr.quatorzeTrinta,
                quatorzeQuarentaCinco = hr.quatorzeQuarentaCinco,

                quinze = hr.quinze,
                quinzeQuinze = hr.quinzeQuinze,
                quinzeTrinta = hr.quinzeTrinta,
                quinzeQuarentaCinco = hr.quinzeQuarentaCinco,

                dezesseis = hr.dezesseis,
                dezesseisQuinze = hr.dezesseisQuinze,
                dezesseisTrinta = hr.dezesseisTrinta,
                dezesseisQuarentaCinco = hr.dezesseisQuarentaCinco,

                dezessete = hr.dezessete,
                dezesseteQuinze = hr.dezesseteQuinze,
                dezesseteTrinta = hr.dezesseteTrinta,
                dezesseteQuarentaCinco = hr.dezesseteQuarentaCinco,

                dezoito = hr.dezoito,
                dezoitoQuinze = hr.dezoitoQuinze,
                dezoitoTrinta = hr.dezoitoTrinta,
                dezoitoQuarentaCinco = hr.dezoitoQuarentaCinco,

                dezenove = hr.dezenove,
                dezenoveQuinze = hr.dezenoveQuinze,
                dezenoveTrinta = hr.dezenoveTrinta,
                dezenoveQuarentaCinco = hr.dezenoveQuarentaCinco,
            )
            repository.update(horaEntity)
        }
    }
    suspend fun setPedidoAgendamentoPorUsuario(agend: AgendamentoPorUsuarioEntity) {
        repository.insertPedidoAgendamento(agend)

    }

    fun setIdUsuario(idUsuario: String) {
        filtrarUsuarioPorId(idUsuario)
    }
    private fun filtrarUsuarioPorId(idUsuario: String) {
        viewModelScope.launch {
            allUsuarios.collectLatest {
                it.forEach {
                    if (it.idUsuario == idUsuario) {
                        val usuario = Usuarios()
                        usuario.idUsuario = it.idUsuario
                        usuario.id = it.id
                        usuario.nome = it.nome
                        usuario.email = it.email
                        usuario.telefone = it.telefone
                        usuario.cpf = it.cpf

                        _usuarioSelecionado.value = usuario
                    }
                }
            }
        }
    }
}