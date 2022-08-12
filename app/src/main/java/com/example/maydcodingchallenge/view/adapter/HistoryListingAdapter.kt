package com.example.maydcodingchallenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.maydcodingchallenge.data.local.ShortLinkEntity
import com.example.maydcodingchallenge.databinding.ItemHistoryBinding
import org.w3c.dom.Entity

class HistoryListingAdapter(
    private val mDeleteClickListener: DeleteItemClickListener,
    private val mCopyItemClickListener: CopyItemClickListener
) :
    ListAdapter<ShortLinkEntity, RecyclerView.ViewHolder>(Companion) {

    inner class HistoryAdapterViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<ShortLinkEntity>() {
        override fun areItemsTheSame(
            oldItem: ShortLinkEntity,
            newItem: ShortLinkEntity
        ): Boolean = oldItem.code == newItem.code

        override fun areContentsTheSame(
            oldItem: ShortLinkEntity,
            newItem: ShortLinkEntity
        ): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(layoutInflater, parent, false)
        return HistoryAdapterViewHolder(binding)
    }

    class DeleteItemClickListener(val deleteClickListener: (mItem: ShortLinkEntity) -> Unit) {
        fun onClick(mItem: ShortLinkEntity) = deleteClickListener(mItem)
    }

    class CopyItemClickListener(val copyClickListener: (Int, ShortLinkEntity) -> Unit) {
        fun onClick(position: Int, data: ShortLinkEntity) = copyClickListener(position, data)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, itemPosition: Int) {
        val mItem = getItem(itemPosition)
        with(holder as HistoryAdapterViewHolder) {
            with(binding){
                data = mItem
                deleteClickListener = mDeleteClickListener
                copyItemClickListener = mCopyItemClickListener
                position = itemPosition
                executePendingBindings()
            }

        }
    }

    fun handleIsCopied(position: Int) {
        val list = currentList.toMutableList()
        list.forEachIndexed { index, element ->
            if (element.isCopied) {
                with(element) {
                    list[index] = ShortLinkEntity(
                        code = code,
                        shortLink = shortLink,
                        isCopied = false,
                        full_link = full_link
                    )
                }
            }
        }
        with(list[position]) {
            list[position] = ShortLinkEntity(
                code = code,
                shortLink = shortLink,
                isCopied = true,
                full_link = full_link
            )
        }
        submitList(list)
    }

}