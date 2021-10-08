package com.example.coinstats.view.overview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinstats.R
import com.example.coinstats.databinding.FragmentOverviewBinding
import com.example.coinstats.utils.observe
import com.example.coinstats.utils.toast
import com.example.coinstats.utils.viewDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OverviewFragment : Fragment(R.layout.fragment_overview) {
    private val binding by viewDataBinding(FragmentOverviewBinding::bind)

    private val viewModel by viewModel<OverviewViewModel>()
    private val coinsAdapter by lazy {
        CoinsAdapter(viewModel::onAddFavorite)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listOverviews.layoutManager = LinearLayoutManager(requireActivity())
        binding.listOverviews.setHasFixedSize(true)
        binding.listOverviews.adapter = coinsAdapter

        observe(viewModel, onDataChanged = ::updateUI, onError = ::handleError)
    }

    private fun updateUI() {
        coinsAdapter.submitList(viewModel.coinsList)
    }

    private fun handleError(exception: Exception) {
        requireActivity().toast(getString(R.string.smth_went_wrong) + exception.localizedMessage)
    }
}