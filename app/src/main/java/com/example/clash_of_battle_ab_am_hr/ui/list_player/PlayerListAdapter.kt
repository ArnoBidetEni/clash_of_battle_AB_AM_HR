package com.example.clash_of_battle_ab_am_hr.ui.list_player

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clash_of_battle_ab_am_hr.R
import com.example.clash_of_battle_ab_am_hr.databinding.PlayerLineBinding
import com.example.clash_of_battle_ab_am_hr.models.Player
import com.example.clash_of_battle_ab_am_hr.utils.getColor
import com.example.clash_of_battle_ab_am_hr.utils.getNameId
import com.example.clash_of_battle_ab_am_hr.utils.getPlayerJob
import com.example.clash_of_battle_ab_am_hr.utils.loadImage

class PlayerListAdapter : ListAdapter<Player, PlayerViewHolder>(PlayerDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PlayerViewHolder private constructor(private val binding: PlayerLineBinding)
    : RecyclerView.ViewHolder(binding.root) {


    companion object {
        fun create(parent: ViewGroup): PlayerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PlayerLineBinding.inflate(layoutInflater, parent, false)
            return PlayerViewHolder(binding)
        }
    }

    fun bind(item: Player) {
        binding.playerName.text = item.name;
        binding.playerClass.text =  getPlayerJob(item).name;
        binding.playerClass.setText(getPlayerJob(item).getNameId());

        loadImage(binding.imageView, item.imageUrl)
        binding.playerClass.setTextColor(getPlayerJob(item).getColor(binding.imageView.context))
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
