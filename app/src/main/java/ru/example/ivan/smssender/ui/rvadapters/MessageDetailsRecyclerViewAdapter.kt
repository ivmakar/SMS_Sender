package ru.example.ivan.smssender.ui.rvadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.example.ivan.smssender.databinding.RvItemMessageDetailBinding
import ru.example.ivan.smssender.ui.uimodels.MessageDetail

class MessageDetailsRecyclerViewAdapter(private var items: ArrayList<MessageDetail>,
                                        private var listener: OnItemClickListener)
    : androidx.recyclerview.widget.RecyclerView.Adapter<MessageDetailsRecyclerViewAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemMessageDetailBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<MessageDetail>) {
        items = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: RvItemMessageDetailBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(md: MessageDetail, listener: OnItemClickListener?) {
            binding.messageDetail = md
            if (listener != null) {
                binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
            }

            binding.executePendingBindings()
        }
    }
}