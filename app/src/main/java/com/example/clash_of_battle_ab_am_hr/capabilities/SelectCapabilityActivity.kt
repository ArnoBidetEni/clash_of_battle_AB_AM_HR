package com.example.clash_of_battle_ab_am_hr.capabilities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clash_of_battle_ab_am_hr.R
import com.example.clash_of_battle_ab_am_hr.models.Capability

class SelectCapabilityActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, index: Int) =
            Intent(context, SelectCapabilityActivity::class.java).apply {
                putExtra(SelectCapabilityFragment.CAPABILITY_INDEX_EXTRA, index)
            }

        fun extractResultData(intent: Intent): Pair<Int, Capability?> {
            val index = intent.getIntExtra(SelectCapabilityFragment.CAPABILITY_INDEX_EXTRA, 0)
            val selectedCapability = intent.getStringExtra(SelectCapabilityFragment.SELECTED_CAPABILITY_EXTRA)
            return index to selectedCapability?.let { Capability.valueOf(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_capability)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, SelectCapabilityFragment())
//                .commitNow()
//        }
    }
}