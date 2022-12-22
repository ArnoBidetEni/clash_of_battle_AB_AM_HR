package com.example.clash_of_battle_ab_am_hr.list_player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clash_of_battle_ab_am_hr.databinding.PlayerLineBinding
import com.example.clash_of_battle_ab_am_hr.models.Player
import com.example.clash_of_battle_ab_am_hr.utils.getPlayerJob
import com.example.clash_of_battle_ab_am_hr.utils.loadImage


class PlayerListAdapter(private val clickListener: (String) -> Unit): ListAdapter<Player, PlayerViewHolder>(PlayerDiffUtil()) {

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.create(parent)
    }
}

class PlayerViewHolder private constructor(private val binding: PlayerLineBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: (String) -> Unit, item: Player) {
        binding.playerName.text = item.name
        binding.playerClass.text =  getPlayerJob(item).name
        loadImage(binding.imageView, item.imageUrl)
        binding.root.setOnClickListener {
            //clickListener(item.remoteId)
        }
    }

    companion object {
        fun create(parent: ViewGroup): PlayerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PlayerLineBinding.inflate(layoutInflater, parent, false)
            return PlayerViewHolder(binding)
        }
    }
}

class PlayerDiffUtil : DiffUtil.ItemCallback<Player>() {

    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }
}
