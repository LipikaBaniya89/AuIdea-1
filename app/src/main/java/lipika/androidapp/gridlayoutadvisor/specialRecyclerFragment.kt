package lipika.androidapp.gridlayoutadvisor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.AdvisorSpecialtyResponse
import api.AllApi
import api.ProjectResponse
import kotlinx.android.synthetic.main.fragment_project_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"

class specialRecyclerFragment : Fragment() {

    var param1=" "
    private lateinit var specialAdapter: SpeacialityViewAdapter

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
        val view = inflater.inflate(R.layout.fragment_advisor_speciality,container, false)
        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val Api: AllApi = retrofit.create(AllApi::class.java)

//        Get Advisor Specialty
        val getAdvisorSpecialtyRequest: Call<AdvisorSpecialtyResponse> = Api.getAdvisorSpecialty()
        getAdvisorSpecialtyRequest.enqueue(object : Callback<AdvisorSpecialtyResponse> {

            override fun onResponse(call: Call<AdvisorSpecialtyResponse>, response: Response<AdvisorSpecialtyResponse>) {
                var advisorSpecialtyResponse = response.body()
                if (advisorSpecialtyResponse!=null) {
                    specialAdapter.setData(advisorSpecialtyResponse)
                    Log.d("SPARK-API", advisorSpecialtyResponse.toString())
                }
            }

            override fun onFailure(call: Call<AdvisorSpecialtyResponse>, t: Throwable) {
                Log.d("SPARK-API","Failed to Request!")
            }
        })


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

