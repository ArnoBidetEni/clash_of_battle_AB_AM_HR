package com.example.clash_of_battle_ab_am_hr.utils

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.example.clash_of_battle_ab_am_hr.R
import com.example.clash_of_battle_ab_am_hr.models.CapabilityType
import com.example.clash_of_battle_ab_am_hr.models.Job
import com.example.clash_of_battle_ab_am_hr.models.Player


fun getPlayerJob(player: Player) : Job {
    val nbAttack = player.capabilities.count { it.type == CapabilityType.ATTACK }
    val nbDefense = player.capabilities.count { it.type == CapabilityType.DEFENSE }
    val nbHeal = player.capabilities.count { it.type == CapabilityType.HEAL }

    return when {
        nbAttack > 1 -> Job.WARRIOR
        nbDefense > 1 -> Job.KNIGHT
        nbHeal > 1 -> Job.PRIEST
        else -> Job.BARD
    }
}

fun Job.getNameId() = when(this) {
    Job.WARRIOR -> R.string.job_warrior_name
    Job.KNIGHT -> R.string.job_knight_name
    Job.PRIEST -> R.string.job_priest_name
    Job.BARD -> R.string.job_bard_name
}

fun Job.getColor(context: Context): Int {
    val colorRes = when(this) {
        Job.WARRIOR -> R.color.red
        Job.KNIGHT -> R.color.blue
        Job.PRIEST -> R.color.green
        Job.BARD -> R.color.black
    }
    return ResourcesCompat.getColor(context.resources, colorRes, null)
}