package com.example.clash_of_battle_ab_am_hr.ui.player_fight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.clash_of_battle_ab_am_hr.databinding.FragmentPlayerFightBinding
import com.example.clash_of_battle_ab_am_hr.models.Player
import com.example.clash_of_battle_ab_am_hr.utils.loadImage
import java.lang.Integer.max

class PlayerFightFragment : Fragment() {

    companion object {
        const val OPPONENT_ID_ARG = "OPPONENT_ID_ARG"
    }

    private lateinit var viewModel: PlayerFightViewModel

    private var binding: FragmentPlayerFightBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayerFightViewModel::class.java)

        val playerId = arguments?.getLong(OPPONENT_ID_ARG)
            ?: throw IllegalStateException("Should have an opponent id")

        viewModel.init(playerId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerFightBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = checkNotNull(binding)

        viewModel.myPlayer.observe(viewLifecycleOwner) {
            loadImage(binding.playerOne.firstPlayerImageView, it.imageUrl)
            binding.playerOne.firstPlayerName.text = it.name
        }

        viewModel.myOpponent.observe(viewLifecycleOwner) {
            loadImage(binding.playerTwo.secondPlayerImageView, it.imageUrl)
            binding.playerTwo.secondPlayerName.text = it.name
        }

        viewModel.myPlayerBattleInfo.observe(viewLifecycleOwner) {
            val pv = max(0, it.pv)
            binding.playerOne.firstPlayerProgressBar.progress = pv
            binding.playerOne.firstPlayerHealth.text = "$pv / 50"

            updateCapabilityButton(binding.fightButtonRiskyParade, it.remainingCapabilities.getOrNull(0))
            updateCapabilityButton(binding.fightButtonDoubleParade, it.remainingCapabilities.getOrNull(1))
            updateCapabilityButton(binding.fightButtonPreciseParade, it.remainingCapabilities.getOrNull(2))
        }

        viewModel.opponentBattleInfo.observe(viewLifecycleOwner) {
            val pv = max(0, it.pv)
            binding.playerTwo.secondPlayerProgressBar.progress = pv
            binding.playerTwo.secondPlayerHealth.text = "$pv / 50"
        }

        viewModel.roundCount.observe(viewLifecycleOwner) {
            binding.roundCountLabel.isVisible = it != 0
            binding.roundCountLabel.text = "Tour n°$it"
        }

        viewModel.lastPlayerResult.observe(viewLifecycleOwner) { actionResult ->
            // Donne le text à afficher
            binding.roundMyPlayerAction.text = getTextForActionResult(
                requireContext(),
                viewModel.myPlayerInfo.value!!,
                actionResult
            )
            // Donne une couleur au texte s'il s'agit d'une capacité
            actionResult.usedCapability?.let {
                binding.roundMyPlayerAction.setTextColor(it.getColor(requireContext()))
            }
        }

        viewModel.lastOpponentResult.observe(viewLifecycleOwner) { actionResult ->
            // Donne le text à afficher
            binding.roundOpponentAction.text = getTextForActionResult(
                requireContext(),
                viewModel.opponentInfo.value!!,
                actionResult
            )
            // Donne une couleur au texte s'il s'agit d'une capacité
            actionResult.usedCapability?.let {
                binding.roundOpponentAction.setTextColor(it.getColor(requireContext()))
            }
        }

        viewModel.winner.observe(viewLifecycleOwner) {
            binding.winnerLabel.isVisible = it != null
            binding.winnerLabel.text = "$it gagne !"
        }

        binding.simpleAttackButton.setOnClickListener {
            viewModel.attack()
        }
    }

    private fun updateCapabilityButton(button: Button, capability: Capability?) {
        button.isVisible = capability != null

        capability?.let { capa ->
            button.setText(capa.getNameId())
            button.setBackgroundColor(capa.getColor(requireContext()))
            button.setOnClickListener {
                viewModel.attack(capa)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}