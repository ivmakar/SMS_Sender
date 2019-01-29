package ru.example.ivan.smssender.ui.rvadapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.example.ivan.smssender.databinding.RvItemGroupBinding
import ru.example.ivan.smssender.ui.uimodels.Group

class GroupRecyclerViewAdapter(private var items: ArrayList<Group>,
                               private var listener: OnItemClickListener)
    : RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemGroupBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<Group>) {
        items = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: RvItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ch: Group, listener: OnItemClickListener?) {
            binding.group = ch
            if (listener != null) {
                binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
            }

            binding.executePendingBindings()
        }
    }
}