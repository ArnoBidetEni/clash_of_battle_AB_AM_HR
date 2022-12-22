package com.example.clash_of_battle_ab_am_hr.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.clash_of_battle_ab_am_hr.models.Player

@Dao
interface PlayerDao {
    @Query("SELECT * FROM Player WHERE id = :id")
    suspend fun get(id : Long) : Player

    @Query("SELECT * FROM Player WHERE remoteId = :remote_id")
    suspend fun get(remote_id : String) : Player

    @Query("SELECT * FROM Player ORDER BY name")
    suspend fun get() : List<Player>

    @Query("SELECT * FROM Player")
    fun watchAll() : LiveData<List<Player>>

    @Update
    fun update(player: Player)




    @Insert
    suspend fun insertAll(trips: List<Player>)

    @Query("DELETE FROM Player")
    suspend fun clear()

    @Transaction
    suspend fun replace(trips: List<Player>) {
        clear()
        insertAll(trips)
    }
}