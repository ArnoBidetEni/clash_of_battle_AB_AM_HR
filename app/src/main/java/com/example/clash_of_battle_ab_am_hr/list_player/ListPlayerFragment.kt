package com.example.clash_of_battle_ab_am_hr.list_player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.clash_of_battle_ab_am_hr.R
import com.example.clash_of_battle_ab_am_hr.databinding.FragmentListPlayerBinding
import com.example.clash_of_battle_ab_am_hr.models.Player
import com.example.clash_of_battle_ab_am_hr.update_player.UpdatePlayerFragment
import com.example.clash_of_battle_ab_am_hr.utils.getPlayerJob
import com.example.clash_of_battle_ab_am_hr.utils.loadImage

class ListPlayerFragment : Fragment() {

    private var _binding: FragmentListPlayerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: ListPlayerViewModel

    private val adapter = PlayerListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListPlayerViewModel::class.java)
        viewModel.refreshPlayers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listPlayer.adapter = adapter

        viewModel.player.observe(viewLifecycleOwner) {
            setCurrentPlayer(it)
            adapter.submitList(it)
        }

        binding.currentPlayer.setOnClickListener{
            val currentPlayer = viewModel.currentPlayer
            val arguments = bundleOf(UpdatePlayerFragment.PLAYER_REMOTE_ID to currentPlayer?.remoteId)
            findNavController().navigate(R.id.action_listPlayerFragment_to_updatePlayerFragment, arguments)
        }

        binding.refresh.setOnClickListener {
            viewModel.refreshPlayers()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCurrentPlayer(players : List<Player>){
        viewModel.currentPlayer?.let{
            binding.currentPlayerName.text = it.name
            binding.currentPlayerClass.text = getPlayerJob(it).name
            loadImage(binding.currentPlayerImageView, it.imageUrl)
        }
    }
}