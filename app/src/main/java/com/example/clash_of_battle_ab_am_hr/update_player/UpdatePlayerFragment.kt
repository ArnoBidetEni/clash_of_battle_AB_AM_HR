package com.example.clash_of_battle_ab_am_hr.update_player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.clash_of_battle_ab_am_hr.R
import com.example.clash_of_battle_ab_am_hr.databinding.FragmentUpdatePlayerBinding
import com.example.clash_of_battle_ab_am_hr.models.Player
import com.example.clash_of_battle_ab_am_hr.utils.getColor
import com.example.clash_of_battle_ab_am_hr.utils.loadImage

class UpdatePlayerFragment : Fragment() {

    private lateinit var player_remote_id : String

    companion object {
        var PLAYER_REMOTE_ID : String = "PLAYER_REMOTE_ID"
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.existingPlayer.observe(viewLifecycleOwner){
            binding.playerNameInput.setText(it.name)

            binding.playerImgUrlInput.setText(it.imageUrl)
            loadImage(binding.playerImg, it.imageUrl)

            binding.capabilityLabelFirst.text = it.capability1.name
            binding.capabilityLabelFirst.setTextColor(it.capability1.getColor(binding.capabilityLabelFirst.context))
            binding.capabilityLabelSecond.text = it.capability2.name
            binding.capabilityLabelSecond.setTextColor(it.capability1.getColor(binding.capabilityLabelSecond.context))
            binding.capabilityLabelThird.text = it.capability3.name
            binding.capabilityLabelThird.setTextColor(it.capability1.getColor(binding.capabilityLabelThird.context))


        }

        viewModel.initWithPlayer(player_remote_id)


        binding.capabilityButtonFirst.setOnClickListener{
            findNavController().navigate(R.id.action_updatePlayerFragment_to_selectCapabilityActivity)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}