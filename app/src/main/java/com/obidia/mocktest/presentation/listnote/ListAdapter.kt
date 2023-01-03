package com.obidia.mocktest.presentation.listnote

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.obidia.mocktest.databinding.ItemNoteBinding
import com.obidia.mocktest.domain.entity.ProductDaoEntity


class ListAdapter(private val onClick: OnClick) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ProductDaoEntity>() {
        override fun areItemsTheSame(
            oldItem: ProductDaoEntity,
            newItem: ProductDaoEntity
        ): Boolean =
            oldItem.idProduct == newItem.idProduct


        override fun areContentsTheSame(
            oldItem: ProductDaoEntity,
            newItem: ProductDaoEntity
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()


    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: MutableList<ProductDaoEntity>) = differ.submitList(value)

    class ListViewHolder(private var binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: ProductDaoEntity) {
            binding.dataEntity = data
            binding.stok.text = "${data.stokBarang} Stok"
            binding.executePendingBindings()
        }
    }

    class OnClick(val click: (entity: ProductDaoEntity?) -> Unit) {
        fun onClick(entity: ProductDaoEntity?) = click(entity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = differ.currentList[position] as ProductDaoEntity
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClick.onClick(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}