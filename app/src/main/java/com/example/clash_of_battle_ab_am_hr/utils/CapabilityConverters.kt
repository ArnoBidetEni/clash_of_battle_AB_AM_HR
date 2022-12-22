package com.example.clash_of_battle_ab_am_hr.utils

import androidx.room.TypeConverter
import com.example.clash_of_battle_ab_am_hr.models.Capability
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson


// Needed for Room
class CapabilityRoomConverter {

    @TypeConverter
    fun fromCapability(value: String?): Capability? {
        return value?.let { Capability.valueOf(it) }
    }

    @TypeConverter
    fun toCapability(capability: Capability?): String? {
        return capability?.name
    }
}

class CapabilityMoshiConverter {

    @FromJson
    fun fromCapability(value: String?): Capability? {
        return value?.let { Capability.valueOf(it) }
    }

    @ToJson
    fun toCapability(capabilities: Capability?): String? {
        return capabilities?.name
    }
}