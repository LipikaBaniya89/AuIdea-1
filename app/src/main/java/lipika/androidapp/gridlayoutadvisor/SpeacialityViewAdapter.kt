package lipika.androidapp.gridlayoutadvisor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import api.HomeProjectItem
import api.Specialty


class SpeacialityViewAdapter(context: Context, var list: List<Specialty>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context: Context = context

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var spec: TextView =itemView.findViewById(R.id.special)

        lateinit var advisorSpecialty: Specialty

        fun bind(advisorSpecialty: Specialty) {
            this.advisorSpecialty=advisorSpecialty
            spec.text=advisorSpecialty.specialty

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.specialty,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val advisor=list[position]
        (holder as ViewHolder).bind(advisor)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setData(advisor: List<Specialty>) {
        list=advisor
        notifyDataSetChanged()
    }
}






