package ru.example.ivan.smssender.ui.rvadapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.example.ivan.smssender.databinding.RvItemMessageSendBinding
import ru.example.ivan.smssender.ui.uimodels.Message

class MessageRecyclerViewAdapter(private var items: ArrayList<Message>,
                                 private var listener: OnItemClickListener)
    : RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemMessageSendBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<Message>) {
        items = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: RvItemMessageSendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ch: Message, listener: OnItemClickListener?) {
            binding.message = ch
            if (listener != null) {
                binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
            }

            binding.executePendingBindings()
        }
    }
}