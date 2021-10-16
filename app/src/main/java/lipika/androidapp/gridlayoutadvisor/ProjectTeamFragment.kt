package lipika.androidapp.gridlayoutadvisor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import api.AdvisorProjectResponseItem
import api.AllApi
import api.ProjectResponse
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_project_team.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"

class ProjectTeamFragment:Fragment() {
    var param1=""

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
        val view = inflater.inflate(R.layout.fragment_project_team,container, false)


        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val Api: AllApi = retrofit.create(AllApi::class.java)

//        Get Project Team
        val getProjectRequest: Call<ProjectResponse> = Api.getProject(param1)
        getProjectRequest.enqueue(object : Callback<ProjectResponse> {

            override fun onResponse(call: Call<ProjectResponse>, response: Response<ProjectResponse>) {
                var projectResponse = response.body()
                if (projectResponse!=null) {
                    view.findViewById<TextView>(R.id.projDetail).text = projectResponse[0].groupMembers.split(",").map { it.trim() }.joinToString("\n")
                    view.findViewById<TextView>(R.id.projectAdvisorName).text = projectResponse[0].advisorName


                    Log.d("SPARK-API", "This is working")
                }
            }

            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
                Log.d("SPARK-API","Failed to Request!")
            }
        })
        return view
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
            ProjectTeamFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}