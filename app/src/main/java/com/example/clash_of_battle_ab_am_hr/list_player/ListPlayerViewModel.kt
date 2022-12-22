package com.example.clash_of_battle_ab_am_hr.list_player

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clash_of_battle_ab_am_hr.database.PlayerApi
import com.example.clash_of_battle_ab_am_hr.database.PlayerDao
import com.example.clash_of_battle_ab_am_hr.database.PlayerDatabase
import com.example.clash_of_battle_ab_am_hr.models.Player
import com.example.clash_of_battle_ab_am_hr.utils.toListOfPlayers
import kotlinx.coroutines.launch

class ListPlayerViewModel: ViewModel() {

    private val playerDAO: PlayerDao by lazy {
        PlayerDatabase.instance?.playerDao()
            ?: throw IllegalStateException("Database should be init at this point")
    }
    private val playerApi: PlayerApi by lazy { PlayerApi.service }


    val player: LiveData<List<Player>> = playerDAO.watchAll()


    fun refreshHeroes() {
        viewModelScope.launch {
            try {
                val remoteTripList: List<Player> = playerApi.players().toListOfPlayers()

                playerDAO.replace(remoteTripList)

            } catch(e: Exception) {
                Log.e("API ERROR", "Error while getting player from API", e)
            }
        }
    }
}