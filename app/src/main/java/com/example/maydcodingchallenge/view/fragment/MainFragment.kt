package com.example.maydcodingchallenge.view.fragment

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.maydcodingchallenge.MainActivity
import com.example.maydcodingchallenge.R
import com.example.maydcodingchallenge.data.local.ShortLinkEntity
import com.example.maydcodingchallenge.databinding.FragmentMainBinding
import com.example.maydcodingchallenge.utils.copyToClipboard
import com.example.maydcodingchallenge.utils.showToast
import com.example.maydcodingchallenge.view.adapter.HistoryListingAdapter
import com.example.maydcodingchallenge.view.base.BaseFragment
import com.example.maydcodingchallenge.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate
) {
    private lateinit var mAdapter: HistoryListingAdapter

    override val viewModel: MainViewModel by activityViewModels()

    override fun initView(binding: FragmentMainBinding, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        initRecyclerView()
        initAdapter()
        viewModel.getHistory()
    }

    private fun initAdapter() {
        binding.historyLayout.rvHistoryList.adapter = mAdapter
    }

    private fun initRecyclerView() {
        mAdapter = HistoryListingAdapter(onDelete(), onCopy())
        binding.historyLayout.rvHistoryList.adapter = mAdapter
    }

    private fun onDelete() = HistoryListingAdapter.DeleteItemClickListener {
        viewModel.deleteItemFromHistory(it)
    }

    private fun onCopy() = HistoryListingAdapter.CopyItemClickListener { position, data ->
        mAdapter.handleIsCopied(position)
        context?.copyToClipboard(data.shortLink)
    }

    override fun observeViewModel(viewModel: MainViewModel) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksEvent.collect { eventState ->
                when (eventState) {

                    is MainViewModel.URLsEvent.Loading -> {
                        showLoading()
                    }

                    is MainViewModel.URLsEvent.Error -> {
                        hideLoading()
                        showToast(requireContext(), eventState.errorData.error.toString())
                    }

                    MainViewModel.URLsEvent.RemoteErrorByNetwork -> {
                        hideLoading()
                        showToast(requireContext(), "Network Error")
                    }

                    is MainViewModel.URLsEvent.GetHistory -> {
                      inflateViewWithHistory(eventState)
                    }

                    is MainViewModel.URLsEvent.URLDeleted -> {
                      historyAfterDeleteOperation(eventState)
                    }
                }
            }
        }
    }

    private fun inflateViewWithHistory(eventState: MainViewModel.URLsEvent.GetHistory) {
        hideLoading()
        binding.includeBottomCcontainer.etShortenLink.text?.clear()
        if (eventState.list.isEmpty()) {
            manageUIState(viewNumber = 1, list = null)
        } else {
            manageUIState(viewNumber = 2, list = eventState.list.toMutableList())
            with(binding.historyLayout.rvHistoryList) {
                adapter?.let { it -> smoothScrollToPosition(it.itemCount) };
            }
        }
    }

    private fun historyAfterDeleteOperation(eventState: MainViewModel.URLsEvent.URLDeleted) {
        if (eventState.list.isEmpty()) {
            manageUIState(viewNumber = 1, list = null)
        } else {
            manageUIState(viewNumber = 2, list = eventState.list.toMutableList())
        }
        hideLoading()
        showToast(requireContext(), getString(R.string.url_deleted))
    }

    private fun manageUIState(viewNumber: Int, list: List<ShortLinkEntity>?) {
        binding.viewFlipper.displayedChild = viewNumber
        mAdapter.submitList(list)
    }

    private fun hideLoading() {
        (activity as MainActivity).hideLoading()
    }

    private fun showLoading() {
        (activity as MainActivity).showLoading()
    }

}