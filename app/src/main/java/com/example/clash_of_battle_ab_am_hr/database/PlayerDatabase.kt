package com.example.clash_of_battle_ab_am_hr.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.clash_of_battle_ab_am_hr.models.Player
import com.example.clash_of_battle_ab_am_hr.utils.CapabilityRoomConverter

@Database(entities = [Player::class], version = 1)
@TypeConverters(CapabilityRoomConverter::class)
abstract class PlayerDatabase: RoomDatabase() {
    abstract fun playerDao(): PlayerDao

    companion object {
        var INSTANCE: PlayerDatabase? = null

        fun getDatabase(context: Context): PlayerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDatabase::class.java,
                    "player_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

