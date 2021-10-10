package lipika.androidapp.gridlayoutadvisor

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.HomeProject
import api.HomeProjectItem
import api.RecommendationItem
import kotlinx.android.synthetic.main.activity_save.*
import kotlinx.android.synthetic.main.item_container_sp1.view.*
import kotlinx.android.synthetic.main.item_container_sp1.view.color_bar
import kotlinx.android.synthetic.main.project_detail.view.*

private const val REQUEST_CODE=101

class SaveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)
        val saveStorage = intent.getStringArrayListExtra("SAVEDLIST") as List<Array<String>>

        val top_fragment = TopFragment()
        supportFragmentManager.beginTransaction().replace(R.id.topContainerView, top_fragment).commit()

        saveRecyclerView.layoutManager = LinearLayoutManager(this)
        saveRecyclerView.adapter = ProjectAdapter(saveStorage)
    }

    private inner class View1Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var p_name: TextView = itemView.findViewById(R.id.nameSP1)
        var g_name: TextView = itemView.findViewById(R.id.grpSP1)
        var sem: TextView = itemView.findViewById(R.id.semSP1)
        var type: TextView = itemView.findViewById(R.id.SP1)

        lateinit var project: Array<String>

        fun bind(project: Array<String>){
            this.project = project
            p_name.text = (project[0])
            g_name.text = (project[1])
            sem.text = (project[2])
            type.text = ("Senior Project " + project[3])
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ProjectDetail::class.java)

                intent.putExtra("p_number", project.toString())
                intent.putExtra("TITLE", project[0])
                intent.putExtra("GRP",project[1])
                intent.putExtra("SEM", project[2])
                intent.putExtra("TYPE", project[3])
                Log.d("PINKY","Working ${project[0].toString()}")
                startActivityForResult(intent, REQUEST_CODE)
            }
        }
    }

    private inner class ProjectAdapter(var saved: List<Array<String>>):RecyclerView.Adapter<View1Holder>(){
        var seniorProject1=1
        var seniorProject2=2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View1Holder {
            val view = layoutInflater.inflate(R.layout.item_container_sp1,parent,false)

            view.color_bar.setBackgroundColor(if (viewType==seniorProject1) resources.getColor(R.color.SP1)
            else
                resources.getColor(R.color.SP2))

            //For Text
            view.SP1.setTextColor(if (viewType==seniorProject1) resources.getColor(R.color.SP1)
            else
                resources.getColor(R.color.SP2))

            return View1Holder(view)
        }

        override fun onBindViewHolder(holder: View1Holder, position: Int) {
            val savedpos =  saved[position]
            holder.apply {
                p_name.text = savedpos[0]
                g_name.text = savedpos[1]
                sem.text = savedpos[2]
                type.text = "Senior Project " + savedpos[3]
            }.bind(savedpos)
        }

        override fun getItemViewType(position: Int): Int {
            val savedpos = saved[position]
            return if(savedpos[3] == "1"){
                seniorProject1
            }else{
                seniorProject2
            }
        }


        override fun getItemCount(): Int {
            return saved.size
        }

    }
}