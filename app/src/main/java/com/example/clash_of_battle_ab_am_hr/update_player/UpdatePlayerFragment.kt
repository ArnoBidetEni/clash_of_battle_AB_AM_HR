package com.example.clash_of_battle_ab_am_hr.update_player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.clash_of_battle_ab_am_hr.R
import com.example.clash_of_battle_ab_am_hr.capabilities.SelectCapabilityActivity
import com.example.clash_of_battle_ab_am_hr.capabilities.SelectCapabilityFragment
import com.example.clash_of_battle_ab_am_hr.databinding.FragmentUpdatePlayerBinding
import com.example.clash_of_battle_ab_am_hr.models.Capability
import com.example.clash_of_battle_ab_am_hr.models.Player
import com.example.clash_of_battle_ab_am_hr.utils.getColor
import com.example.clash_of_battle_ab_am_hr.utils.getNameId
import com.example.clash_of_battle_ab_am_hr.utils.loadImage

class UpdatePlayerFragment : Fragment() {

    private lateinit var player_remote_id : String
    private var playerNewCapabilityIndex : Int? = null
    private var playerNewCapability : String? =null

    companion object {
        var PLAYER_REMOTE_ID : String = "PLAYER_REMOTE_ID"
        var PLAYER_NEW_CAPABILITY_INDEX : String = "PLAYER_NEW_CAPABILITY_INDEX"
        var PLAYER_NEW_CAPABILITY : String = "PLAYER_NEW_CAPABILITY"
    }

    private var _binding: FragmentUpdatePlayerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: UpdatePlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)
            .get(UpdatePlayerViewModel::class.java)
        player_remote_id = arguments?.getString(PLAYER_REMOTE_ID)!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdatePlayerBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    private val selectCapabilityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        it.data?.let{ intent ->
            val pair = SelectCapabilityActivity.extractResultData(intent)
            viewModel.updateCapability(pair.first, pair.second)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.buttonValidate.setEnabled(false)
        viewModel.existingPlayer.observe(viewLifecycleOwner){
            val player = it
            binding.playerNameInput.setText(it.name)

            binding.playerImgUrlInput.setText(it.imageUrl)
            loadImage(binding.playerImg, it.imageUrl)

            binding.buttonValidate.setEnabled(true)
            binding.buttonValidate.setOnClickListener{
                val player = Player(
                    player.id,
                    binding.playerNameInput.text.toString(),
                    binding.playerNameInput.text.toString(),
                    binding.playerImgUrlInput.text.toString(),
                    player.capability1,
                    player.capability2,
                    player.capability3,
                )
                viewModel.updatePlayer(player)
                findNavController().popBackStack()
            }

            onCapabilityClickedSetter(binding.capabilityButtonFirst, binding.capabilityLabelFirst, it.capability1, 1)
            onCapabilityClickedSetter(binding.capabilityButtonSecond, binding.capabilityLabelSecond, it.capability2,2)
            onCapabilityClickedSetter(binding.capabilityButtonThird, binding.capabilityLabelThird, it.capability3,3)
        }

        viewModel.initWithPlayer(player_remote_id)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onCapabilityClickedSetter(button : Button, textView : TextView, currentCapability : Capability, capabilityIndex : Int){

        textView.setText(currentCapability.getNameId())
        textView.setTextColor(currentCapability.getColor(button.context))

        button.setOnClickListener{
            selectCapabilityLauncher.launch(
                SelectCapabilityActivity.newIntent(
                    requireContext(),
                    capabilityIndex
                )
            )
        }
    }
}