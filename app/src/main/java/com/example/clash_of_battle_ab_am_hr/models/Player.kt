package com.example.clash_of_battle_ab_am_hr.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Player(
    @PrimaryKey(autoGenerate = true)
    @Json(ignore = true)
    val id: Long = 0L,

    @Json(ignore = true)
    val remoteId: String? = null,

    val name: String,
    @Json(name = "image_url")
    val imageUrl: String,

    val capability1: Capability,
    val capability2: Capability,
    val capability3: Capability,
) : Parcelable {
    val capabilities : List<Capability>
        get() = listOf(
            capability1,
            capability2,
            capability3
        )
}

enum class Job {
    WARRIOR,
    KNIGHT,
    PRIEST,
    BARD
}