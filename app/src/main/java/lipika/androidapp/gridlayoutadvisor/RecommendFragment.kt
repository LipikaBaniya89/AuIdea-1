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
import api.AllApi
import api.Recommendation
import api.RecommendationItem
import kotlinx.android.synthetic.main.activity_recommend.saveRecyclerView
import kotlinx.android.synthetic.main.item_container_sp1.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val REQUEST_CODE=102
private const val REQUEST_CODE1=103

class RecommendFragment : Fragment() {

    private var list: Recommendation = Recommendation()
    private lateinit var listAdapter: ProjectAdapter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==Activity.RESULT_OK) {
            if(data != null){
                data.getStringArrayExtra("SAVED")?.let{(activity as HomeActivity).saveStorage.add(it)}
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_recommend,container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        saveRecyclerView.layoutManager = LinearLayoutManager(activity)
        listAdapter = ProjectAdapter(list)
        saveRecyclerView.adapter = listAdapter


        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val Api: AllApi = retrofit.create(AllApi::class.java)

//        Get Recommendation HomeScreen
        val getRecommendationRequest: Call<Recommendation> = Api.getRecommendation()
        getRecommendationRequest.enqueue(object : Callback<Recommendation> {

            override fun onResponse(
                call: Call<Recommendation>,
                response: Response<Recommendation>
            ) {
                var recommendationResponse = response.body()
                if (recommendationResponse != null) {
                    listAdapter.setData(recommendationResponse)
                    Log.d("SPARK-API", recommendationResponse.toString())
                }
            }

            override fun onFailure(call: Call<Recommendation>, t: Throwable) {
                Log.d("SPARK-API", "Failed to Request!")
            }
        })

    }


    private inner class View1Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var p_name: TextView = itemView.findViewById(R.id.nameSP1)
        var g_name: TextView = itemView.findViewById(R.id.grpSP1)
        var sem: TextView = itemView.findViewById(R.id.semSP1)
        var type: TextView = itemView.findViewById(R.id.SP1)

        lateinit var project: RecommendationItem

        fun bind(project: RecommendationItem){
            this.project=project
            p_name.text = (project.projectTitle)
            g_name.setText(project.groupName)
            sem.text = (project.semester)
            type.text = ("Senior Project " + project.projectType.toString())
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ProjectDetail::class.java)
                intent.putExtra("p_number", project.projectNo.toString())
                intent.putExtra("TITLE", project.projectTitle)
                intent.putExtra("GRP",project.groupName)
                intent.putExtra("SEM", project.semester)
                intent.putExtra("TYPE", project.projectType.toString())
                Log.d("SPARK-API","The project number for this is ${project.projectNo}")
                startActivityForResult(intent,Activity.RESULT_OK)
            }
        }

    }

    private inner class ProjectAdapter(var projects:Recommendation):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var seniorProject1=1
        var seniorProject2=2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val View1=layoutInflater.inflate(R.layout.item_container_sp1,parent,false)

            //For Text
            View1.SP1.setTextColor(if (viewType==seniorProject1) resources.getColor(R.color.SP1)
            else
             resources.getColor(R.color.SP2))

            //For color bar
            View1.color_bar.setBackgroundColor(if (viewType==seniorProject1) resources.getColor(R.color.SP1)
            else
                resources.getColor(R.color.SP2))

            return View1Holder(View1)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val project = projects[position]
            (holder as View1Holder).bind(project)
        }

        override fun getItemCount(): Int {
            return projects.size
        }

        override fun getItemViewType(position: Int): Int {
            val project = projects[position]
            return if(project.projectType == 1){
                seniorProject1
            }else{
                seniorProject2
            }
        }

        fun setData(project: Recommendation) {
            projects = project
            notifyDataSetChanged()
        }

    }

}

