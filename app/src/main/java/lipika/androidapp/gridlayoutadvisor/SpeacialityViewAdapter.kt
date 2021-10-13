package lipika.androidapp.gridlayoutadvisor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.AdvisorSpecialtyResponseItem
import api.HomeProjectItem


class SpeacialityViewAdapter(context: Context,var list: List<AdvisorSpecialtyResponseItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context:Context=context

    private inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var spec: TextView =itemView.findViewById(R.id.special)

        lateinit var advisorSpecialty: AdvisorSpecialtyResponseItem

        fun bind(advisorSpecialty:AdvisorSpecialtyResponseItem) {
            this.advisorSpecialty=advisorSpecialty
            spec.text=advisorSpecialty.advisorSpecialty.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.specialty, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val advisor=list[position]
        (holder as ViewHolder).bind(advisor)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(advisor: List<AdvisorSpecialtyResponseItem>) {
        list=advisor
        notifyDataSetChanged()
    }




}

