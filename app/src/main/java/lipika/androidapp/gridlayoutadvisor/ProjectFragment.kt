package lipika.androidapp.gridlayoutadvisor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.AdvisorProjectResponse
import api.AdvisorProjectResponseItem
import api.AllApi
import kotlinx.android.synthetic.main.fragment_advisor_project.*
import kotlinx.android.synthetic.main.item_container_sp1.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val REQUEST_CODE11=100
private const val REQUEST_CODE22=101

private const val ARG_PARAM1 = "param1"

class ProjectFragment: Fragment(){
    var param1=""

    private lateinit var listAdapter: ProjectAdvisorAdapter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==Activity.RESULT_OK) {
            if(data != null){
                data.getStringArrayExtra("SAVED")?.let{(activity as HomeActivity).saveStorage.add(it)}
                Log.d("CHECK",data.getStringExtra("SAVED").toString())
                Log.d("CHECK",(activity as HomeActivity).saveStorage.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1).toString()
        }
    }

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
        listAdapter = ProjectAdvisorAdapter(emptyList())
        rv_SP1.adapter = listAdapter

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val Api: AllApi = retrofit.create(AllApi::class.java)


//        Get Project Description
        val getAdvisorRequest: Call<AdvisorProjectResponse> = Api.getAdvisorProject(param1.toString())
        Log.d("ADVISOR PROJECT",param1.toString())
        getAdvisorRequest.enqueue(object : Callback<AdvisorProjectResponse> {

            override fun onResponse(call: Call<AdvisorProjectResponse>, response: Response<AdvisorProjectResponse>) {
                var projectResponse = response.body()
                if (projectResponse!=null) {
                    listAdapter.setData(projectResponse)
                    Log.d("ADVISOR PROJECT", projectResponse.toString())
                }
            }

            override fun onFailure(call: Call<AdvisorProjectResponse>, t: Throwable) {
                Log.d("ADVISOR PROJECT","Failed to Request!")
            }
        })

    }


    private inner class AViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var p_name: TextView = itemView.findViewById(R.id.nameSP1)
        var g_name: TextView = itemView.findViewById(R.id.grpSP1)
        var sem: TextView = itemView.findViewById(R.id.semSP1)
        var type: TextView = itemView.findViewById(R.id.SP1)

        lateinit var project: AdvisorProjectResponseItem

        fun bind(project: AdvisorProjectResponseItem){
            this.project=project
            p_name.text = (project.projTitle)
            g_name.setText(project.projTeam)
            sem.setText(project.projSemester)
            type.text = ("Senior Project " + project.projType.toString())
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ProjectDetail::class.java)
                intent.putExtra("p_number", project.projNo.toString())
                intent.putExtra("TITLE", project.projType)
                intent.putExtra("GRP", project.projTeam)
                intent.putExtra("SEM", project.projSemester)
                intent.putExtra("TYPE", project.projType)
                startActivityForResult(intent, Activity.RESULT_OK)
            }
        }

    }

    private inner class ProjectAdvisorAdapter(var projects:List<AdvisorProjectResponseItem>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var seniorProject1 = 1
        var seniorProject2 = 2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val AView=layoutInflater.inflate(R.layout.item_container_sp1,parent,false)

            //For Text
            AView.SP1.setTextColor(if (viewType==seniorProject1) resources.getColor(R.color.SP1)
            else
                resources.getColor(R.color.SP2))

            //For color bar
            AView.color_bar.setBackgroundColor(if (viewType==seniorProject1) resources.getColor(R.color.SP1)
            else
                resources.getColor(R.color.SP2))

            return AViewHolder(AView)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val project = projects[position]
            (holder as AViewHolder).bind(project)

        }

        override fun getItemCount(): Int {
            return projects.size
        }

        override fun getItemViewType(position: Int): Int {
            val project = projects[position]
            return if (project.projType == 1) {
                seniorProject1
            } else {
                seniorProject2
            }
        }

        fun setData(project: List<AdvisorProjectResponseItem>){
            projects = project
            notifyDataSetChanged()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            ProjectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}

