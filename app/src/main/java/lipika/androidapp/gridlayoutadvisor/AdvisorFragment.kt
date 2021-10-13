package lipika.androidapp.gridlayoutadvisor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.advisor_grid.*
import kotlinx.android.synthetic.main.advisor_grid.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val REQUESTCODE=101

class AdvisorFragment: Fragment() {

    private var advisorList: AdvisorSpecialtyResponse = AdvisorSpecialtyResponse()
    private lateinit var recyclerAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.advisor_recyclerview, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        saveRecyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerAdapter = RecyclerViewAdapter(advisorList)
        saveRecyclerView.adapter = recyclerAdapter
        saveRecyclerView.layoutManager = GridLayoutManager(activity, 2)

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val Api: AllApi = retrofit.create(AllApi::class.java)

//        Get Advisor Recycler
        val getAdvisorRequest: Call<AdvisorSpecialtyResponse> = Api.getAdvisorSpecialty()
        getAdvisorRequest.enqueue(object : Callback<AdvisorSpecialtyResponse> {

            override fun onResponse(call: Call<AdvisorSpecialtyResponse>, response: Response<AdvisorSpecialtyResponse>) {
                var advisorResponse = response.body()
                if (advisorResponse != null) {
                    recyclerAdapter.setData(advisorResponse)
                    Log.d("SPARK-API", advisorResponse.toString())
                }
            }

            override fun onFailure(call: Call<AdvisorSpecialtyResponse>, t: Throwable) {
                Log.d("SPARK-API", "Failed to Request!")
            }
        })
    }



    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView = itemView.findViewById(R.id.imageView)
        var name: TextView = itemView.findViewById(R.id.advisorName)

        lateinit var advisor: AdvisorSpecialtyResponseItem

        fun bind(advisor: AdvisorSpecialtyResponseItem) {
            this.advisor = advisor
            Picasso.get().load(advisor.advisorImage).into(image)
            name.text = (advisor.advisorName)

            itemView.viewAdvisorButtonGrid.setOnClickListener {
                val intent = Intent(viewAdvisorButtonGrid.context, SecondActivity::class.java)
                intent.putExtra("image",advisor.advisorImage)
                intent.putExtra("name",advisor.advisorName)
                Log.d("SPARK-API","ABCD ${advisor.advisorId}")

                startActivityForResult(intent, REQUESTCODE)
            }
        }
    }

    private inner class RecyclerViewAdapter(var advisors: List<AdvisorSpecialtyResponseItem>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.advisor_grid, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val advisor = advisors[position]
            (holder as ViewHolder).bind(advisor)
        }

        override fun getItemCount(): Int {
            return advisors.size
        }

        fun setData(advisor: List<AdvisorSpecialtyResponseItem>) {
            advisors = advisor
            notifyDataSetChanged()
        }

        }
}