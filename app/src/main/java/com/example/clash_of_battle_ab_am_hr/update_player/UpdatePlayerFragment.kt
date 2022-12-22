package com.example.clash_of_battle_ab_am_hr.update_player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.clash_of_battle_ab_am_hr.R
import com.example.clash_of_battle_ab_am_hr.databinding.FragmentUpdatePlayerBinding

class UpdatePlayerFragment : Fragment() {


    companion object {
        const val PLAYER_ID_ARG = "PLAYER_ID__ARG"
        const val PLAYER_REMOTE_ID_ARG = "PLAYER_REMOTE_ID_ARG"
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

        val remote_id = arguments?.getString(PLAYER_ID_ARG)
        val id = arguments?.getLong(PLAYER_ID_ARG)
        id?.let {
            viewModel.initWithPlayer(remote_id?:"", id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_player, container, false)
    }
}