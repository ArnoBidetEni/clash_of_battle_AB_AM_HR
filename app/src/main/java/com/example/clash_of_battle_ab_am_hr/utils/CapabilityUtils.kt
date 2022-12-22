package com.example.clash_of_battle_ab_am_hr.utils

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.example.clash_of_battle_ab_am_hr.R
import com.example.clash_of_battle_ab_am_hr.models.Capability
import com.example.clash_of_battle_ab_am_hr.models.CapabilityType


fun Capability.getNameId() = when(this) {
    Capability.RISKY_ATTACK -> R.string.capability_risky_attack_name
    Capability.DOUBLE_ATTACK -> R.string.capability_double_attack_name
    Capability.PRECISE_ATTACK -> R.string.capability_precise_attack_name
    Capability.RISKY_PARRY -> R.string.capability_risky_parry_name
    Capability.DOUBLE_PARRY -> R.string.capability_double_parry_name
    Capability.PRECISE_PARRY -> R.string.capability_precise_parry_name
    Capability.RISKY_HEAL -> R.string.capability_risky_heal_name
    Capability.DOUBLE_HEAL -> R.string.capability_double_heal_name
    Capability.PRECISE_HEAL -> R.string.capability_precise_heal_name
}

fun Capability.getDescriptionId() = when(this) {
    Capability.RISKY_ATTACK -> R.string.capability_risky_attack_description
    Capability.DOUBLE_ATTACK -> R.string.capability_double_attack_description
    Capability.PRECISE_ATTACK -> R.string.capability_precise_attack_description
    Capability.RISKY_PARRY -> R.string.capability_risky_parry_description
    Capability.DOUBLE_PARRY -> R.string.capability_double_parry_description
    Capability.PRECISE_PARRY -> R.string.capability_precise_parry_description
    Capability.RISKY_HEAL -> R.string.capability_risky_heal_description
    Capability.DOUBLE_HEAL -> R.string.capability_double_heal_description
    Capability.PRECISE_HEAL -> R.string.capability_precise_heal_description
}

fun Capability.getColor(context: Context): Int {
    val colorRes = when(this.type) {
        CapabilityType.ATTACK -> R.color.red
        CapabilityType.DEFENSE -> R.color.blue
        CapabilityType.HEAL -> R.color.green
    }
    return ResourcesCompat.getColor(context.resources, colorRes, null)
}
