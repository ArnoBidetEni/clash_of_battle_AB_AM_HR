package com.example.clash_of_battle_ab_am_hr.update_player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clash_of_battle_ab_am_hr.database.PlayerApi
import com.example.clash_of_battle_ab_am_hr.database.PlayerDao
import com.example.clash_of_battle_ab_am_hr.database.PlayerDatabase
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
                playerApi.updatePlayer(existingPlayer.value!!.name,player)
            }
        }
    }
}