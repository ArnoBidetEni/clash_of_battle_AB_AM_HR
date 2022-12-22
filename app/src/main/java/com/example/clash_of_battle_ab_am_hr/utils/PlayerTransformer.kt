package com.example.clash_of_battle_ab_am_hr.utils

import com.example.clash_of_battle_ab_am_hr.models.Player

fun Map<String, Player>.toListOfPlayers() = entries.map {
    it.value.copy(remoteId = it.key)
}