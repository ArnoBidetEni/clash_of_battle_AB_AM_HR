package com.example.clash_of_battle_ab_am_hr.database

import androidx.room.*
import com.example.clash_of_battle_ab_am_hr.models.Player

@Dao
interface PlayerDao {
    @Query("SELECT * FROM Player WHERE id = :id")
    suspend fun get(id : Long) : Player

    @Query("SELECT * FROM Player ORDER BY name")
    suspend fun get() : List<Player>

    @Update
    fun update(player: Player)
}