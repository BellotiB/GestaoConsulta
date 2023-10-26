package com.app.gestaoconsulta.Ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),LoadFragment {

    private lateinit var binding: ActivityMainBinding
    private val CHANNEL_ID = "my_channel_id"

     private lateinit var consultaViewModel: ConsultaViewModel
     private  var pedidoNotification = mutableListOf<PedidoAgendamento>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        consultaViewModel = ViewModelProvider(this)[ConsultaViewModel::class.java]
        createNotificationChannel()
        loadPedidoAgendamento()
    }
    private fun loadPedidoAgendamento() {
        lifecycleScope.launch {
            consultaViewModel?.pedidosNotification?.collectLatest { pedAgend ->
                if (pedAgend.size > 0 && pedAgend.size > pedidoNotification.size){
                    pedidoNotification.addAll(pedAgend)
                    showNotification("Pedido Agendamento", "Novo Pedido de agendamento realizados")
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "My Channel"
            val description = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
                this.description = description
            }

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_save) // Substitua pelo seu ícone de notificação
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(1, builder.build())
    }

    override fun openSecondFragment() {
        findNavController(R.id.nav_host).navigate(R.id.action_cadastroMedicoFragment_to_cadastroDataPorMedicoFragment)
    }

    override fun cadastroSelected(id: Int) {
      consultaViewModel.selectCadastroById(id)
    }

    override fun excluirItem(cad: CadastroMedico) {
        consultaViewModel.deleteCadastro(cad)
    }

    override fun excluirDataSelected(dat: DatasCadastradas) {
        consultaViewModel.deleteDataCadastrada(dat)
    }

    override fun openSelecionarDiasFragment(idDataCadastrada: String) {
        findNavController(R.id.nav_host).navigate(R.id.action_cadastroDataPorMedicoFragment_to_selecionarDiasFragment)
        consultaViewModel.setIdData(idDataCadastrada)
    }
}