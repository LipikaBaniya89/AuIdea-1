package lipika.androidapp.gridlayoutadvisor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import api.AllApi
import api.HomeProject
import api.ProjectResponse
import kotlinx.android.synthetic.main.fragment_project_detail.*
import kotlinx.android.synthetic.main.fragment_project_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"

class ProjectDesFragment: Fragment() {

    var param1=""

    var mydownloadid : Long = 0

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
        val view = inflater.inflate(R.layout.fragment_project_detail,container, false)
        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val Api: AllApi = retrofit.create(AllApi::class.java)

//        Get Project Description
        val getProjectRequest: Call<ProjectResponse> = Api.getProject(param1)
        getProjectRequest.enqueue(object : Callback<ProjectResponse> {

            override fun onResponse(call: Call<ProjectResponse>, response: Response<ProjectResponse>) {
                var projectResponse = response.body()
                if (projectResponse!=null) {
                    view.findViewById<TextView>(R.id.projDetail).text=projectResponse[0].projectDescription
                    Log.d("SPARK-API", projectResponse.toString())
                }
            }

            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
                Log.d("SPARK-API","Failed to Request!")
            }
        })

        view.downloadButton.setOnClickListener{
            Log.d("logout clicked", "Selected")
//            val intent = Intent(activity, ProjectDetail::class.java)
//            startActivity(intent)
        }

        return view

    }

    override fun onStart() {
        super.onStart()
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
            ProjectDesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}