package com.example.clash_of_battle_ab_am_hr.models

enum class Capability(val type: CapabilityType) {
    RISKY_ATTACK(CapabilityType.ATTACK),
    DOUBLE_ATTACK(CapabilityType.ATTACK),
    PRECISE_ATTACK(CapabilityType.ATTACK),

    RISKY_PARRY(CapabilityType.DEFENSE),
    DOUBLE_PARRY(CapabilityType.DEFENSE),
    PRECISE_PARRY(CapabilityType.DEFENSE),

    RISKY_HEAL(CapabilityType.HEAL),
    DOUBLE_HEAL(CapabilityType.HEAL),
    PRECISE_HEAL(CapabilityType.HEAL)
}

enum class CapabilityType {
    ATTACK,
    DEFENSE,
    HEAL
}
