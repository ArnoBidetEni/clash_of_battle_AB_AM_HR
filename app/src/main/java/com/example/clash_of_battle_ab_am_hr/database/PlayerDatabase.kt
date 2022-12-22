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

        @Volatile
        var instance: PlayerDatabase? = null
            private set

        fun init(context: Context) {
            synchronized(this) {
                this.instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDatabase::class.java,
                    "tripDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}

