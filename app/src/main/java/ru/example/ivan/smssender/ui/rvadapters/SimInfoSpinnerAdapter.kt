package ru.example.ivan.smssender.ui.rvadapters


import android.content.Context
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.BaseAdapter
import android.widget.ImageView
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.ui.uimodels.SimInfo


class SimInfoSpinnerAdapter (
    var context: Context,
    var simInfoList: ArrayList<SimInfo>
) : BaseAdapter(){

    var inflter: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return simInfoList.size
    }

    override fun getItem(i: Int): SimInfo {
        return simInfoList[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        view = inflter.inflate(R.layout.item_sim_list, null)
        val icon = view.findViewById(R.id.sim_image_view) as ImageView
        val name = view.findViewById(R.id.sim_name_text_view) as TextView

        icon.setImageBitmap(simInfoList[i].icon)
        name.text = simInfoList[i].simName

        return view
    }

}