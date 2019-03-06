package ru.example.ivan.smssender.ui.rvadapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.example.ivan.smssender.databinding.RvItemContactBinding
import ru.example.ivan.smssender.ui.uimodels.Contact

class ContactRecyclerViewAdapter(private var items: ArrayList<Contact>,
                                 private var listener: OnItemClickListener)
    : RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemContactBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)

//        holder.itemView.isActivated = items[position].isSelected
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<Contact>) {
        items = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: RvItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ct: Contact, listener: OnItemClickListener?) {
            binding.contact = ct
            if (listener != null) {
                binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
            }

            binding.executePendingBindings()
        }
    }
}