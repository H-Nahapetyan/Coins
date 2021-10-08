package com.example.coinstats.view.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstats.R
import com.example.coinstats.databinding.CoinListItemBinding
import com.example.coinstats.repo.local.dao.CoinDao
import com.example.coinstats.utils.viewBinding

class CoinsAdapter(
    val onAddFavorite: (coin: CoinDao) -> Unit,
) : ListAdapter<CoinDao, CoinsAdapter.CoinListItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListItemViewHolder {
        return CoinListItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.coin_list_item,
                parent,
                false
            )
        )
    }

    inner class CoinListItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val bindingItem by viewBinding(CoinListItemBinding::bind)

        fun bind(coin: CoinDao) = with(bindingItem) {
            bindingItem.textName.text = coin.name
            bindingItem.textPrice.text = coin.price.toString()
            bindingItem.constraintTop.setOnClickListener { onAddFavorite(coin) }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<CoinDao>() {

        override fun areItemsTheSame(
            oldItem: CoinDao,
            newItem: CoinDao
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CoinDao,
            newItem: CoinDao
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.price == newItem.price
        }

    }

    override fun onBindViewHolder(holder: CoinListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}