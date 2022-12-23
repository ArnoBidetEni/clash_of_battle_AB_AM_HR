package com.example.clash_of_battle_ab_am_hr.ui.update_player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clash_of_battle_ab_am_hr.api.PlayerApi
import com.example.clash_of_battle_ab_am_hr.database.PlayerDatabase
import com.example.clash_of_battle_ab_am_hr.models.Capability
import com.example.clash_of_battle_ab_am_hr.models.Player
import kotlinx.coroutines.launch

class UpdatePlayerViewModel: ViewModel()  {

    private val playerDao by lazy { PlayerDatabase.instance?.playerDao() }
    private val playerApi by lazy { PlayerApi.service }

    val existingPlayer = MutableLiveData<Player>()

    private var remote_id = ""

    fun initWithPlayer(id : String){
        viewModelScope.launch {
            remote_id = id
            existingPlayer.value = playerDao?.get(remote_id)
        }

    }

    fun updatePlayer(newPlayer: Player) {
        existingPlayer.value?.let {
            val player = newPlayer.copy(it.id)
            viewModelScope.launch {
                playerApi.updatePlayer(existingPlayer.value!!.remoteId!!,player)
            }
        }
    }

    fun updateCapability(index : Int, capability : Capability?){
        capability?.let {
            when(index){
                1-> existingPlayer.value?.capability1 = capability
                2-> existingPlayer.value?.capability2 = capability
                else-> existingPlayer.value?.capability3 = capability
            }
        }
        existingPlayer.value = existingPlayer.value
    }
}