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

    private val playerDao by lazy { PlayerDatabase.INSTANCE?.playerDao() }
    private val playerApi by lazy { PlayerApi.service }

    var current_id = 0L
    var current_remote_id = ""
    val existingPlayer = MutableLiveData<Player>()

    fun initWithPlayer(remote_id: String, id : Long) {
        viewModelScope.launch {
            current_id = id
            current_remote_id = remote_id
            existingPlayer.value = playerDao?.get(id)
        }
    }

    fun updatePlayer(newPlayer: Player) {
        existingPlayer.value?.let {
            val player = newPlayer.copy(it.id)
            viewModelScope.launch {
                playerApi.updatePlayer(current_remote_id,player)
            }
        }
    }
}