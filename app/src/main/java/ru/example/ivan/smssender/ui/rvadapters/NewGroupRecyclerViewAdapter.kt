package ru.example.ivan.smssender.ui.rvadapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.example.ivan.smssender.databinding.RvNgItemContactBinding
import ru.example.ivan.smssender.ui.uimodels.Contact

class NewGroupRecyclerViewAdapter(private var items: ArrayList<Contact>,
                                  private var itemsInd: Set<Int>,
                                  private var listener: OnItemClickListener)
    : RecyclerView.Adapter<NewGroupRecyclerViewAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvNgItemContactBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[itemsInd.elementAt(position)], listener)

    override fun getItemCount(): Int = itemsInd.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(items: ArrayList<Contact>, newItems: Set<Int>) {
        this.items = items
        if (itemsInd.isEmpty()){
            itemsInd = newItems
            return
        }
        var i = 0
        var cur = 0
        while (i < itemsInd.size){
            cur = itemsInd.elementAt(i)
            if (!newItems.contains(cur)){
                itemsInd = itemsInd.minus(cur)
                notifyItemRemoved(i)
                i--
            }
            i++
        }

        for (i in newItems){
            if (!itemsInd.contains(i)){
                itemsInd = itemsInd.plus(i)
                notifyItemInserted(itemsInd.size - 1)
            }
        }
    }

    class ViewHolder(private var binding: RvNgItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ch: Contact, listener: OnItemClickListener?) {
            binding.contact = ch
            if (listener != null) {
                binding.root.setOnClickListener { _ -> listener.onItemClick(layoutPosition) }
            }

            binding.executePendingBindings()
        }
    }
}