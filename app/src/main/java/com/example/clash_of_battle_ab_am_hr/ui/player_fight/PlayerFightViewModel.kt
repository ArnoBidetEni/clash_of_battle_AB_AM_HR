package com.example.clash_of_battle_ab_am_hr.ui.player_fight

import Env
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clash_of_battle_ab_am_hr.battle.engine.BattleEngine
import com.example.clash_of_battle_ab_am_hr.battle.engine.PlayerBattleInfo
import com.example.clash_of_battle_ab_am_hr.battle.engine.RandomGeneratorImpl
import com.example.clash_of_battle_ab_am_hr.database.PlayerDao
import com.example.clash_of_battle_ab_am_hr.database.PlayerDatabase
import com.example.clash_of_battle_ab_am_hr.models.Capability
import com.example.clash_of_battle_ab_am_hr.models.Player
import com.example.democlashofbattle.battle.engine.ActionResult
import kotlinx.coroutines.launch
import kotlin.math.max

class PlayerFightViewModel: ViewModel() {

    private val playerDAO: PlayerDao by lazy {
        PlayerDatabase.instance?.playerDao()
            ?: throw IllegalStateException("Database should be init at this point")
    }

    private val battleEngine = BattleEngine(RandomGeneratorImpl());

    val myPlayer = MutableLiveData<Player>();
    val myOpponent = MutableLiveData<Player>();

    val myPlayerBattleInfo = MutableLiveData<PlayerBattleInfo>();
    val opponentBattleInfo = MutableLiveData<PlayerBattleInfo>();
    
    val roundCount = MutableLiveData(0);

    val lastPlayerResult = MutableLiveData<ActionResult>();
    val lastOpponentResult = MutableLiveData<ActionResult>();

    val winner = MutableLiveData<String>();

    fun init(opponentId: Long) {
        viewModelScope.launch {
            val player = playerDAO.get(Env.current_user);
            val opponent = playerDAO.get(opponentId);

            myPlayer.value = player;
            myOpponent.value = opponent;

            myPlayerBattleInfo.value = PlayerBattleInfo(player.capabilities);
            opponentBattleInfo.value = PlayerBattleInfo(opponent.capabilities);
        }
    }

    fun attack(capability: Capability? = null) {
        val player = myPlayerBattleInfo.value!!;
        val opponent = opponentBattleInfo.value!!;

        val result = battleEngine.attack(opponent, capability)
        val playerResult = result.first;
        val opponentResult = result.second;

        myPlayerBattleInfo.value = updatePlayer(player, playerResult, opponentResult)

        opponentBattleInfo.value = updatePlayer(opponent, opponentResult, playerResult)

        lastPlayerResult.value = playerResult
        lastOpponentResult.value = opponentResult

        roundCount.value = roundCount.value!! + 1

        if (myPlayerBattleInfo.value!!.pv <= 0)
        {
            winner.value = myOpponent.value!!.name

        } else if(opponentBattleInfo.value!!.pv <= 0) {
            winner.value = myPlayer.value!!.name
        }
    }

    private fun updatePlayer(
        player: PlayerBattleInfo,
        playerResult: ActionResult,
        opponentResult: ActionResult
    ): PlayerBattleInfo {

        var newPlayer = player

        val realDamage = max(0, opponentResult.damage - playerResult.defense)

        val heal = playerResult.heal

        playerResult.usedCapability?.let {
            val newCapabilitiesList = player.remainingCapabilities.toMutableList()

            newCapabilitiesList.remove(it)

            newPlayer = newPlayer.copy(newCapabilitiesList)
        }

        val modifiedHP = newPlayer.pv - realDamage + heal

        val hp = Integer.min(50, Integer.max(0, modifiedHP))

        return newPlayer.copy(pv = hp)

    }
}