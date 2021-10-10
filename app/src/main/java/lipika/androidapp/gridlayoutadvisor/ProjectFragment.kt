package lipika.androidapp.gridlayoutadvisor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_advisor_project.*

private const val REQUEST_CODE11=100
private const val REQUEST_CODE22=101

class ProjectFragment: Fragment(){

    private var list = sampleProject()
    private lateinit var listAdapter: ProjectFragment.ProjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_advisor_project, container, false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_SP1.layoutManager = LinearLayoutManager(activity)
        listAdapter = ProjectAdapter(list)
        rv_SP1.adapter = listAdapter

    }

    private fun sampleProject():ArrayList<Project> {
        val studentList = ArrayList<Project>()
        studentList.add(Project(2, "A case study of the indoor performance of Bluetooth Smart with Raspberry Pi 3", "The Final Fight", "2/2016", "Senior Project 1"))
        studentList.add(Project(2, "Using data analytics to predict student performance for recommended courses", "Reverse", "2/2016", "Senior Project 2"))
        studentList.add(Project(2, "Using data analytics to predict student performance for recommended courses", "Reverse", "2/2016", "Senior Project 2"))
        studentList.add(Project(1, "A case study of the indoor performance of Bluetooth Smart with Raspberry Pi 3", "The Final Fight", "2/2016", "Senior Project 1"))
        studentList.add(Project(1, "A case study of the indoor performance of Bluetooth Smart with Raspberry Pi 3", "The Final Fight", "2/2016", "Senior Project 1"))

        return studentList
    }

    private inner class View1Holder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var p_name: TextView = itemView.findViewById(R.id.nameSP1)
        var g_name: TextView = itemView.findViewById(R.id.grpSP1)
        var sem: TextView = itemView.findViewById(R.id.semSP1)
        var type: TextView = itemView.findViewById(R.id.SP1)

        lateinit var project: Project

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(project:Project){
            this.project=project
            p_name.text = (project.project_name)
            g_name.setText(project.group_name)
            sem.text = (project.semester)
            type.text = (project.project_no)
        }

        override fun onClick(v: View?) {
            val intent= Intent (v!!.context, ProjectDetail::class.java)
            intent.putExtra("p_name",project.project_name)
            intent.putExtra("g_name",project.group_name)
            intent.putExtra("sem",project.semester)
            intent.putExtra("type",project.viewType)

            startActivityForResult(intent, REQUEST_CODE11)
        }
    }

    private inner class View2Holder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var p_name: TextView = itemView.findViewById(R.id.nameSP2)
        var g_name: TextView = itemView.findViewById(R.id.grpSP2)
        var sem: TextView = itemView.findViewById(R.id.semSP2)
        var type: TextView = itemView.findViewById(R.id.SP2)

        lateinit var project: Project

        fun bind(project: Project){
            p_name.text = (project.project_name)
            g_name.setText(project.group_name)
            sem.text = (project.semester)
            type.text = (project.project_no)

        }

        override fun onClick(v: View?) {
            val intent= Intent(v!!.context,ProjectDetail::class.java)
            intent.putExtra("p_name",project.project_name)
            intent.putExtra("g_name",project.group_name)
            intent.putExtra("sem",project.semester)
            intent.putExtra("type",project.viewType)

            startActivityForResult(intent, REQUEST_CODE22)
        }
    }

    private inner class ProjectAdapter(var projects:ArrayList<Project>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var seniorProject1 = 1
        var seniorProject2 = 2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == seniorProject1) {
                val View1 = layoutInflater.inflate(R.layout.item_container_sp1, parent, false)
                return View1Holder(View1)
            } else {
                val View2 = layoutInflater.inflate(R.layout.item_container_sp2, parent, false)
                return View2Holder(View2)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val project = projects[position]
            if (project.viewType == 1) {
                (holder as ProjectFragment.View1Holder).bind(project)
            } else {
                (holder as ProjectFragment.View2Holder).bind(project)
            }

        }

        override fun getItemCount(): Int {
            return projects.size
        }

        override fun getItemViewType(position: Int): Int {
            val project = projects[position]
            return if (project.viewType == 1) {
                seniorProject1
            } else {
                seniorProject2
            }
        }


    }

}