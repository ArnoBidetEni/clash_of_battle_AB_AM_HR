package com.example.clash_of_battle_ab_am_hr.capabilities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clash_of_battle_ab_am_hr.databinding.FragmentSelectCapabilityBinding
import com.example.clash_of_battle_ab_am_hr.models.Capability

class SelectCapabilityFragment : Fragment() {

    companion object {
        const val SELECTED_CAPABILITY_EXTRA = "SELECTED_CAPABILITY_EXTRA"
        const val CAPABILITY_INDEX_EXTRA = "CAPABILITY_INDEX_EXTRA"
    }

    private var binding: FragmentSelectCapabilityBinding? = null

    private lateinit var adapter: SelectCapabilityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectCapabilityBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SelectCapabilityAdapter {
            onCapabilitySelected(it)
        }
        binding?.selectCapabilityRecyclerView?.adapter = adapter
        adapter.submitList(Capability.values().toList())
    }

    private fun onCapabilitySelected(capability: Capability) {
        val intentResult = Intent()
        val index = requireActivity().intent.getIntExtra(CAPABILITY_INDEX_EXTRA, 0)
        intentResult.putExtra(CAPABILITY_INDEX_EXTRA, index)
        intentResult.putExtra(SELECTED_CAPABILITY_EXTRA, capability.name)
        requireActivity().setResult(Activity.RESULT_OK, intentResult)
        requireActivity().finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}