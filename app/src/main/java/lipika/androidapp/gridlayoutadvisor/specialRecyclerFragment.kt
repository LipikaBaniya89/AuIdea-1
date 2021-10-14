package lipika.androidapp.gridlayoutadvisor

import android.content.Context
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
import api.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_advisor_speciality.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"

class specialRecyclerFragment : Fragment() {
    var param1=""

    private lateinit var specialAdapter: SpeacialityViewAdapter
    private var list: AdvisorResponse= AdvisorResponse()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_advisor_speciality, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1).toString()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_Special.layoutManager = LinearLayoutManager(activity)
        specialAdapter = SpeacialityViewAdapter(param1.split("/"))
        rv_Special.adapter = specialAdapter

        param1.split("/").forEach{Log.d("12",it.toString())}


//        val retrofit: Retrofit =
//            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
//                GsonConverterFactory.create()
//            ).build()
//
//        val Api: AllApi = retrofit.create(AllApi::class.java)
//
//        //Get Advisor Specialty
//        val getAdvisorSpecialtyRequest: Call<AdvisorResponse> = Api.getAdvisor()
//        getAdvisorSpecialtyRequest.enqueue(object : Callback<AdvisorResponse> {
//
//            override fun onResponse(
//                call: Call<AdvisorResponse>,
//                response: Response<AdvisorResponse>
//            ) {
//                var advisorSpecialtyResponse = response.body()
//                if (advisorSpecialtyResponse != null) {
//                    //specialAdapter.setData(advisorSpecialtyResponse.)
//                    //Log.d("SPECIAL", advisorSpecialtyResponse.toString())
//                    advisorSpecialtyResponse.forEach { Log.d("SPECIAL",it.toString()) }
//                }
//            }
//
//            override fun onFailure(call: Call<AdvisorResponse>, t: Throwable) {
//                Log.d("SPARK-API", "Failed to Request!")
//            }
//        })

    }

    private inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var spec: TextView =itemView.findViewById(R.id.special)

       // lateinit var advisorSpecialty: Specialty

        fun bind(advisorSpecialty: String) {
            //this.advisorSpecialty=advisorSpecialty
            spec.text=advisorSpecialty

        }
    }

    private inner class SpeacialityViewAdapter(var list: List<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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


        fun setData(advisor: List<String>) {
            list=advisor
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
            specialRecyclerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}





