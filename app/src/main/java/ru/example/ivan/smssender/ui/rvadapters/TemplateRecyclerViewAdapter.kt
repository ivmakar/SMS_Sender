package ru.example.ivan.smssender.ui.rvadapters
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.example.ivan.smssender.databinding.RvItemChainBinding
import ru.example.ivan.smssender.databinding.RvItemTemplateBinding
import ru.example.ivan.smssender.ui.uimodels.Chain
import ru.example.ivan.smssender.ui.uimodels.Template

class TemplateRecyclerViewAdapter(private var items: ArrayList<Template>,
                               private var listener: OnItemClickListener)
    : androidx.recyclerview.widget.RecyclerView.Adapter<TemplateRecyclerViewAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemTemplateBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<Template>) {
        items = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: RvItemTemplateBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(tmp: Template, listener: OnItemClickListener?) {
            binding.template = tmp
            if (listener != null) {
                binding.root.setOnClickListener { _ -> listener.onItemClick(layoutPosition) }
            }

            binding.executePendingBindings()
        }
    }
}